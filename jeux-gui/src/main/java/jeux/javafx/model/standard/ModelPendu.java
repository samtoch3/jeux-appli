package jeux.javafx.model.standard;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.util.Duration;
import jeux.commun.dto.DtoPendu;
import jeux.commun.dto.DtoPendu.EnumResponse;
import jeux.commun.service.IServicePendu;
import jeux.javafx.model.IModelPendu;
import jeux.javafx.view.pendu.ControllerPenduJeu;


@Component
public class ModelPendu implements IModelPendu {
	
	
	// Champs pour la vue Config
	
	private final BooleanProperty	flagModeFacileVue	= new SimpleBooleanProperty();
	private final Property<Integer>	nbErreursMaxiVue			= new SimpleObjectProperty<>(0);
	
	
	// Champs pour la vue Jeu

	private final StringProperty	idJoueur			= new SimpleStringProperty( "XX");
	private final StringProperty	messageVue		= new SimpleStringProperty();
	private final StringProperty	resultatVue		= new SimpleStringProperty();
	private final Property<Image>	imageVue		= new SimpleObjectProperty<>();
	private final BooleanProperty	flagPartieDebutVue	= new SimpleBooleanProperty();
	private final BooleanProperty	flagPartieFinieVue	= new SimpleBooleanProperty();
	private final StringProperty	lettresJoueesVue	= new SimpleStringProperty();
	
	
	// Valeurs courantes
	
	private Image[]		images = new Image[9];

	private boolean		flagPartieFinie;
	
	
	// Autres champs

	@Inject
	private IServicePendu	servicePendu;
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		initialiserImages();
	}

	
	// Getters
	
	@Override
	public final BooleanProperty flagModeFacileProperty() {
		return flagModeFacileVue;
	}

	@Override
	public final Property<Integer> nbErreursMaxiProperty() {
		return nbErreursMaxiVue;
	}

	@Override
	public final StringProperty messageProperty() {
		return messageVue;
	}

	@Override
	public final StringProperty resultatProperty() {
		return resultatVue;
	}

	@Override
	public final Property<Image> imageProperty() {
		return imageVue;
	}

	@Override
	public final BooleanProperty flagPartieDebutProperty() {
		return flagPartieDebutVue;
	}

	@Override
	public final BooleanProperty flagPartieFinieProperty() {
		return flagPartieFinieVue;
	}
	
	@Override
	public final StringProperty lettresJoueesProperty() {
		return lettresJoueesVue;
	}
	
	
	// Actions

	@Override
	public void preparerConfig()  {
		DtoPendu dtoPendu = servicePendu.retrouver(idJoueur.get());
		flagModeFacileVue.set( dtoPendu.isFlagModeFacile() );
		nbErreursMaxiVue.setValue( dtoPendu.getNbErreursMaxi() );
	}
	
	@Override
	public void enregistrerConfig()  {
		DtoPendu dtoPendu = new DtoPendu();
		dtoPendu.setIdJoueur(idJoueur.get());
		dtoPendu.setFlagModeFacile( flagModeFacileVue.get() );
		dtoPendu.setNbErreursMaxi( nbErreursMaxiVue.getValue() );
		servicePendu.modifierConfig( dtoPendu );
		nouvellePartie();
	}
	
	
	@Override
	public void nouvellePartie()  {
		DtoPendu dtoPendu = servicePendu.nouvellePartie( idJoueur.get() );
		flagPartieFinie = false;
		actualiserVueJeu( dtoPendu );
	}

	@Override
	public void retrouverPartie()  {
		DtoPendu dtoPendu = servicePendu.retrouver(idJoueur.get());
		if( dtoPendu == null ) {
			dtoPendu = servicePendu.nouvellePartie(idJoueur.get());
		}
		flagPartieFinie = false;
		actualiserVueJeu( dtoPendu );		
	}
	
	@Override
	public void jouer( char lettre )  {
		if ( flagPartieFinie ) {
			return;
		}
		DtoPendu dtoPendu = servicePendu.jouer( idJoueur.get(),	lettre );
		actualiserVueJeu( dtoPendu );
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
		messageVue.set( "La solution est " + servicePendu.tricher( idJoueur.get() ) ) ;
		
	}
	
	
	// Méthodes auxiliaires
	
	private void actualiserVueJeu( DtoPendu dtoPendu )  {

		messageVue.set(dtoPendu.getMessage() );
		resultatVue.set( dtoPendu.getResultat() );
		lettresJoueesVue.set( dtoPendu.getLettresJouees() );
		if ( dtoPendu.getReponse() != EnumResponse.EN_COURS) {
			flagPartieFinie = true;
		}
		if ( dtoPendu.getNbErreursRestantes() == dtoPendu.getNbErreursMaxi() && dtoPendu.getNbErreursMaxi() < 7 ) {
			imageVue.setValue( images[1]);
		} else {
			imageVue.setValue( images[ Math.max( 0, 7 - dtoPendu.getNbErreursRestantes() ) ]);
		}
	}
	
	
	private void initialiserImages() {
		for ( int i = 0; i < images.length; i++ ) {
			images[i] = new Image( ControllerPenduJeu.class.getResource( "images/pendu-" + i + ".png").toString() );
		}
	}
}
