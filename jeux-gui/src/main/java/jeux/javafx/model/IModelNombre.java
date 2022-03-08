package jeux.javafx.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;


public interface IModelNombre {

	StringProperty valeurMaxiProperty();

	StringProperty nbEssaisMaxiProperty();

	StringProperty nbEssaisRestantsProperty();

	StringProperty messageProperty();

	StringProperty propositionProperty();

	BooleanProperty flagPartieFinieProperty();

	void preparerconfig();

	void enregistrerconfig();

	void nouvellePartie();

	void retrouverPartie();

	void jouer();

	void tricher();

}