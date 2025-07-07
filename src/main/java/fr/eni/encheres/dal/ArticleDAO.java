package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {
	
	void insert(Article article);

	Article selectById(long id);

	List<Article> selectAll();

	List<Article> selectByCategorie(long idCategorie);

	List<Article> selectByUtilisateur(long idUtilisateur);

	int update(Article article);

	int delete(long id);

	List<Article> selectEnCours();
	
	List<Article> selectEncheresOuvertes(long idCategorie, String nomArticle);
	
	List<Article> selectEncheresTerminees(long idCategorie, String nomArticle);
	
	List<Article> selectEncheresNonDebutees(long idCategorie, String nomArticle);
}
