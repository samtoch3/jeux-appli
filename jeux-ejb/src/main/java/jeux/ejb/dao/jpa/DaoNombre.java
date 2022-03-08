package jeux.ejb.dao.jpa;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jeux.ejb.dao.IDaoNombre;
import jeux.ejb.data.Nombre;

@Stateless
@Local
public class DaoNombre implements IDaoNombre {

	@PersistenceContext(unitName="jeux")
	private EntityManager em;
	
	
	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public void inserer(Nombre nombre) {
		em.persist(nombre);
		em.flush();
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.MANDATORY )
	public void modifier(Nombre nombre) {
		em.merge(nombre);
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.NOT_SUPPORTED )
	public Nombre retrouver(String idJoueur) {
		return em.find(Nombre.class, idJoueur);
	}

}
