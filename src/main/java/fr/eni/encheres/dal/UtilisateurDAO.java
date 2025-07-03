package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	int insert(Utilisateur utilisateur);

	Utilisateur selectById(long id);

	Utilisateur selectByPseudo(String pseudo);

	Utilisateur selectByEmail(String email);

	List<Utilisateur> selectAll();

	void update(Utilisateur utilisateur);
	
	Utilisateur login(String identifiant, String motDePasse);
	
	int delete(long id);


	
}
