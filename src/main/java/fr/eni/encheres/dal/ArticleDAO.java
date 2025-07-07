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
	
	List<Article> selectEncheresEnCours();
	
	List<Article> selectEncheresEnCoursFiltre(long idCategorie, String nomArticle);
}
