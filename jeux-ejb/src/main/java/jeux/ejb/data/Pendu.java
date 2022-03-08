package jeux.ejb.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="pendu")
public class Pendu {
	
	
	// Champs
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idjoueur")
	private String			idJoueur;

	@Column(name="flagmodefacile")
	private boolean			flagModeFacile;
	
	@Column(name="nberreursmaxi")
	private int				nbErreursMaxi;
	
	@Column(name="nberreursrestantes")
	private int				nbErreursRestantes;
	
	@Column(name="motmystere")
	private String			motMystere;
	
	@Column(name="resultat")
	private String			resultat;
	
	@Column(name="lettresjouees")
	private String			lettresJouees;
	
	
	// Getters & Setters
	
	public String getIdJoueur() {
		return idJoueur;
	}
	
	public void setIdJoueur( String idJoueur ) {
		this.idJoueur = idJoueur;
	}

	public boolean isFlagModeFacile() {
		return flagModeFacile;
	}

	public void setFlagModeFacile(boolean flagModeFacile) {
		this.flagModeFacile = flagModeFacile;
	}

	public int getNbErreursMaxi() {
		return nbErreursMaxi;
	}

	public void setNbErreursMaxi(int nbErreursMaxi) {
		this.nbErreursMaxi = nbErreursMaxi;
	}

	public String getMotMystere() {
		return motMystere;
	}

	public void setMotMystere(String motMystere) {
		this.motMystere = motMystere;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public int getNbErreursRestantes() {
		return nbErreursRestantes;
	}

	public void setNbErreursRestantes(int nbErreursRestantes) {
		this.nbErreursRestantes = nbErreursRestantes;
	}

	public String getLettresJouees() {
		return lettresJouees;
	}

	public void setLettresJouees(String lettresJouees) {
		this.lettresJouees = lettresJouees;
	}

}
