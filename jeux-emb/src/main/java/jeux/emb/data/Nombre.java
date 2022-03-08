package jeux.emb.data;


public class Nombre {

	
	// Champs
	
	private String		idJoueur;

	private int			valeurMaxi;
	private int			nbEssaisMaxi;

	private int			nbEssaisRestants;
	private int			nombreMystere;

	private int			borneInf;
	private int			borneSup;
	private boolean		flagPartieGagnee;
	
	
	// Getters & Setters
	
	public String getIdJoueur() {
		return idJoueur;
	}

	public void setIdJoueur( String idJoueur ) {
		this.idJoueur = idJoueur;
	}

	public int getValeurMaxi() {
		return valeurMaxi;
	}

	public void setValeurMaxi(int valeurMaxi) {
		this.valeurMaxi = valeurMaxi;
	}

	public int getNbEssaisMaxi() {
		return nbEssaisMaxi;
	}

	public void setNbEssaisMaxi(int nbEssaisMaxi) {
		this.nbEssaisMaxi = nbEssaisMaxi;
	}

	public int getNbEssaisRestants() {
		return nbEssaisRestants;
	}

	public void setNbEssaisRestants(int nbEssaisRestants) {
		this.nbEssaisRestants = nbEssaisRestants;
	}

	public int getNombreMystere() {
		return nombreMystere;
	}

	public void setNombreMystere(int nombreMystere) {
		this.nombreMystere = nombreMystere;
	}

	public int getBorneInf() {
		return borneInf;
	}

	public void setBorneInf(int borneInf) {
		this.borneInf = borneInf;
	}

	public int getBorneSup() {
		return borneSup;
	}

	public void setBorneSup(int borneSup) {
		this.borneSup = borneSup;
	}

	public boolean isFlagPartieGagnee() {
		return flagPartieGagnee;
	}

	public void setFlagPartieGagnee(boolean flagPartieGagnee) {
		this.flagPartieGagnee = flagPartieGagnee;
	}

}
