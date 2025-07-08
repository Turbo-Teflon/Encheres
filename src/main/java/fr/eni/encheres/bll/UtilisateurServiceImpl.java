package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

@Service
@Profile("prod")
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private final UtilisateurDAO utilisateurDAO;
	

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
	    this.utilisateurDAO = utilisateurDAO;
	    
	}
	
	@Override
	public void insert(Utilisateur utilisateur) {
		
		// 💾 Insertion
		utilisateurDAO.insert(utilisateur);
	}

	@Override
	public Utilisateur selectById(long id) {
		return utilisateurDAO.selectById(id);
	}

	@Override
	public Utilisateur selectByPseudo(String pseudo) {
		return utilisateurDAO.selectByPseudo(pseudo);
	}

	@Override
	public Utilisateur selectByEmail(String email) {
		return utilisateurDAO.selectByEmail(email);
	}

	@Override
	public List<Utilisateur> selectAll() {
		return utilisateurDAO.selectAll();
	}

	@Override
	public void update(Utilisateur utilisateur) {
		// TODO : penser à hasher ici si le mot de passe a changé
		utilisateurDAO.update(utilisateur);
	}

	@Override
	public void delete(long id) {
		utilisateurDAO.delete(id);
	}

}
