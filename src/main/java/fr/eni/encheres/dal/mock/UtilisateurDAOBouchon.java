
package fr.eni.encheres.dal.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;

@Repository("utilisateurDAOBouchon")
public class UtilisateurDAOBouchon implements UtilisateurDAO{
	
	public static List<Utilisateur> lstUtilisateur;
	
	static {
	    lstUtilisateur = new ArrayList<>();

	    Utilisateur u1 = new Utilisateur();
	    u1.setIdUtilisateur(1);
	    u1.setPseudo("DUDU");
	    u1.setNom("Durand");
	    u1.setPrenom("Eric");
	    u1.setEmail("Durand_eric@example.com");
	    u1.setTelephone("0601020304");
	    u1.setRue("10 rue des Lilas");
	    u1.setCodePostal("75000");
	    u1.setVille("Paris");
	    u1.setMotDePasse("mdpEric");
	    u1.setCredit(100);
	    u1.setAdministrateur(false);
	    u1.setMain(true);

	    Utilisateur u2 = new Utilisateur();
	    u2.setIdUtilisateur(2);
	    u2.setPseudo("victoria29");
	    u2.setNom("Martin");
	    u2.setPrenom("Victoria");
	    u2.setEmail("Martin_victoria@example.com");
	    u2.setTelephone("0604050607");
	    u2.setRue("12 avenue Mozart");
	    u2.setCodePostal("44000");
	    u2.setVille("Nantes");
	    u2.setMotDePasse("mdpVictoria");
	    u2.setCredit(100);
	    u2.setAdministrateur(false);
	    u2.setMain(false);

	    Utilisateur u3 = new Utilisateur();
	    u3.setIdUtilisateur(3);
	    u3.setPseudo("CoquinouDu69");
	    u3.setNom("Mathieu");
	    u3.setPrenom("François");
	    u3.setEmail("CoquinouDu69@example.com");
	    u3.setTelephone("0611223344");
	    u3.setRue("5 place Gutenberg");
	    u3.setCodePostal("69000");
	    u3.setVille("Lyon");
	    u3.setMotDePasse("super69");
	    u3.setCredit(100);
	    u3.setAdministrateur(false);
	    u3.setMain(true);

	    lstUtilisateur.add(u1);
	    lstUtilisateur.add(u2);
	    lstUtilisateur.add(u3);
	}
	@Override
	public int insert(Utilisateur utilisateur) {
	    // Vérifier doublon pseudo
	    boolean pseudoExiste = lstUtilisateur.stream()
	            .anyMatch(u -> u.getPseudo().equals(utilisateur.getPseudo()));

	    // Vérifier doublon email
	    boolean emailExiste = lstUtilisateur.stream()
	            .anyMatch(u -> u.getEmail().equals(utilisateur.getEmail()));

	    if (pseudoExiste) {
	        throw new RuntimeException("Le pseudo est déjà utilisé : " + utilisateur.getPseudo());
	    }

	    if (emailExiste) {
	        throw new RuntimeException("L’email est déjà utilisé : " + utilisateur.getEmail());
	    }

	    // Génération de l’ID
	    long maxId = lstUtilisateur.stream()
	            .mapToLong(Utilisateur::getIdUtilisateur)
	            .max()
	            .orElse(0);

	    utilisateur.setIdUtilisateur(maxId + 1);
	    lstUtilisateur.add(utilisateur);
	    return 1;
	}

	@Override
	public Utilisateur selectById(long id) {
		// On impose la casse pour éviter toute confusion (ex: "DUDU" ≠ "dudu")
	    // Lève une exception si aucun utilisateur trouvé		
	    return lstUtilisateur.stream()
	            .filter(u -> u.getIdUtilisateur() == id)
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé avec l’ID : " + id));
	}

	@Override
	public Utilisateur selectByPseudo(String pseudo) {
	    // On impose la casse pour éviter toute confusion (ex: "DUDU" ≠ "dudu")
	    // Lève une exception si aucun utilisateur trouvé
	    return lstUtilisateur.stream()
	            .filter(u -> u.getPseudo().equals(pseudo))
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec le pseudo : " + pseudo));
	}

	@Override
	public Utilisateur selectByEmail(String email) {
		// On impose la casse pour éviter toute confusion (ex: "DUDU" ≠ "dudu")
	    // Lève une exception si aucun utilisateur trouvé		
	    return lstUtilisateur.stream()
	            .filter(u -> u.getEmail().equals(email)) // casse respectée
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé avec l’email : " + email));
	}

	@Override
	public List<Utilisateur> selectAll() {
	    return lstUtilisateur;
	}

	@Override
	public void update(Utilisateur utilisateur) {
	    for (int i = 0; i < lstUtilisateur.size(); i++) {
	        if (lstUtilisateur.get(i).getIdUtilisateur() == utilisateur.getIdUtilisateur()) {
	            lstUtilisateur.set(i, utilisateur);
	            return;
	        }
	    }
	    //Prévient d'une exception en cas d'utilisateur non trouvé.
	    throw new RuntimeException("Utilisateur non trouvé pour mise à jour");
	}

	@Override
	public int delete(long id) {
	    boolean removed = lstUtilisateur.removeIf(u -> u.getIdUtilisateur() == id);
	    if (!removed) {
	        throw new RuntimeException("Utilisateur à supprimer non trouvé : id " + id);
	    }
	    return 1;
	}

}

