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
	
	void selectVendeur(Article article);
}
