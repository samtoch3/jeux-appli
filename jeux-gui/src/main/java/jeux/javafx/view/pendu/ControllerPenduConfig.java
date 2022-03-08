package jeux.javafx.view.pendu;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import jeux.javafx.model.IModelPendu;
import jeux.javafx.view.EnumView;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;


@Component
@Scope( "prototype" )
public class ControllerPenduConfig extends Controller {
	
	
	// Composants de la vue

	@FXML
	private CheckBox			checkBoxModeFacile;

	@FXML
	private Spinner<Integer>	spinnerNbErreursMaxi;
	
	
	// Autre champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private IModelPendu		modelPendu;


	// Initialisation du Controller
	
	public void initialize() {
		
		// Configure le Spinner
		spinnerNbErreursMaxi.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory( 3, 15) );

		// Data binding
		checkBoxModeFacile.selectedProperty().bindBidirectional( modelPendu.flagModeFacileProperty() );
		spinnerNbErreursMaxi.getValueFactory().valueProperty().bindBidirectional( modelPendu.nbErreursMaxiProperty()  );
		
	}
	
	
	
	// Actions
	
	@FXML
	private void doValider() {
		modelPendu.enregistrerConfig();
		managerGui.showView( EnumView.PenduJeu );
	}

	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.PenduJeu );
	}
	
}
