package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.UtilisateurFormDto;
import jakarta.servlet.http.HttpSession;

@Controller

public class UtilisateurController {
	
	
	private CategorieService categorieService;
	private ArticleService articleService;
	
	
	public UtilisateurController (@Qualifier("categorieServiceBouchon") CategorieService categorieService,
		    @Qualifier("articleServiceBouchon") ArticleService articleService) {
		
		this.categorieService=categorieService;
		this.articleService=articleService;
	}
	@ModelAttribute("utilisateur")
	public Utilisateur utilisateurActif(HttpSession session) {
	    Object userInSession = session.getAttribute("utilisateur");
	    if (userInSession instanceof Utilisateur) {
	        return (Utilisateur) userInSession;
	    }
	    return null;
	}



	@GetMapping("/accueil")
	public String accueil(Model model) {	    
	    List<Categorie> categories = categorieService.selectAll();
	    model.addAttribute("categories", categories);
	    List<Article> articles = articleService.encheresEnCours(); 
	    model.addAttribute("articles", articles);   
	    return "accueil";
	}
	
	@GetMapping("/encheres/filtrer")
	public String filtrer(@RequestParam (name="categorie", defaultValue="0") long categorie, @RequestParam (name="nomArticle", defaultValue = "") String nomArticle, Model model) {
	    model.addAttribute("user", "jojo44");
	    List<Categorie> categories = categorieService.selectAll();
	    model.addAttribute("categories", categories);
	    List<Article> articles = articleService.selectEncheresEnCoursFiltre(categorie, nomArticle); 
	    model.addAttribute("articles", articles);
	    model.addAttribute("nomArticle", nomArticle);
	    model.addAttribute("categorie", categorie);
	    return "accueil";
	}

    @GetMapping("/connexion")
    public String connexion() {
        return "view-connexion-enchere";
    }

    @GetMapping("/creer-compte")
    public String creerCompteForm(Model model) {
        model.addAttribute("utilisateurFormDto", new UtilisateurFormDto());
        return "view-creer-compte-enchere";
    }



    @GetMapping("/profil")
    public String profil() {
        return "view-profil-enchere";
    }

    @GetMapping("/encherir")
    public String enchere() {
        return "view-achat-detail-enchere";
    }
    
    @GetMapping("/detail-vente")
    public String detailVente() {
        return "view-vente-detail-enchere"; 
    }
    @GetMapping("/deconnexion")
    public String Deconnexion(HttpSession session) {
        session.invalidate(); // Supprime toutes les données de session
        return "redirect:/accueil";
    }
    @PostMapping("/creer-compte")
    public String creerComptePost(
            @ModelAttribute UtilisateurFormDto formDto,
            Model model) {

        // Vérification que les mots de passe correspondent
        if (!formDto.getMotDePasse().equals(formDto.getConfirmation())) {
            model.addAttribute("erreur", "Les mots de passe ne correspondent pas.");
            return "view-creer-compte-enchere";
        }

        // Conversion du DTO vers l'entité métier Utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPseudo(formDto.getPseudo());
        utilisateur.setEmail(formDto.getEmail());
        utilisateur.setMotDePasse(formDto.getMotDePasse());
        
        // TODO : enregistrer l'utilisateur (via le service)
        return "redirect:/connexion";
    }
    @PostMapping("/connexion")
    public String traiterConnexion(
            @RequestParam("identifiant") String identifiant,
            @RequestParam("motDePasse") String motDePasse,
            HttpSession session,
            Model model) {

        // 🔐 TODO : valider identifiant / motDePasse (fictif ou via service)
        if ("admin".equals(identifiant) && "admin".equals(motDePasse)) {
        	Utilisateur utilisateur = new Utilisateur();
        	utilisateur.setPseudo(identifiant); // à remplacer plus tard par l’objet récupéré depuis ta BDD
        	session.setAttribute("utilisateur", utilisateur);

            return "redirect:/accueil";
        } else {
            model.addAttribute("erreur", "Identifiants invalides.");
            return "view-connexion-enchere";
        }
    }


}