package jeux.javafx.view;

import jfox.javafx.view.IEnumView;
import jfox.javafx.view.View;


public enum EnumView implements IEnumView {

	
	// Valeurs
	
	Menu			( "menu/ViewMenu.fxml" ),
	NombreJeu		( "nombre/ViewNombreJeu.fxml" ),
	NombreConfig	( "nombre/ViewNombreConfig.fxml" ),
	PenduJeu		( "pendu/ViewPenduJeu.fxml" ),
	PenduConfig		( "pendu/ViewPenduConfig.fxml" ),
	;

	
	// Champs
	
	private final View	view;

	
	// Constructeurs
	
	EnumView( String path, boolean flagReuse ) {
		view = new View(path, flagReuse);
	}
	
	EnumView( String path ) {
		view = new View(path);
	}

	
	// Getters & setters
	
	@Override
	public View getView() {
		return view;
	}
}
