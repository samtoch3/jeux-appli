package jeux.emb.dao.mock;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import jeux.emb.dao.IDaoNombre;
import jeux.emb.data.Nombre;


@Component
public class DaoNombre implements IDaoNombre {

	
	// Champs
	
	private Map<String, Nombre> mapData = new HashMap<>();

	
	// Initialisation
	
	@PostConstruct
	public void init() {
		initialiserDonnees();
	}

	
	// Actions
	
	@Override
	public void inserer( Nombre nombre ) {
		mapData.put( nombre.getIdJoueur(), nombre);
	}

	
	@Override
	public void modifier( Nombre nombre ) {
		mapData.put( nombre.getIdJoueur(), nombre);
	}
	
	
	@Override
	public Nombre retrouver( String idJoueur ) {
		return mapData.get(idJoueur);
	}
	
	
	// Méthodes auxiliaires

	private void initialiserDonnees() {
		
		Nombre nombre;
		
		nombre = new Nombre();
		nombre.setIdJoueur( "XX" );
		nombre.setValeurMaxi( 32 );
		nombre.setNbEssaisMaxi( 5 );
		nombre.setNbEssaisRestants( 3 );
		nombre.setNombreMystere( 19 );
		nombre.setBorneInf(16);
		nombre.setBorneSup(24);
		inserer(nombre);
		
	}

}
