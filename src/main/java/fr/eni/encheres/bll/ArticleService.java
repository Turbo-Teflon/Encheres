package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

public interface ArticleService {
	void insert(Article article);

	Article selectById(long id);

	List<Article> selectAll();

	List<Article> selectByCategorie(long idCategorie);

	List<Article> selectByUtilisateur(long idUtilisateur);

	void update(Article article);

	void delete(long id);

	List<Article> selectEnCours();
	
	void setVendeurByArticle(Article article);
	
	void setBestEnchereByArticle(Article article);
	
	List<Article> selectEncheresOuvertes(long idCategorie, String nomArticle);
	
	List<Article> selectMesEncheres(long idUtilisateur, long idCategorie, String nomArticle);
	
	List<Article> selectMesEncheresRemportees(long idUtilisateur, long idCategorie, String nomArticle);
	
	List<Article> selectMesVentesEnCours(long idUtilisateur, long idCategorie, String nomArticle);
	
	List<Article> selectMesVentesNonDebutees(long idUtilisateur, long idCategorie, String nomArticle);
	
	List<Article> selectMesVentesTerminees(long idUtilisateur, long idCategorie, String nomArticle);
	
	void insertEnchere(Enchere enchere);
	
	void encherir( long idUtilisateur, long idArticle, int montantEnchere);
}
