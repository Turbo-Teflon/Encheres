package fr.eni.encheres.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UtilisateurFormDto {

    private String pseudo;
    private String nom;
    private String prenom;
    @Email(message = "Veuillez entrer une adresse email valide.")
    @NotBlank(message = "L'email est obligatoire.")
    private String email;
    @Pattern(regexp = "\\d{10}", message = "Le numéro de téléphone doit contenir exactement 10 chiffres.")
    private String telephone;
    private String rue;
    private String codePostal;
    private String ville;
    private boolean main; 

    private String motDePasse;
    private String confirmation; 
    private String nouveauMotDePasse;   

    /*
     * DTO (Data Transfer Object) est utilisé pour le formulaire de création de compte.
     *
     * Cette classe permet de séparer les données du formulaire utilisateur de l'entité métier `Utilisateur`.
     * Elle évite de "polluer" l'entité avec des champs temporaires ou spécifiques à la vue (comme la confirmation du mot de passe).
     *
     * Avantages :
     * - Facilite la validation des champs sans impacter l'entité principale
     * - Améliore la sécurité en contrôlant précisément les données exposées ou modifiables
     * - Rend le code plus clair et maintenable en respectant le principe de séparation des responsabilités (SRP)
     *
     * Ce DTO est utilisé dans le contrôleur pour recevoir les données du formulaire,
     * puis converti manuellement en `Utilisateur` avant d’être sauvegardé.
     */

    
    

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
    
    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

}
