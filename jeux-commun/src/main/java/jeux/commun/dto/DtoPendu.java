package jeux.commun.dto;

import java.io.Serializable;

public class DtoPendu implements Serializable{


	// Enumératon
	public static enum EnumResponse { EN_COURS, GAGNE, PERDU }

	
	// Champs
	
	private String		idJoueur;
	
	private boolean		flagModeFacile;
	private int			nbErreursMaxi;
	
	private String		message;
	private int			nbErreursRestantes;
	private String		resultat;
	private String		lettresJouees;
	
	private EnumResponse	reponse;

	
	// Getters & Setters
	
	public String getIdJoueur() {
		return idJoueur;
	}

	public void setIdJoueur(String idJoueur) {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNbErreursRestantes() {
		return nbErreursRestantes;
	}

	public void setNbErreursRestantes(int nbErreursRestantes) {
		this.nbErreursRestantes = nbErreursRestantes;
	}

	public String getResultat() {
		return resultat;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public String getLettresJouees() {
		return lettresJouees;
	}

	public void setLettresJouees(String lettresJouees) {
		this.lettresJouees = lettresJouees;
	}

	public EnumResponse getReponse() {
		return reponse;
	}

	public void setReponse(EnumResponse reponse) {
		this.reponse = reponse;
	}
	
}
