package fr.eni.encheres;

// Importation des assertions pour vérifier les résultats attendus
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.mock.UtilisateurDAOBouchon;

@SpringBootTest
class UtilisateurDAOBouchonTest {

    // Création d’un logger pour afficher des messages dans la console
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurDAOBouchonTest.class);

    private UtilisateurDAOBouchon dao;

    // Avant chaque test, on réinitialise la "base de données" simulée avec 3 utilisateurs
    @BeforeEach
    void setUp() {
        dao = new UtilisateurDAOBouchon();

        // On vide la liste statique avant de la remplir (sinon les tests s'accumuleraient)
        UtilisateurDAOBouchon.lstUtilisateur.clear();

        // On crée 3 utilisateurs de base
        Utilisateur u1 = new Utilisateur(1, "DUDU", "Durand", "Eric", "Durand_eric@example.com",
            "0601020304", "10 rue des Lilas", "75000", "Paris", "mdpEric", 100, false, true);

        Utilisateur u2 = new Utilisateur(2, "victoria29", "Martin", "Victoria", "Martin_victoria@example.com",
            "0604050607", "12 avenue Mozart", "44000", "Nantes", "mdpVictoria", 100, false, false);

        Utilisateur u3 = new Utilisateur(3, "CoquinouDu69", "Mathieu", "François", "CoquinouDu69@example.com",
            "0611223344", "5 place Gutenberg", "69000", "Lyon", "super69", 100, false, true);

        // On les ajoute à la liste simulée
        UtilisateurDAOBouchon.lstUtilisateur.addAll(List.of(u1, u2, u3));
    }

    @Test
    void testSelectAllEtInsert() {
        // On récupère la liste actuelle des utilisateurs
        List<Utilisateur> usersAvant = new ArrayList<>(dao.selectAll());

        logger.info("Utilisateurs initiaux : {}", usersAvant.size());

        // On crée un nouvel utilisateur à insérer
        Utilisateur nouveau = new Utilisateur();
        nouveau.setPseudo("gueudin");
        nouveau.setNom("Tesh");
        nouveau.setPrenom("Gueudin");
        nouveau.setEmail("gueudin@example.com");
        nouveau.setTelephone("0600000000");
        nouveau.setRue("Rue des bourrins");
        nouveau.setCodePostal("12345");
        nouveau.setVille("Furytown");
        nouveau.setMotDePasse("gueudinPower");
        nouveau.setCredit(500);
        nouveau.setAdministrateur(false);
        nouveau.setMain(true);

        // On insère le nouvel utilisateur
        dao.insert(nouveau);

        // On vérifie que la taille de la liste a augmenté de 1
        List<Utilisateur> usersApres = dao.selectAll();
        assertEquals(usersAvant.size() + 1, usersApres.size());

        logger.info("Utilisateur inséré avec succès : {}", nouveau.getPseudo());
    }

    @Test
    void testUpdate() {
        // On insère un utilisateur temporaire
        Utilisateur u = new Utilisateur();
        u.setPseudo("tempModif");
        u.setNom("Test");
        u.setPrenom("Avant");
        u.setEmail("modif@test.com");
        u.setTelephone("0600000000");
        u.setRue("Rue X");
        u.setCodePostal("12345");
        u.setVille("Testville");
        u.setMotDePasse("modif123");
        u.setCredit(100);
        u.setAdministrateur(false);
        u.setMain(true);

        dao.insert(u);
        long id = u.getIdUtilisateur(); // On récupère l’ID auto-généré

        // On modifie son pseudo
        u.setPseudo("apresModif");
        dao.update(u);

        // On vérifie que le pseudo a bien été modifié
        Utilisateur updated = dao.selectById(id);
        assertEquals("apresModif", updated.getPseudo());

        logger.info("Pseudo modifié avec succès : {}", updated.getPseudo());
    }

    @Test
    void testDelete() {
        // Création et insertion d’un utilisateur temporaire
        Utilisateur u = new Utilisateur();
        u.setPseudo("tempUser");
        u.setEmail("temp@user.com");
        u.setMotDePasse("123");
        u.setNom("Test");
        u.setPrenom("À Supprimer");
        u.setTelephone("0000000000");
        u.setRue("Rue X");
        u.setCodePostal("99999");
        u.setVille("Testville");
        u.setCredit(100);
        u.setAdministrateur(false);
        u.setMain(false);

        dao.insert(u);
        Long id = u.getIdUtilisateur();

        // Vérifie que l’utilisateur est bien présent
        assertNotNull(dao.selectById(id));
        logger.info("Utilisateur à supprimer : {}", u.getPseudo());

        // Supprime l’utilisateur
        dao.delete(id);

        // Vérifie que l’utilisateur n’existe plus (attend une exception)
        Exception exception = assertThrows(RuntimeException.class, () -> dao.selectById(id));
        logger.info("Suppression confirmée pour l’utilisateur id={} : {}", id, exception.getMessage());
    }

    @Test
    void testInsertPseudoDoublon() {
        // Test d'insertion avec un pseudo déjà existant
        Utilisateur doublon = new Utilisateur();
        doublon.setPseudo("DUDU"); // déjà utilisé dans setUp()
        doublon.setEmail("nouveau@example.com");
        doublon.setMotDePasse("123");

        // On s'attend à une exception
        Exception exception = assertThrows(RuntimeException.class, () -> dao.insert(doublon));
        logger.info("Erreur attendue pour pseudo en doublon : {}", exception.getMessage());
    }

    @Test
    void testInsertEmailDoublon() {
        // Test d'insertion avec un email déjà utilisé
        Utilisateur doublon = new Utilisateur();
        doublon.setPseudo("nouveauPseudo");
        doublon.setEmail("Martin_victoria@example.com"); // déjà utilisé
        doublon.setMotDePasse("123");

        // On s'attend aussi à une exception
        Exception exception = assertThrows(RuntimeException.class, () -> dao.insert(doublon));
        logger.info("Erreur attendue pour email en doublon : {}", exception.getMessage());
    }      

    @Test
    void testSelectByPseudoEtEmail() {
        // Vérifie qu'on retrouve bien les utilisateurs par pseudo/email
        Utilisateur u1 = dao.selectByPseudo("DUDU");
        assertNotNull(u1);
        logger.info("Pseudo DUDU -> {} {}", u1.getPrenom(), u1.getNom());

        Utilisateur u2 = dao.selectByEmail("Martin_victoria@example.com");
        assertNotNull(u2);
        logger.info("Email Martin_victoria@example.com -> {} {}", u2.getPrenom(), u2.getNom());
    }
}
