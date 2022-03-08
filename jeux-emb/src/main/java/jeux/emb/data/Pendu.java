package jeux.emb.data;


public class Pendu {
	
	
	// Champs
	
	private String			idJoueur;

	private boolean			flagModeFacile;
	private int				nbErreursMaxi;
	
	private int				nbErreursRestantes;
	private String			motMystere;
	private String			resultat;
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
