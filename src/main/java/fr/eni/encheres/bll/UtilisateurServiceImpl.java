package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	public Utilisateur login(String pseudo, String motDePasse) {
	    Utilisateur utilisateur = utilisateurDAO.selectByPseudo(pseudo);

	    if (utilisateur == null) {
	        throw new RuntimeException("Identifiant incorrect.");
	    }

	    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    if (!encoder.matches(motDePasse, utilisateur.getMotDePasse())) {
	        throw new RuntimeException("Mot de passe incorrect.");
	    }

	    return utilisateur;
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
	    System.out.println("Mot de passe dans service update : " + utilisateur.getMotDePasse());
	    utilisateurDAO.update(utilisateur);
	}

	@Override
	public void delete(long id) {
		utilisateurDAO.delete(id);
	}

}
