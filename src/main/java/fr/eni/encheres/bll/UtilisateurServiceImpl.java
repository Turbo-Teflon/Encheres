package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

@Service
@Profile("prod")
public class UtilisateurServiceImpl implements UtilisateurService {
	private UtilisateurDAO utilisateurDAO;
	
	public UtilisateurServiceImpl (UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}
	
	@Override
	public void insert(Utilisateur utilisateur) {
		utilisateurDAO.insert(utilisateur);
		
	}

	@Override
	public Utilisateur selectById(long id) {
		return utilisateurDAO.selectById(id);
	}

	@Override
	public Utilisateur selectByPseudo(String pseudo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur selectByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Utilisateur> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Utilisateur login(String identifiant, String motDePasse) {
		// TODO Auto-generated method stub
		return null;
	}

}
