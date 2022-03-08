package jeux.emb.service.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.swing.undo.CannotUndoException;

import org.springframework.stereotype.Component;

import jeux.commun.dto.DtoPendu;
import jeux.commun.dto.DtoPendu.EnumResponse;
import jeux.commun.exception.ExceptionAnomaly;
import jeux.commun.service.IServicePendu;
import jeux.emb.data.Pendu;


@Component
public class ServicePendu implements IServicePendu {
	
	
	// Champs
	
	private static final Logger logger = Logger.getLogger(ServicePendu.class.getName());
	
	private final Map<String, Pendu> mapData = new HashMap<>();

	private String[]		dictionnaire;
	
	private final Random 	random = new Random();

	
	// Initialisation
	
	@PostConstruct
	public void init() {
		initialiserDictionnaire();
	}
	
	
	// Actions
	
	@Override
	public DtoPendu retrouver( String idJoueur )  {
		try {
			return map( mapData.get( idJoueur ) );
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	@Override
	public void modifierConfig( DtoPendu dtoPendu ) {
		try {
			Pendu pendu = mapData.get( dtoPendu.getIdJoueur() );
			pendu.setFlagModeFacile( dtoPendu.isFlagModeFacile() );
			pendu.setNbErreursMaxi( dtoPendu.getNbErreursMaxi() );
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	
	@Override
	public DtoPendu nouvellePartie( String idJoueur )  {

		try {
			Pendu pendu = mapData.get( idJoueur );
			if ( pendu == null ) {
				pendu = initDataNouveauJoueur( idJoueur );
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
			return map(pendu);
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}
	
	@Override
	public DtoPendu jouer( String idJoueur, char lettre )  {

		try {
			Pendu pendu = mapData.get( idJoueur );
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
			return map(pendu);
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new ExceptionAnomaly(e);
		}
	}

	@Override
	public String tricher( String idJoueur )  {
		try {
			Pendu pendu = mapData.get( idJoueur );
			return pendu.getMotMystere();
		} catch (CannotUndoException e) {
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
			dtoPendu.setMessage( "Félicitations, vous avez gagné !" );
		} else if ( pendu.getNbErreursRestantes() < 0 ) {
			dtoPendu.setReponse( EnumResponse.PERDU );
			dtoPendu.setMessage( "Vous avez perdu.\nLa solution était " + pendu.getMotMystere() );
		} else {
			dtoPendu.setReponse( EnumResponse.EN_COURS );
			dtoPendu.setMessage( String.format( "Trouvez le mot caché.%nVous avez droit à %d erreurs.", pendu.getNbErreursRestantes() ) );
		}
		dtoPendu.setResultat( pendu.getResultat() );
		dtoPendu.setNbErreursRestantes( pendu.getNbErreursRestantes() );
		dtoPendu.setLettresJouees( pendu.getLettresJouees() );
		return dtoPendu;
	}
	
	
	private Pendu initDataNouveauJoueur( String idJoueur ) {
		Pendu pendu = new Pendu();
		pendu.setIdJoueur( idJoueur );
		pendu.setFlagModeFacile(false);
		pendu.setNbErreursMaxi( 7 );
		mapData.put( idJoueur, pendu );
		return pendu;
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
