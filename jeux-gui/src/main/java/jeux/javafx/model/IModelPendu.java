package jeux.javafx.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public interface IModelPendu {

	BooleanProperty flagModeFacileProperty();

	Property<Integer> nbErreursMaxiProperty();

	StringProperty messageProperty();

	StringProperty resultatProperty();

	Property<Image> imageProperty();

	BooleanProperty flagPartieDebutProperty();

	BooleanProperty flagPartieFinieProperty();

	StringProperty lettresJoueesProperty();

	void preparerConfig();

	void enregistrerConfig();

	void nouvellePartie();

	void retrouverPartie();

	void jouer(char lettre);

	void tricher();

}