package jeux.emb.service.standard;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import jeux.commun.dto.DtoPendu;
import jeux.commun.dto.DtoPendu.EnumResponse;
import jeux.commun.exception.ExceptionAnomaly;
import jeux.commun.service.IServicePendu;
import jeux.emb.dao.IDaoPendu;
import jeux.emb.data.Pendu;


@Component
public class ServicePendu implements IServicePendu {
	
	
	// Champs

	private static final Logger logger = Logger.getLogger(ServicePendu.class.getName());
	
	private String[]		dictionnaire;
	private final Random 	random = new Random();
	
	@Inject
	private IDaoPendu		daoPendu;
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		initialiserDictionnaire();
	}
	
	
	// Actions
	
	@Override
	public DtoPendu retrouver( String idJoueur ) {
		try {
			return map( daoPendu.retrouver( idJoueur ) );
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	@Override
	public void modifierConfig( DtoPendu dtoPendu ) {
		try {
			Pendu pendu = daoPendu.retrouver( dtoPendu.getIdJoueur() );
			pendu.setFlagModeFacile( dtoPendu.isFlagModeFacile() );
			pendu.setNbErreursMaxi( dtoPendu.getNbErreursMaxi() );
			daoPendu.modifier( pendu );
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	
	@Override
	public DtoPendu nouvellePartie( String idJoueur ) {

		try {
			boolean flagNouveauJoueur = false;
			Pendu pendu = daoPendu.retrouver( idJoueur );

			if ( pendu == null ) {
				flagNouveauJoueur = true;
				pendu = new Pendu();
				pendu.setIdJoueur( idJoueur );
				pendu.setFlagModeFacile(false);
				pendu.setNbErreursMaxi( 7 );
			}
			pendu.setNbErreursRestantes( pendu.getNbErreursMaxi() );
			pendu.setLettresJouees("");
			
			int rang = random.nextInt( dictionnaire.length );
			String motMystere = dictionnaire[rang];
			
			StringBuilder sbResultat = new StringBuilder();
			sbResultat.append('_');
			for ( int i=1; i < motMystere.length() ; i++ ) {
				sbResultat.append(' ').append('_');
			}
			if ( pendu.isFlagModeFacile() ) {
				sbResultat.setCharAt( 0, motMystere.charAt(0) );
				sbResultat.setCharAt( sbResultat.length() - 1, motMystere.charAt( motMystere.length() - 1 ) );
			}

			pendu.setMotMystere( motMystere );
			pendu.setResultat( sbResultat.toString()  );

			if ( flagNouveauJoueur ) {
				daoPendu.inserer(pendu);
			} else {
				daoPendu.modifier( pendu );
			}
			return map(pendu);
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	
	@Override
	public DtoPendu jouer( String idJoueur, char lettre ) {

		try {
			Pendu pendu = daoPendu.retrouver( idJoueur );
			String motMystere = pendu.getMotMystere();
			StringBuilder sbResultat = new StringBuilder( pendu.getResultat() );

			boolean flagErreur = true;
			int pos = -1;
			while (true) {
				pos = motMystere.indexOf( lettre, pos+1 );
				if ( pos < 0 ) 
					break;
				if ( sbResultat.charAt( pos*2 ) == '_' ) {
					sbResultat.setCharAt( pos*2, lettre );
					flagErreur = false;
				}
			}
			if ( flagErreur ) {
				pendu.setNbErreursRestantes( pendu.getNbErreursRestantes() - 1);
			} else {
				pendu.setResultat( sbResultat.toString() );
			}
			pendu.setLettresJouees( pendu.getLettresJouees() + lettre );
			daoPendu.modifier( pendu );
			return map(pendu);
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	@Override
	public String tricher( String idJoueur ) {
		try {
			return daoPendu.retrouver( idJoueur ).getMotMystere();
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	
	// Methodes auxiliaires
	
	private DtoPendu map( Pendu pendu ) {
		if( pendu == null ) {
			return null;
		}
		DtoPendu dtoPendu = new DtoPendu();
		dtoPendu.setIdJoueur( pendu.getIdJoueur() );
		dtoPendu.setFlagModeFacile( pendu.isFlagModeFacile() );
		dtoPendu.setNbErreursMaxi( pendu.getNbErreursMaxi() );
		if ( pendu.getResultat().indexOf('_') < 0 ) {
			dtoPendu.setReponse( EnumResponse.GAGNE );
			dtoPendu.setMessage( "F??licitations, vous avez gagn?? !" );
		} else if ( pendu.getNbErreursRestantes() < 0 ) {
			dtoPendu.setReponse( EnumResponse.PERDU );
			dtoPendu.setMessage( "Vous avez perdu.\nLa solution ??tait " + pendu.getMotMystere() );
		} else {
			dtoPendu.setReponse( EnumResponse.EN_COURS );
			dtoPendu.setMessage( String.format( "Trouvez le mot cach??.%nVous avez droit ?? %d erreurs.", pendu.getNbErreursRestantes() ) );
		}
		dtoPendu.setResultat( pendu.getResultat() );
		dtoPendu.setNbErreursRestantes( pendu.getNbErreursRestantes() );
		dtoPendu.setLettresJouees( pendu.getLettresJouees() );
		return dtoPendu;
	}
	
	
	private void initialiserDictionnaire() {
		
		dictionnaire = new String[] {
			"AEROPORT",
			"APPARTEMENT",
			"AUTOROUTE",
			"BIBLIOTHEQUE",
			"BOULANGERIE",
			"BOULEVARD",
			"CARREFOUR",
			"CATHEDRALE",
			"METROPOLE",
			"PREFECTURE",
			"PERIPHERIQUE",
			"PHARMACIE",
			"SUPERMARCHE",
			"TROLLEYBUS",
		};
	}

}
