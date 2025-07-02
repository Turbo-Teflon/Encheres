package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;

public interface RetraitDAO {
	void insert(Retrait retrait);

	Retrait selectByArticle(int idArticle);

	void update(Retrait retrait);

	void deleteByArticle(int idArticle);
}
