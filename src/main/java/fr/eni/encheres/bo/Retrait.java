package fr.eni.encheres.bo;



public class Retrait {

  

    private String rue;
    private String codePostal;
    private String ville;
    private Article article;

	public Retrait() {

	}
	public Retrait(String rue, String codePostal, String ville) {
	    this.rue = rue;
	    this.codePostal = codePostal;
	    this.ville = ville;
	}



	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

    
}
