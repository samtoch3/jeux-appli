package jeux.javafx.view.nombre;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jeux.javafx.model.IModelNombre;
import jeux.javafx.view.EnumView;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;


@Component
@Scope( "prototype" )
public class ControllerNombreJeu extends Controller {
	
	
	// Composants de la vue
	
	@FXML
	private Label		labelMessage;
	
	@FXML
	private Label		labelNbEssaisRestants;

	@FXML
	private TextField	textFieldProposition;
	
	@FXML
	private Button		buttonJouer;
	
	
	// Autre champs
	
	@Inject
	private IManagerGui	managerGui;
	@Inject
	private IModelNombre	modelNombre;


	// Initialisation du Controller
	
	public void initialize() {
		
		// Initialise les données du modèle
		modelNombre.retrouverPartie();
		
		// Data binding
		labelMessage.textProperty().bind( modelNombre.messageProperty() );
		labelNbEssaisRestants.textProperty().bind( modelNombre.nbEssaisRestantsProperty() );
		textFieldProposition.textProperty().bindBidirectional( modelNombre.propositionProperty() );
		buttonJouer.disableProperty().bind( modelNombre.flagPartieFinieProperty() );
	}
	
	
	// Actions
	
	@FXML
	private void doNouvellePartie() {
		modelNombre.nouvellePartie();
	}

	
	@FXML
	private void doJouer() {
		modelNombre.jouer();
	}
	
	@FXML
	private void doConfig() {
		modelNombre.preparerconfig();
		managerGui.showView( EnumView.NombreConfig );;;
	}
	
	@FXML
	private void doMenu() {
		managerGui.showView( EnumView.Menu );;;
	}
	
	
	public void doTricher() {
		modelNombre.tricher();
	}
	

	// Gestion des évènements
	
	@FXML
	private void onClick( MouseEvent event) {
		// Test double-clic
		if( event.getClickCount() == 2  && event.getButton() == MouseButton.PRIMARY ) {
			doTricher();
		}
	}
	
}
