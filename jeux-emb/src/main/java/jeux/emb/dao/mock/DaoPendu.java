package jeux.emb.dao.mock;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import jeux.emb.dao.IDaoPendu;
import jeux.emb.data.Pendu;


@Component
public class DaoPendu implements IDaoPendu {

	
	// Champs
	
	private Map<String, Pendu> mapData = new HashMap<>();
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		initialiserDonnees();
	}

	
	// Actions
	
	@Override
	public void inserer( Pendu pendu ) {
		mapData.put( pendu.getIdJoueur(), pendu);
	}

	
	@Override
	public void modifier( Pendu pendu ) {
		mapData.put( pendu.getIdJoueur(), pendu);
	}
	
	
	@Override
	public Pendu retrouver( String idJoueur ) {
		return mapData.get(idJoueur);
	}
	
	
	// Méthodes auxiliaires

	private void initialiserDonnees() {
		
		Pendu pendu;
		
		pendu = new Pendu();
		pendu.setIdJoueur( "XX" );
		pendu.setFlagModeFacile(false);
		pendu.setNbErreursMaxi( 7 );
		pendu.setNbErreursRestantes( 5 );
		pendu.setMotMystere( "AUTOROUTE" );
		pendu.setResultat( "_ _ T O _ O _ T _" );
		pendu.setLettresJouees( "OTMP" );
		inserer(pendu);
		
	}

}
