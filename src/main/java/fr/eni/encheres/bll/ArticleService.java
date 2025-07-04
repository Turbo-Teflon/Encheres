package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleService {
	void insert(Article article);

	Article selectById(long id);

	List<Article> selectAll();

	List<Article> selectByCategorie(long idCategorie);

	List<Article> selectByUtilisateur(long idUtilisateur);

	void update(Article article);

	void delete(long id);

	List<Article> selectEnCours();
	
	void readVendeurByArticle(Article article);
	
	void readBestEnchereByArticle(Article article);
	
	List<Article> encheresEnCours();
	
	List<Article> selectEncheresEnCoursFiltre(long idCategorie, String nomArticle);
}
