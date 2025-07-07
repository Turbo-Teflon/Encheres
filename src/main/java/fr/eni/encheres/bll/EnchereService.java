package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EnchereService {

    List<Enchere> selectAll();

    List<Enchere> selectByArticle(long idArticle);

    List<Enchere> selectByUtilisateur(long idUtilisateur);

    void insert(Enchere enchere);
}
