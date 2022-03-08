package jeux.ejb.dao.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeux.ejb.dao.IDaoPendu;
import jeux.ejb.data.Pendu;

@Stateless
@Local
public class DaoPendu implements IDaoPendu {

	@PersistenceContext(unitName="jeux")
	private EntityManager em;

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public void inserer(Pendu pendu) {
		em.persist(pendu);
		em.flush();
		
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public void modifier(Pendu pendu) {
		em.merge(pendu);
		
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.NOT_SUPPORTED )
	public Pendu retrouver(String idJoueur) {
		return em.find(Pendu.class, idJoueur);
	}
	


}
