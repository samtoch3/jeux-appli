package jeux.emb.dao;

import jeux.emb.data.Nombre;


public interface IDaoNombre {

	void	inserer( Nombre nombre );

	void	modifier( Nombre nombre );

	Nombre 	retrouver( String idJoueur );

}
