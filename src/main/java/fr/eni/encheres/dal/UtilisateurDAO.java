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
	
	int delete(long id);
	
	boolean isAdmin(Utilisateur u);
	
	void updateRole(long id, String role);
	
	void insertRole(long id, String role);


	
}
