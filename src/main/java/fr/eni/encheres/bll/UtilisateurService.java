package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {
	void insert(Utilisateur utilisateur);

	Utilisateur selectById(int id);

	Utilisateur selectByPseudo(String pseudo);

	Utilisateur selectByEmail(String email);

	List<Utilisateur> selectAll();

	void update(Utilisateur utilisateur);

	void delete(int id);

	Utilisateur login(String identifiant, String motDePasse);
}
