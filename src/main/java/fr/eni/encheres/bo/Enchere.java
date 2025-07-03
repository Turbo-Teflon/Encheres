package fr.eni.encheres.bo;



import java.time.LocalDateTime;


public class Enchere {

    private Utilisateur utilisateur;
    private Article article;
    private LocalDateTime dateEnchere;
    private int montantEnchere;
    
    
	public Enchere() {

	}


	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}


	public Article getArticle() {
		return article;
	}


	public void setArticle(Article article) {
		this.article = article;
	}


	public LocalDateTime getDateEnchere() {
		return dateEnchere;
	}


	public void setDateEnchere(LocalDateTime dateEnchere) {
		this.dateEnchere = dateEnchere;
	}


	public int getMontantEnchere() {
		return montantEnchere;
	}


	public void setMontantEnchere(int montantEnchere) {
		this.montantEnchere = montantEnchere;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Enchere [utilisateur=");
		builder.append(utilisateur);
		builder.append(", article=");
		builder.append(article);
		builder.append(", dateEnchere=");
		builder.append(dateEnchere);
		builder.append(", montantEnchere=");
		builder.append(montantEnchere);
		builder.append("]");
		return builder.toString();
	}
	
	
    
}

