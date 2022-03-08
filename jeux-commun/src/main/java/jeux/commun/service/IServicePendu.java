package jeux.commun.service;

import jeux.commun.dto.DtoPendu;


public interface IServicePendu {

	DtoPendu retrouver( String idJoueur );

	void modifierConfig( DtoPendu dtoPendu );

	DtoPendu nouvellePartie( String idJoueur );

	DtoPendu jouer( String idJoueur, char lettre );

	String tricher( String idJoueur );

}
