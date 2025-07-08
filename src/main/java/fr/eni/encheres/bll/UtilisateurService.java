package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;

@Service
public interface UtilisateurService {
	void insert(Utilisateur utilisateur);

	Utilisateur selectById(long id);

	Utilisateur selectByPseudo(String pseudo);

	Utilisateur selectByEmail(String email);

	List<Utilisateur> selectAll();

	void update(Utilisateur utilisateur);

	void delete(long id);

	
}
