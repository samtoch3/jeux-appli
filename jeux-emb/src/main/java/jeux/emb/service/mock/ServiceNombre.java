package jeux.emb.service.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import jeux.commun.dto.DtoNombre;
import jeux.commun.dto.DtoNombre.EnumResponse;
import jeux.commun.exception.ExceptionValidation;
import jeux.commun.service.IServiceNombre;
import jeux.emb.data.Nombre;


@Component
public class ServiceNombre implements IServiceNombre {
	
	
	// Champs
	
	private final Map<String, Nombre> mapData = new HashMap<>();
	
	private final Random random = new Random();
	
	
	// Actions
	
	@Override
	public DtoNombre retrouver( String idJoueur ) {
		return map( getDataNombre( idJoueur ) );
	}
	
	@Override
	public void modifierConfig( DtoNombre dtoNombre ) {
		Nombre dataNombre = getDataNombre( dtoNombre.getIdJoueur() );
		dataNombre.setValeurMaxi( dtoNombre.getValeurMaxi() );
		dataNombre.setNbEssaisMaxi( dtoNombre.getNbEssaisMaxi() );
	}
	
	@Override
	public DtoNombre nouvellePartie( String idJoueur )  {
		Nombre dataNombre = mapData.get( idJoueur );
		if ( dataNombre == null ) {
			dataNombre = initDataNouveauJoueur( idJoueur );
		}
		dataNombre.setNbEssaisRestants( dataNombre.getNbEssaisMaxi() );
		dataNombre.setNombreMystere( random.nextInt( dataNombre.getValeurMaxi() + 1) );
		dataNombre.setBorneInf(0);
		dataNombre.setBorneSup( dataNombre.getValeurMaxi() );
		dataNombre.setFlagPartieGagnee( false );
		return map(dataNombre);
	}

	
	@Override
	public DtoNombre jouer( String idJoueur, int proposition ) throws ExceptionValidation {

		Nombre dataNombre = getDataNombre( idJoueur );
		
		// Contrôle de validité
		if ( proposition < 0 || dataNombre.getValeurMaxi() < proposition ) {
			throw new IllegalArgumentException();
		}
		
		dataNombre.setNbEssaisRestants( dataNombre.getNbEssaisRestants() - 1 );

		if ( proposition == dataNombre.getNombreMystere()  ) {
			dataNombre.setFlagPartieGagnee( true );
		} else if ( dataNombre.getNbEssaisRestants() > 0 ) {
			if ( proposition < dataNombre.getNombreMystere()  ) {
				if ( dataNombre.getBorneInf() <= proposition ) {
					dataNombre.setBorneInf( proposition + 1 );
				}
			} else {
				if ( dataNombre.getBorneSup() >= proposition ) {
					dataNombre.setBorneSup( proposition - 1 );
				}
			}
		}
		
		DtoNombre dtoReponse = map(dataNombre, proposition);
		return dtoReponse;
	}
	
	
	@Override
	public int tricher( String idJoueur )  {
		Nombre dataNombre = getDataNombre( idJoueur );
		return dataNombre.getNombreMystere();
	}

	
	// Methodes auxiliaires
	
	private DtoNombre map( Nombre dataNombre ) {
		return map(dataNombre, -1 );
	}
	
	private DtoNombre map( Nombre dataNombre, int proposition ) {

		if( dataNombre == null ) {
			return null;
		}

		DtoNombre dtoNombre = new DtoNombre();
		dtoNombre.setIdJoueur( dataNombre.getIdJoueur() );
		dtoNombre.setValeurMaxi( dataNombre.getValeurMaxi() );
		dtoNombre.setNbEssaisMaxi( dataNombre.getNbEssaisMaxi() );
		dtoNombre.setNbEssaisRestants( dataNombre.getNbEssaisRestants() );
		
		if (dataNombre.isFlagPartieGagnee()) {
			dtoNombre.setReponse( EnumResponse.GAGNE );
			dtoNombre.setMessage( "Gagné !\nLe nombre mystère était " + dataNombre.getNombreMystere() );
		} else if (dataNombre.getNbEssaisRestants() == 0) {
			dtoNombre.setReponse( EnumResponse.PERDU );
			dtoNombre.setMessage( "Perdu !\nLe nombre mystère était " + dataNombre.getNombreMystere() );
		} else if ( proposition < 0 ) {
			dtoNombre.setReponse( null );
			dtoNombre.setMessage( String.format( "Trouvez un nombre entre %d et %d%nVous avez droit à %d essais.", dataNombre.getBorneInf(), dataNombre.getBorneSup(), dataNombre.getNbEssaisRestants() ) );
		} else {
			if ( proposition < dataNombre.getNombreMystere() ) {
				dtoNombre.setReponse( EnumResponse.TROP_PETIT );
				dtoNombre.setMessage( String.format( "%d est trop petit. !", proposition ) );
			} else if ( proposition > dataNombre.getNombreMystere() ) {
				dtoNombre.setReponse( EnumResponse.TROP_GRAND );
				dtoNombre.setMessage( String.format( "%d est trop grand. !", proposition ) );
			}
		}
		
		return dtoNombre;
	}
	
	
	private Nombre getDataNombre( String idJoueur ) {
		Nombre dataNombre =  mapData.get( idJoueur );
		if ( dataNombre == null ) {
			nouvellePartie(idJoueur);
			dataNombre = mapData.get( idJoueur );
		}
		return dataNombre;
	}
	
	
	private Nombre initDataNouveauJoueur( String idJoueur ) {
		Nombre dataNombre = new Nombre();
		dataNombre.setIdJoueur( idJoueur );
		dataNombre.setValeurMaxi( 32 );
		dataNombre.setNbEssaisMaxi( 5 );
		mapData.put( idJoueur, dataNombre );
		return dataNombre;
	}
}
