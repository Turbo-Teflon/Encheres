package fr.eni.encheres.bll.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;

@Profile("dev")
@Service
public class UtilisateurServiceBouchon implements UtilisateurService {

	private List<Utilisateur> utilisateurs = new ArrayList<>();

	public UtilisateurServiceBouchon() {
		// Données fictives pour tests
		utilisateurs.add(new Utilisateur(1, "Jdoe", "Doe", "John", "Jdoe@email.com", "0623456789",
				"1 rue A", "75001", "Paris", "mdp123", 100, false, true));

		utilisateurs.add(new Utilisateur(2, "GI-Joe", "GI", "Joe", "GI-Joe@email.com", "0687654321",
				"2 rue B", "29200", "Brest", "azerty", 200, false, false));
	}

	@Override
	public void insert(Utilisateur utilisateur) {
		utilisateur.setIdUtilisateur(utilisateurs.size() + 1);
		utilisateurs.add(utilisateur);
	}

	@Override
	public Utilisateur selectById(long id) {
		return utilisateurs.stream()
				.filter(u -> u.getIdUtilisateur() == id)
				.findFirst()
				.orElse(null);
	}

	@Override
	public Utilisateur selectByPseudo(String pseudo) {
		return utilisateurs.stream()
				.filter(u -> u.getPseudo().equalsIgnoreCase(pseudo))
				.findFirst()
				.orElse(null);
	}

	@Override
	public Utilisateur selectByEmail(String email) {
		return utilisateurs.stream()
				.filter(u -> u.getEmail().equalsIgnoreCase(email))
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Utilisateur> selectAll() {
		return new ArrayList<>(utilisateurs); // Copie défensive
	}

	@Override
	public void update(Utilisateur utilisateur) {
		delete(utilisateur.getIdUtilisateur());
		utilisateurs.add(utilisateur);
	}

	@Override
	public void delete(long id) {
		utilisateurs.removeIf(u -> u.getIdUtilisateur() == id);
	}

	/*@Override
	public Utilisateur login(String identifiant, String motDePasse) {
		return utilisateurs.stream()
				.filter(u -> (u.getPseudo().equalsIgnoreCase(identifiant)
							|| u.getEmail().equalsIgnoreCase(identifiant))
						&& u.getMotDePasse().equals(motDePasse))
				.findFirst()
				.orElse(null);
	}*/
}
