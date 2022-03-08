package jeux.javafx.model.autonome;

import java.util.Random;

import org.springframework.stereotype.Component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import jeux.javafx.model.IModelNombre;


@Component
public class ModelNombre implements IModelNombre {
	
	
	// Champs pour la vue Config
	
	private final StringProperty		valeurMaxiVue		= new SimpleStringProperty();
	private final StringProperty		nbEssaisMaxiVue		= new SimpleStringProperty();

	
	// Champs pour la vue Jeu
	
	private final StringProperty		messageVue			= new SimpleStringProperty();
	private final StringProperty		nbEssaisRestantsVue	= new SimpleStringProperty();
	private final StringProperty		propositionVue		= new SimpleStringProperty();
	private final BooleanProperty		flagPartieFinieVue	= new SimpleBooleanProperty();

	
	// Valeurs courantes
	
	private int 		valeurMaxi 		= 32;
	private int			nbEssaisMaxi	=  5;

	private int			nbEssaisRestants;
	private int			nombreMystere;
	
	private String		message;
	private boolean		flagPartieFinie;
	
	private final Random random = new Random();
	
	
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
	public void preparerconfig() {
		
		// Transfère les valeurs des variables courantes
		// dans les variables liées à la vue Config
		valeurMaxiVue.set( String.valueOf( valeurMaxi ) );
		nbEssaisMaxiVue.set( String.valueOf( nbEssaisMaxi ) );
	}
	
	@Override
	public void enregistrerconfig() {

		// Transfère les valeurs des variables liées à la vue Config
		// dans les variables courantes
		valeurMaxi = Integer.parseInt( valeurMaxiVue.get() );
		nbEssaisMaxi = Integer.parseInt( nbEssaisMaxiVue.get() );
		nouvellePartie();
	}

	
	@Override
	public void nouvellePartie() {
		nombreMystere = random.nextInt( valeurMaxi + 1 );
		nbEssaisRestants = nbEssaisMaxi;
		flagPartieFinie = false;
		message = String.format( "Trouvez un nombre entre 0 et %d%nVous avez droit à %d essais.", valeurMaxi, nbEssaisRestants );
		actualiserVueJeu();
	}

	@Override
	public void retrouverPartie() {
		nouvellePartie();
	}
	
	
	@Override
	public void jouer() {
		
		int 		proposition;

		try {
			proposition = Integer.parseInt( propositionVue.get() );
		} catch (NumberFormatException e) {
			message = "Valeur incorreecte.\nRecommencez !";
			actualiserVueJeu();		
			return;
		}
	
		nbEssaisRestants--;
		if ( proposition == nombreMystere  ) {
			message = "Gagné !\nLe nombre mystère était " + nombreMystere;
			flagPartieFinie = true;
		} else {
			if ( nbEssaisRestants <= 0 ) {
				message = "Perdu !\nLe nombre mystère était " + nombreMystere ;
				flagPartieFinie = true;
			}else if ( proposition < nombreMystere  ) {
				message = String.format( "%d est trop petit. !", proposition );
			} else {
				message = String.format( "%d est trop grand. !", proposition );
			}
		}
		actualiserVueJeu();		
		
	}
	
	
	@Override
	public void tricher() {
		
		message = messageVue.get();

		// Rétablit l'affichage normal au bout de 500 millisecondes 
		Timeline timeline = new Timeline(new KeyFrame(
		        Duration.millis(500),
		        ae -> messageVue.set( message )) );
		timeline.play();
		
		// Affiche le message de triche
		messageVue.set( "La solution est " + nombreMystere ) ;
		
	}
	
	
	// Méthodes auxiliaires
	
	private void actualiserVueJeu() {
		messageVue.set( message );
		if ( nbEssaisRestants < 2) {
			nbEssaisRestantsVue.set( nbEssaisRestants + " essai restant" );
		} else {
			nbEssaisRestantsVue.set( nbEssaisRestants + " essais restants" );
		}
		if ( ! flagPartieFinie ) {
			propositionVue.set(null);
		}
		flagPartieFinieVue.set( flagPartieFinie );
	}
	
}
