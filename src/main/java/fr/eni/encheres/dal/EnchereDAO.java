package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Enchere;



public interface EnchereDAO {
	
	void insert(Enchere enchere);

	List<Enchere> selectByArticle(int idArticle);

	List<Enchere> selectByUtilisateur(int idUtilisateur);

	Enchere selectBestByArticle(int idArticle);

	void deleteByArticle(int idArticle);
	

	
}
