package fr.eni.encheres.bo;




import java.util.List;


public class Categorie {


    private long idCategorie;
    private String libelle;
    private List<Article> articles;


	public Categorie() {
	}


	public long getIdCategorie() {
		return idCategorie;
	}


	public void setIdCategorie(long idCategorie) {
		this.idCategorie = idCategorie;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public List<Article> getArticles() {
		return articles;
	}


	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}


    
}
