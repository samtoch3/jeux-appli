package jeux.javafx.view.pendu;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import jeux.javafx.model.IModelPendu;
import jeux.javafx.view.EnumView;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;


@Component
@Scope( "prototype" )
public class ControllerPenduJeu extends Controller {
	
	
	// Composants de la vue
	
	@FXML
	private Label			labelMessage;
	
	@FXML
	private Label			labelResultat;
	
	@FXML
	private ImageView		imageView;
	
	@FXML
	private FlowPane		paneBoutonsLettres;
	
	
	// Autre champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private IModelPendu		modelPendu;


	// Initialisation du Controller
	
	public void initialize() {
		
		// Initialise les données du modèle
		modelPendu.retrouverPartie();
		
		
		// Data binding
		
		labelMessage.textProperty().bindBidirectional( modelPendu.messageProperty() );
		labelResultat.textProperty().bindBidirectional( modelPendu.resultatProperty() );
		imageView.imageProperty().bind( modelPendu.imageProperty() );
		
		ChangeListener<String> listenerLettresJouees =  
				( observable, oldValue, newValue ) -> {
					for ( Node node : paneBoutonsLettres.getChildren() ) {
						if ( node instanceof Button ) {
							node.setDisable( newValue.contains( ((Button) node).getText()) );
						}
					}
			};
		modelPendu.lettresJoueesProperty().addListener( listenerLettresJouees ) ;
		
		
		// Configure le comportement des boutons avec les lettres
		
		// Association de onActionBoutonLettre() + pas de focus
		for ( Node node : paneBoutonsLettres.getChildren() ) {
			if ( node instanceof Button ) {
				((Button)  node).setOnAction( this::onActionBoutonLettre );
				node.setFocusTraversable(false);
			}
		}

		
		// Initialisaiton de l'aaffichage
		listenerLettresJouees.changed( null, null, modelPendu.lettresJoueesProperty().get() );
	}
	
	
	// Actions
	
	@FXML
	private void doNouvellePartie() {
		modelPendu.nouvellePartie();
	}

	
	public void doTricher() {
		modelPendu.tricher();
	}

	
	@FXML
	private void doConfig() {
		modelPendu.preparerConfig();
		managerGui.showView( EnumView.PenduConfig );
	}
	
	@FXML
	private void doMenu() {
		managerGui.showView( EnumView.Menu );
	}
	

	// Gestion des évènements

	@FXML
	public void onActionBoutonLettre(ActionEvent event ) {
		if ( modelPendu.flagPartieFinieProperty().get() ) 
			return;
		Button button =  (Button) event.getSource();
		char lettre = button.getText().charAt(0);
		modelPendu.jouer(lettre);
	}
	
	@FXML
	public void onKeyPressed( KeyEvent event ) {
		char lettre = 0;
		if ( ! event.getText().isEmpty() ) {
			lettre = event.getText().toUpperCase().charAt(0);
		}
		if (  ( ! modelPendu.flagPartieFinieProperty().get() ) && 'A' <= lettre && lettre <= 'Z' ) {
			if ( modelPendu.lettresJoueesProperty().get().indexOf(lettre) < 0 )   {
				modelPendu.jouer(lettre);
			}
		}
	}
	
	
	@FXML
	private void onClick( MouseEvent event) {
		// Test double-clic
		if( event.getClickCount() == 2  && event.getButton() == MouseButton.PRIMARY ) {
			modelPendu.tricher();
		}
	}
	
}
