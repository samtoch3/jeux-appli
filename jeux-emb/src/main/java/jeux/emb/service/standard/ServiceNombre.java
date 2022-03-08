package jeux.emb.service.standard;

import java.util.Random;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import jeux.commun.dto.DtoNombre;
import jeux.commun.dto.DtoNombre.EnumResponse;
import jeux.commun.exception.ExceptionValidation;
import jeux.commun.service.IServiceNombre;
import jeux.emb.dao.IDaoNombre;
import jeux.emb.data.Nombre;


@Component
public class ServiceNombre implements IServiceNombre {
	
	
	// Champs
	
	private final Random random = new Random();
	
	@Inject
	private IDaoNombre	daoNombre;
	
	
	// Actions
	
	@Override
	public DtoNombre retrouver( String idJoueur ) {
		return map( daoNombre.retrouver(idJoueur) );
	}
	
	@Override
	public void modifierConfig( DtoNombre dtoNombre ) {
		Nombre nombre = daoNombre.retrouver( dtoNombre.getIdJoueur() );
		nombre.setValeurMaxi( dtoNombre.getValeurMaxi() );
		nombre.setNbEssaisMaxi( dtoNombre.getNbEssaisMaxi() );
		daoNombre.modifier( nombre );
	}
	
	@Override
	public DtoNombre nouvellePartie( String idJoueur ) {

		boolean flagNouveauJoueur = false;
		Nombre nombre = daoNombre.retrouver( idJoueur );

		if ( nombre == null ) {
			flagNouveauJoueur = true;
			nombre = new Nombre();
			nombre.setIdJoueur( idJoueur );
			nombre.setValeurMaxi( 32 );
			nombre.setNbEssaisMaxi( 5 );
		}

		nombre.setNbEssaisRestants( nombre.getNbEssaisMaxi() );
		nombre.setNombreMystere( random.nextInt( nombre.getValeurMaxi() + 1) );
		nombre.setBorneInf(0);
		nombre.setBorneSup( nombre.getValeurMaxi() );
		nombre.setFlagPartieGagnee( false );
		
		if ( flagNouveauJoueur ) {
			daoNombre.inserer( nombre );
		} else {
			daoNombre.modifier( nombre );
		}
		return map(nombre);
	}

	
	@Override
	public DtoNombre jouer( String idJoueur, int proposition ) throws ExceptionValidation {

		Nombre nombre = daoNombre.retrouver( idJoueur );
		
		// Contrôle de validité
		if ( proposition < 0 || nombre.getValeurMaxi() < proposition ) {
			throw new ExceptionValidation();
		}
		
		nombre.setNbEssaisRestants( nombre.getNbEssaisRestants() - 1 );

		if ( proposition == nombre.getNombreMystere()  ) {
			nombre.setFlagPartieGagnee( true );
		} else if ( nombre.getNbEssaisRestants() > 0 ) {
			if ( proposition < nombre.getNombreMystere()  ) {
				if ( nombre.getBorneInf() <= proposition ) {
					nombre.setBorneInf( proposition + 1 );
				}
			} else {
				if ( nombre.getBorneSup() >= proposition ) {
					nombre.setBorneSup( proposition - 1 );
				}
			}
		}
		daoNombre.modifier( nombre );
		return map( nombre, proposition );
	}

	
	@Override
	public int tricher( String idJoueur ) {
		Nombre nombre = daoNombre.retrouver( idJoueur );
		return nombre.getNombreMystere();
	}

	
	// Methodes auxiliaires
	
	private DtoNombre map( Nombre nombre ) {
		return map(nombre, -1 );
	}
	
	private DtoNombre map( Nombre nombre, int proposition ) {

		if( nombre == null ) {
			return null;
		}

		DtoNombre dtoNombre = new DtoNombre();
		dtoNombre.setIdJoueur( nombre.getIdJoueur() );
		dtoNombre.setValeurMaxi( nombre.getValeurMaxi() );
		dtoNombre.setNbEssaisMaxi( nombre.getNbEssaisMaxi() );
		dtoNombre.setNbEssaisRestants( nombre.getNbEssaisRestants() );
		
		if (nombre.isFlagPartieGagnee()) {
			dtoNombre.setReponse( EnumResponse.GAGNE );
			dtoNombre.setMessage( "Gagné !\nLe nombre mystère était " + nombre.getNombreMystere() );
		} else if (nombre.getNbEssaisRestants() == 0) {
			dtoNombre.setReponse( EnumResponse.PERDU );
			dtoNombre.setMessage( "Perdu !\nLe nombre mystère était " + nombre.getNombreMystere() );
		} else if ( proposition < 0 ) {
			dtoNombre.setReponse( null );
			dtoNombre.setMessage( String.format( "Trouvez un nombre entre %d et %d%nVous avez droit à %d essais.", nombre.getBorneInf(), nombre.getBorneSup(), nombre.getNbEssaisRestants() ) );
		} else {
			if ( proposition < nombre.getNombreMystere() ) {
				dtoNombre.setReponse( EnumResponse.TROP_PETIT );
				dtoNombre.setMessage( String.format( "%d est trop petit. !", proposition ) );
			} else if ( proposition > nombre.getNombreMystere() ) {
				dtoNombre.setReponse( EnumResponse.TROP_GRAND );
				dtoNombre.setMessage( String.format( "%d est trop grand. !", proposition ) );
			}
		}
		
		return dtoNombre;
	}
}
