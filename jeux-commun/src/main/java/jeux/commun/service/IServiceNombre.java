package jeux.commun.service;

import jeux.commun.dto.DtoNombre;
import jeux.commun.exception.ExceptionValidation;


public interface IServiceNombre {

	DtoNombre retrouver( String idJoueur );

	void modifierConfig( DtoNombre dto );

	DtoNombre nouvellePartie( String idJoueur );

	DtoNombre jouer( String idJoueur, int proposition ) throws ExceptionValidation;

	int tricher( String idJoueur );
	
}
