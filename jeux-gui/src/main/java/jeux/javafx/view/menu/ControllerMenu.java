package jeux.javafx.view.menu;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import jeux.javafx.view.EnumView;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;


@Component
@Scope( "prototype" )
public class ControllerMenu extends Controller {
	
	
	// Champs
	
	@Inject
	private IManagerGui		managerGui;
	
	
	// Actions
	
	@FXML
	private void doNombreMystere() {
		managerGui.showView( EnumView.NombreJeu ) ;
	}
	
	@FXML
	private void doPendu() {
		managerGui.showView( EnumView.PenduJeu ) ;
	}

	@FXML
	private void doSudoku() {
	}
	
	@FXML
	private void doQuitter() {
		managerGui.exit();
	}
	
}
