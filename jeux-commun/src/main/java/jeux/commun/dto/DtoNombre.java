package jeux.commun.dto;

import java.io.Serializable;


public class DtoNombre implements Serializable {

	
	// Enumératon
	public static enum EnumResponse { TROP_PETIT, TROP_GRAND, GAGNE, PERDU }

	
	// Champs
	
	private String		idJoueur;
	
	private int			valeurMaxi;
	private int			nbEssaisMaxi;
	private String		message;
	private int			nbEssaisRestants;
	private EnumResponse reponse;

	
	// Getters & Setters
	
	public String getIdJoueur() {
		return idJoueur;
	}

	public void setIdJoueur(String idJoueur) {
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNbEssaisRestants() {
		return nbEssaisRestants;
	}

	public void setNbEssaisRestants(int nbEssaisRestants) {
		this.nbEssaisRestants = nbEssaisRestants;
	}

	public EnumResponse getReponse() {
		return reponse;
	}

	public void setReponse(EnumResponse reponse) {
		this.reponse = reponse;
	}

}
