package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleService {
	void insert(Article article);

	Article selectById(int id);

	List<Article> selectAll();

	List<Article> selectByCategorie(int idCategorie);

	List<Article> selectByUtilisateur(int idUtilisateur);

	void update(Article article);

	void delete(int id);

	List<Article> selectEnCours();
}
