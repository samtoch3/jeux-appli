package jeux.javafx.model.standard;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import jeux.commun.dto.DtoNombre;
import jeux.commun.dto.DtoNombre.EnumResponse;
import jeux.commun.exception.ExceptionValidation;
import jeux.commun.service.IServiceNombre;
import jeux.javafx.model.IModelNombre;


@Component
public class ModelNombre implements IModelNombre {
	
	
	// Champs pour la vue Config
	
	private final StringProperty	valeurMaxiVue	= new SimpleStringProperty();
	private final StringProperty	nbEssaisMaxiVue	= new SimpleStringProperty();

	
	// Champs pour la vue Jeu
	
	private final StringProperty	idJoueur			= new SimpleStringProperty( "XX");
	private final StringProperty	messageVue			= new SimpleStringProperty();
	private final StringProperty	nbEssaisRestantsVue	= new SimpleStringProperty();
	private final StringProperty	propositionVue		= new SimpleStringProperty();
	private final BooleanProperty	flagPartieFinieVue	= new SimpleBooleanProperty();
	
	
	// Autres champs

	@Inject
	private IServiceNombre	serviceNombre;
	
	
	// Getters
	
	@Override
	public final StringProperty valeurMaxiProperty() {
		return this.valeurMaxiVue;
	}

	@Override
	public final StringProperty nbEssaisMaxiProperty() {
		return this.nbEssaisMaxiVue;
	}

	@Override
	public final StringProperty nbEssaisRestantsProperty() {
		return this.nbEssaisRestantsVue;
	}

	@Override
	public final StringProperty messageProperty() {
		return this.messageVue;
	}

	@Override
	public final StringProperty propositionProperty() {
		return this.propositionVue;
	}
	
	@Override
	public final BooleanProperty flagPartieFinieProperty() {
		return this.flagPartieFinieVue;
	}
	
	
	// Actions
	
	@Override
	public void preparerconfig()  {
		DtoNombre dtoNombre = serviceNombre.retrouver(idJoueur.get());
		valeurMaxiVue.set( String.valueOf( dtoNombre.getValeurMaxi() ) );
		nbEssaisMaxiVue.set( String.valueOf( dtoNombre.getNbEssaisMaxi() ) );
	}
	
	@Override
	public void enregistrerconfig()  {
		DtoNombre dtoNombre = new DtoNombre();
		dtoNombre.setIdJoueur( idJoueur.get() );
		dtoNombre.setValeurMaxi( Integer.parseInt( valeurMaxiVue.get() ) );
		dtoNombre.setNbEssaisMaxi( Integer.parseInt( nbEssaisMaxiVue.get() ) );
		serviceNombre.modifierConfig( dtoNombre );
		nouvellePartie();
	}

	
	@Override
	public void nouvellePartie()  {
		DtoNombre dtoNombre = serviceNombre.nouvellePartie(idJoueur.get());
		actualiserVueJeu( dtoNombre );		
	}

	
	@Override
	public void retrouverPartie()  {
		DtoNombre dtoReponse = serviceNombre.retrouver(idJoueur.get());
		if( dtoReponse == null ) {
			dtoReponse = serviceNombre.nouvellePartie(idJoueur.get());
		}
		actualiserVueJeu( dtoReponse );		
	}

	
	@Override
	public void jouer()  {
		try {
			int proposition = Integer.parseInt( propositionVue.get() );
			DtoNombre dtoNombre = serviceNombre.jouer(idJoueur.get(), proposition );
			actualiserVueJeu( dtoNombre );		
		} catch (IllegalArgumentException | ExceptionValidation e) {
			messageVue.set( "Valeur incorreecte.\nRecommencez !" );
		}
	}
	
	
	@Override
	public void tricher()  {
		
		String message = messageVue.get();

		// Rétablit l'affichage normal au bout de 500 millisecondes 
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(500),
		        ae -> messageVue.set( message )) );
		timeline.play();
		
		// Affiche le message de triche
		messageVue.set( "La solution est " + serviceNombre.tricher(idJoueur.get()) ) ;
	}
	
	
	// Méthodes auxiliaires
	
	private void actualiserVueJeu( DtoNombre dtoNombre ) {
		
		messageVue.set( dtoNombre.getMessage() );

		if ( dtoNombre.getNbEssaisRestants() < 2) {
			nbEssaisRestantsVue.set( dtoNombre.getNbEssaisRestants() + " essai restant" );
		} else {
			nbEssaisRestantsVue.set( dtoNombre.getNbEssaisRestants() + " essais restants" );
		}

		if ( dtoNombre.getReponse() == EnumResponse.GAGNE || dtoNombre.getReponse() == EnumResponse.PERDU ) {
			flagPartieFinieVue.set( true );
		} else {
			flagPartieFinieVue.set( false );
			propositionVue.set(null);
		}
		
	}
	
	
}
