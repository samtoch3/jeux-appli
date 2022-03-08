package jeux.emb.dao;

import jeux.emb.data.Pendu;


public interface IDaoPendu {

	void	inserer( Pendu pendu );

	void 	modifier( Pendu pendu );

	Pendu 	retrouver( String idJoueur );

}
