package jeux.javafx.model.autonome;

import java.util.Random;

import javax.annotation.PostConstruct;

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
import jeux.javafx.model.IModelPendu;
import jeux.javafx.view.pendu.ControllerPenduJeu;


@Component
public class ModelPendu implements IModelPendu {

	
	// Champs pour la vue Config
	
	private final BooleanProperty	flagModeFacileVue	= new SimpleBooleanProperty();
	private final Property<Integer>	nbErreursMaxiVue	= new SimpleObjectProperty<>(0);
	
	
	// Champs pour la vue Jeu

	private final StringProperty	messageVue		= new SimpleStringProperty();
	private final StringProperty	resultatVue		= new SimpleStringProperty();
	private final Property<Image>	imageVue		= new SimpleObjectProperty<>();
	private final BooleanProperty	flagPartieDebutVue	= new SimpleBooleanProperty();
	private final BooleanProperty	flagPartieFinieVue	= new SimpleBooleanProperty();
	private final StringProperty	lettresJoueesVue	= new SimpleStringProperty();
	
	
	// Autre champs
	
	private Image[]		images = new Image[9];
	private String[]	dictionnaire;

	private int			nbErreursMaxi = 7;
	private boolean		flagModeFacile = false;
	
	private String		motMystere;
	private String		resultat;
	private int			nbErreursRestantes;
	private StringBuilder	lettresJouees = new StringBuilder();

	private String		message;
	private boolean		flagPartieDebut;
	private boolean		flagPartieFinie;
	
	private final Random random = new Random();
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		initialiserDictionnaire();
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
	public void preparerConfig() {
		flagModeFacileVue.set(flagModeFacile);
		nbErreursMaxiVue.setValue( nbErreursMaxi);
	}
	

	@Override
	public void enregistrerConfig() {
		flagModeFacile = flagModeFacileVue.get();
		nbErreursMaxi = nbErreursMaxiVue.getValue();
		nouvellePartie();
	}
	
	
	@Override
	public void nouvellePartie() {
		
		nbErreursRestantes = nbErreursMaxi;
		flagPartieDebut = true;
		flagPartieFinie = false;
		lettresJouees.delete(0, lettresJouees.length() );
		message = String.format( "Trouvez le mot caché.%nVous avez droit à %d erreurs.", nbErreursRestantes );
		
		int rang = random.nextInt( dictionnaire.length );
		motMystere = dictionnaire[rang];
		
		StringBuilder sbResultat = new StringBuilder();
		sbResultat.append('_');
		for ( int i=1; i < motMystere.length() ; i++ ) {
			sbResultat.append(' ').append('_');
		}
		
		if ( flagModeFacile ) {
			sbResultat.setCharAt( 0, motMystere.charAt(0) );
			sbResultat.setCharAt( sbResultat.length() - 1, motMystere.charAt( motMystere.length() - 1 ) );
		}
		resultat = sbResultat.toString();

		actualiserVueJeu();
	}

	
	@Override
	public void retrouverPartie() {
		nouvellePartie();
	}

	
	@Override
	public void jouer( char lettre ) {

		if ( flagPartieFinie ) {
			return;
		} else {
			flagPartieDebut = false;
		}
		boolean flagErreur = true;
		lettresJouees.append(lettre);
		int pos = -1;
		StringBuilder sbResultat = new StringBuilder( resultat );
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
			nbErreursRestantes--;
		} else {
			resultat = sbResultat.toString();
		}
		if ( nbErreursRestantes < 0 ) {
			flagPartieFinie = true;
			message = "Vous avez perdu.\nLa solution était " + motMystere;
		} else if ( sbResultat.toString().indexOf('_') < 0 ) {
			flagPartieFinie = true;
			message = "Félicitations, vous avez gagné !";
		} else {
			message = String.format( "Trouvez le mot caché.%nVous avez droit à %d erreurs.", nbErreursRestantes );
		}
		actualiserVueJeu();
	}

	
	@Override
	public void tricher() {
		
		String sauvegarde = messageVue.get();

		// Rétablit l'affichage normal au bout de 500 millisecondes 
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(500),
		        ae -> messageVue.set( sauvegarde )) );
		timeline.play();
		
		// Affiche le message de triche
		messageVue.set( "La solution est " + motMystere ) ;
		
	}
	
	
	// Méthodes auxiliaires
	
	private void actualiserVueJeu() {

		messageVue.set(message);
		resultatVue.set( resultat );
		flagPartieDebutVue.set( flagPartieDebut );
		flagPartieFinieVue.set( flagPartieFinie );
		lettresJoueesVue.set( lettresJouees.toString() );
		if ( nbErreursRestantes == nbErreursMaxi && nbErreursMaxi < 7 ) {
			imageVue.setValue( images[1]);
		} else {
			imageVue.setValue( images[ Math.max( 0, 7 - nbErreursRestantes ) ]);
		}
	}
	
	
	private void initialiserImages() {
		for ( int i = 0; i < images.length; i++ ) {
//			images[i] = new Image( Thread.currentThread().getContextClassLoader().getResourceAsStream( "jeux/javafx/view/pendu/images/pendu-" + i + ".png") );
			images[i] = new Image( ControllerPenduJeu.class.getResourceAsStream( "images/pendu-" + i + ".png") );
//			images[i] = new Image( ControllerPenduJeu.class.getClassLoader().getResourceAsStream( "/jeux/javafx/view/pendu/images/pendu-" + i + ".png") );
//			images[i] = new Image( ControllerPenduJeu.class.getClassLoader().getResourceAsStream( "images/pendu-" + i + ".png") );
		}
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
