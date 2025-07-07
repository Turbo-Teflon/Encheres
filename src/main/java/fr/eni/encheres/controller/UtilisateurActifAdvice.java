package fr.eni.encheres.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import fr.eni.encheres.bo.Utilisateur;

@ControllerAdvice
public class UtilisateurActifAdvice {

    @ModelAttribute("utilisateur")
    public Utilisateur utilisateurActif() {
        return new Utilisateur(
            1, "jojo44", "Durand", "Jean", "jojo@email.com", "0600000000",
            "1 rue des Lilas", "44000", "Nantes", "azerty", 150, false,
            false // false = gaucher, true = droitier
        );
    }
}
