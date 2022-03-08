package jeux.ejb.dao;

import jeux.ejb.data.Pendu;


public interface IDaoPendu {

	void	inserer( Pendu pendu );

	void 	modifier( Pendu pendu );

	Pendu 	retrouver( String idJoueur );

}
