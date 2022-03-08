package jeux.ejb.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="nombre")
public class Nombre {

	
	// Champs
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idjoueur")
	private String		idJoueur;

	@Column(name="valeurmaxi")
	private int			valeurMaxi;
	
	@Column(name="nbessaismaxi")
	private int			nbEssaisMaxi;

	@Column(name="nbessaisrestants")
	private int			nbEssaisRestants;
	
	@Column(name="nombremystere")
	private int			nombreMystere;

	@Column(name="borneinf")
	private int			borneInf;
	
	@Column(name="bornesup")
	private int			borneSup;
	
	@Column(name="flagpartiegagnee")
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
