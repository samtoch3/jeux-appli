package jeux.javafx.view.nombre;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import jeux.javafx.model.IModelNombre;
import jeux.javafx.view.EnumView;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;


@Component
@Scope( "prototype" )
public class ControllerNombreConfig extends Controller {
	
	
	// Composants de la vue

	@FXML
	private TextField	textFieldValeurMaxi;

	@FXML
	private TextField	textFieldNbEssaisMaxi;
	
	
	// Autre champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private IModelNombre	modelNombre;


	// Initialisation du Controller
	
	public void initialize() {
		
		// Data binding
		textFieldValeurMaxi.textProperty().bindBidirectional( 
		    modelNombre.valeurMaxiProperty() );
		textFieldNbEssaisMaxi.textProperty().bindBidirectional(
		    modelNombre.nbEssaisMaxiProperty() );		
	}
	
	
	// Actions
	
	@FXML
	private void doValider() {
		modelNombre.enregistrerconfig();
		managerGui.showView( EnumView.NombreJeu ) ;
	}

	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.NombreJeu ) ;
	}

	
}
