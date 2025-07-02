package fr.eni.encheres;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.mock.UtilisateurDAOBouchon;

public class TestUtilisateurDAOBouchon {

    public static void main(String[] args) {

        UtilisateurDAOBouchon dao = new UtilisateurDAOBouchon();

        System.out.println("---- Utilisateurs existants ----");
        dao.selectAll().forEach(u -> System.out.println(u.getIdUtilisateur() + " - " + u.getPseudo()));

        // 🔹 Insertion
        System.out.println("\n---- Insertion d’un nouveau utilisateur ----");

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
        nouveau.setMain("droitier");

        dao.insert(nouveau);

        System.out.println("\n---- Après insertion ----");
        dao.selectAll().forEach(u -> System.out.println(u.getIdUtilisateur() + " - " + u.getPseudo()));

        // 🔹 Update
        System.out.println("\n---- Modification du pseudo de l’utilisateur 4 ----");
        Utilisateur modif = dao.selectById(4);
        modif.setPseudo("gueudinOfficiel");
        dao.update(modif);

        System.out.println("\n---- Après modification ----");
        dao.selectAll().forEach(u -> System.out.println(u.getIdUtilisateur() + " - " + u.getPseudo()));

        // 🔹 Delete
        System.out.println("\n---- Suppression de l’utilisateur 4 ----");
        dao.delete(4);

        System.out.println("\n---- Après suppression ----");
        dao.selectAll().forEach(u -> System.out.println(u.getIdUtilisateur() + " - " + u.getPseudo()));
        
        System.out.println("\n---- Test d'insertion avec un pseudo déjà utilisé ----");

        try {
            Utilisateur doublon = new Utilisateur();
            doublon.setPseudo("DUDU"); // pseudo déjà pris
            doublon.setEmail("nouveau@example.com");
            doublon.setMotDePasse("123");
            doublon.setNom("Test");
            doublon.setPrenom("Erreur");
            doublon.setTelephone("0000000000");
            doublon.setRue("Rue X");
            doublon.setCodePostal("99999");
            doublon.setVille("Testville");
            doublon.setCredit(100);
            doublon.setAdministrateur(false);
            doublon.setMain("droitier");

            dao.insert(doublon);

        } catch (RuntimeException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }

        System.out.println("\n---- Test d'insertion avec un email déjà utilisé ----");

        try {
            Utilisateur doublonEmail = new Utilisateur();
            doublonEmail.setPseudo("nouveauPseudo");
            doublonEmail.setEmail("Martin_victoria@example.com"); // email déjà pris
            doublonEmail.setMotDePasse("123");
            doublonEmail.setNom("Test");
            doublonEmail.setPrenom("Erreur");
            doublonEmail.setTelephone("0000000000");
            doublonEmail.setRue("Rue X");
            doublonEmail.setCodePostal("99999");
            doublonEmail.setVille("Testville");
            doublonEmail.setCredit(100);
            doublonEmail.setAdministrateur(false);
            doublonEmail.setMain("gaucher");

            dao.insert(doublonEmail);

        } catch (RuntimeException e) {
            System.out.println("Erreur attendue : " + e.getMessage());
        }
        
        
        
        System.out.println("\n---- Test du login par pseudo ----");
        Utilisateur u1 = dao.login("DUDU", "mdpEric");
        System.out.println(u1 != null ? "Connexion OK : " + u1.getPrenom() : "Échec de connexion");

        System.out.println("\n---- Test du login par email ----");
        Utilisateur u2 = dao.login("Martin_victoria@example.com", "mdpVictoria");
        System.out.println(u2 != null ? "Connexion OK : " + u2.getPrenom() : "Échec de connexion");

        System.out.println("\n---- Test du login incorrect ----");
        Utilisateur u3 = dao.login("victoria29", "mauvaisMotDePasse");
        System.out.println(u3 != null ? "Connexion OK : " + u3.getPrenom() : "Échec de connexion");
        
        System.out.println("\n---- Test selectByPseudo(\"DUDU\") ----");
        Utilisateur dudu = dao.selectByPseudo("DUDU");
        System.out.println(dudu.getPrenom() + " " + dudu.getNom());

        System.out.println("\n---- Test selectByEmail(\"Martin_victoria@example.com\") ----");
        Utilisateur victoria = dao.selectByEmail("Martin_victoria@example.com");
        System.out.println(victoria.getPrenom() + " " + victoria.getNom());
    }
    
}