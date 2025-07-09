package fr.eni.encheres.bo;


import java.time.LocalDateTime;
import java.util.List;

public class Article {


    private long idArticle;
    private String nomArticle;
    private String description;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
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
	}
	
	public Article(long id, String nom, String desc, int miseAPrix, int prixActuel, LocalDateTime debut, LocalDateTime fin) {
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

	public String getDernierEncherisseur() {
		return dernierEncherisseur;
	}

	public void setDernierEncherisseur(String dernierEncherisseur) {
		this.dernierEncherisseur = dernierEncherisseur;
	}
	
	
	

}

