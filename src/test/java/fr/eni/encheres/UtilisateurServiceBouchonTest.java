package fr.eni.encheres;

// Importation des assertions JUnit pour valider les tests
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bll.mock.UtilisateurServiceBouchon;
import fr.eni.encheres.bo.Utilisateur;

@SpringBootTest
class UtilisateurServiceBouchonTest {

    // Logger pour afficher des messages de debug dans la console (au lieu de System.out)
    private static final Logger logger = LoggerFactory.getLogger(UtilisateurServiceBouchonTest.class);

    private UtilisateurService service;

    // Cette méthode s’exécute AVANT chaque test → ici on crée un service de test (le bouchon)
    @BeforeEach
    void setUp() {
        service = new UtilisateurServiceBouchon(); // Instanciation du faux service (mock/bouchon)
    }

    /*@Test
    void testLoginOK() {
        // On teste une connexion avec des identifiants valides
        Utilisateur u = service.login("jdoe", "mdp123");

        // On vérifie que le login a fonctionné : l'utilisateur ne doit pas être null
        assertNotNull(u, "L'utilisateur ne devrait pas être nul");

        // On affiche le prénom de l'utilisateur connecté
        logger.info("Connexion réussie : {}", u.getPrenom());
    }

    @Test
    void testLoginKO() {
        // On teste une connexion avec de mauvais identifiants
        Utilisateur u = service.login("badlogin", "badpwd");

        // On s’attend à ce que le login échoue → donc l’utilisateur doit être null
        assertNull(u, "L'utilisateur devrait être nul avec des identifiants incorrects");

        logger.info("Connexion échouée comme prévu.");
    }*/

    @Test
    void testInsertEtSelectByPseudo() {
        // On crée un nouvel utilisateur
        Utilisateur nouvel = new Utilisateur(
            0,                          // ID (sera mis à jour dans le bouchon)
            "vicky",                   // Pseudo
            "Martin", "Victoria",      // Nom et prénom
            "vicky@email.com",         // Email
            "0642424242",              // Téléphone
            "3 rue C", "29650", "Guerlesquin", // Adresse
            "1234",                    // Mot de passe
            150,                       // Crédit
            false,                     // Pas admin
            true                       // Main = droitier
        );

        // On insère le nouvel utilisateur dans le système
        service.insert(nouvel);

        // On tente de récupérer l’utilisateur par son pseudo
        Utilisateur recupere = service.selectByPseudo("vicky");

        // On vérifie qu’on a bien retrouvé un utilisateur
        assertNotNull(recupere, "L'utilisateur inséré devrait être retrouvé");

        // On vérifie que le prénom correspond à ce qu’on attend
        assertEquals("Victoria", recupere.getPrenom(), "Le prénom ne correspond pas");

        logger.info("Utilisateur inséré et retrouvé : {}", recupere);
    }
}
