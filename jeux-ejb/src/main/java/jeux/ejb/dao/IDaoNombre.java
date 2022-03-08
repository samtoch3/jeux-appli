package jeux.ejb.dao;

import jeux.ejb.data.Nombre;


public interface IDaoNombre {

	void	inserer( Nombre nombre );

	void	modifier( Nombre nombre );

	Nombre 	retrouver( String idJoueur );

}
