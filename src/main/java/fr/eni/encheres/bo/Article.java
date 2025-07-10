package fr.eni.encheres.bo;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Article implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -1572052415321189850L;
	
	private long idArticle;
    @NotBlank
    private String nomArticle;
    @NotBlank
    private String description;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
    @Min(0)
    private int miseAPrix;
    private int prixActuel;
    private String dernierEncherisseur;
    private int prixVente;
    private String etatVente;
    private Utilisateur utilisateur;
    private Categorie categorie;
    private Retrait retrait;
    private List<Enchere> encheres;
    
    
	public Article() {
		retrait = new Retrait();
		encheres = new ArrayList<Enchere>();
	}
	
	public Article(long id, String nom, String desc, int miseAPrix, int prixActuel, LocalDateTime debut, LocalDateTime fin) {
	    this();
		this.idArticle = id;
	    this.nomArticle = nom;
	    this.description = desc;
	    this.miseAPrix = miseAPrix;
	    this.prixActuel = prixActuel;
	    this.dateDebutEncheres = debut;
	    this.dateFinEncheres = fin;
	}



	public long getIdArticle() {
		return idArticle;
	}


	public void setIdArticle(long idArticle) {
		this.idArticle = idArticle;
	}


	public String getNomArticle() {
		return nomArticle;
	}


	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}


	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}


	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}


	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}


	public int getMiseAPrix() {
		return miseAPrix;
	}


	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}


	public int getPrixVente() {
		return prixVente;
	}


	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}


	public String getEtatVente() {
		return etatVente;
	}


	public void setEtatVente(String etatVente) {
		this.etatVente = etatVente;
	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Categorie getCategorie() {
		return categorie;
	}


	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}


	public Retrait getRetrait() {
		return retrait;
	}


	public void setRetrait(Retrait retrait) {
		this.retrait = retrait;
	}


	public List<Enchere> getEncheres() {
		return encheres;
	}


	public void setEncheres(List<Enchere> encheres) {
		this.encheres = encheres;
	}


	public int getPrixActuel() {
		return prixActuel;
	}


	public void setPrixActuel(int prixActuel) {
		this.prixActuel = prixActuel;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [idArticle=");
		builder.append(idArticle);
		builder.append(", ");
		if (nomArticle != null) {
			builder.append("nomArticle=");
			builder.append(nomArticle);
			builder.append(", ");
		}
		if (description != null) {
			builder.append("description=");
			builder.append(description);
			builder.append(", ");
		}
		if (dateDebutEncheres != null) {
			builder.append("dateDebutEncheres=");
			builder.append(dateDebutEncheres);
			builder.append(", ");
		}
		if (dateFinEncheres != null) {
			builder.append("dateFinEncheres=");
			builder.append(dateFinEncheres);
			builder.append(", ");
		}
		builder.append("miseAPrix=");
		builder.append(miseAPrix);
		builder.append(", prixActuel=");
		builder.append(prixActuel);
		builder.append(", prixVente=");
		builder.append(prixVente);
		builder.append(", ");
		if (etatVente != null) {
			builder.append("etatVente=");
			builder.append(etatVente);
			builder.append(", ");
		}
		if (utilisateur != null) {
			builder.append("utilisateur=");
			builder.append(utilisateur);
			builder.append(", ");
		}
		if (categorie != null) {
			builder.append("categorie=");
			builder.append(categorie);
			builder.append(", ");
		}
		if (retrait != null) {
			builder.append("retrait=");
			builder.append(retrait);
			builder.append(", ");
		}
		if (encheres != null) {
			builder.append("encheres=");
			builder.append(encheres);
		}
		builder.append("]");
		return builder.toString();
	}
	
	


	public String getDernierEncherisseur() {
		return dernierEncherisseur;
	}

	public void setDernierEncherisseur(String dernierEncherisseur) {
		this.dernierEncherisseur = dernierEncherisseur;
	}
	
	
	

}

