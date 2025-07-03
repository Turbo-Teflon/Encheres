package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Enchere;



public interface EnchereDAO {
	
	void insert(Enchere enchere);

	List<Enchere> selectByArticle(long idArticle);

	List<Enchere> selectByUtilisateur(long idUtilisateur);

	Enchere selectBestByArticle(long idArticle);

	void deleteByArticle(long idArticle);
	

	
}
