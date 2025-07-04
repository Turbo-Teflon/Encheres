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
	public String utilisateurActif(HttpSession session) {
	    return (String) session.getAttribute("utilisateur");
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
        return "view-connection-enchere";
    }

    @GetMapping("/creer-compte")
    public String afficherFormulaireCreation(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
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
            @ModelAttribute Utilisateur utilisateur,
            @RequestParam("confirmation") String confirmation,
            Model model) {

        // Vérification que les mots de passe correspondent
        if (!utilisateur.getMotDePasse().equals(confirmation)) {
            model.addAttribute("erreur", "Les mots de passe ne correspondent pas.");
            return "view-creer-compte-enchere"; // on renvoie le formulaire avec le message d'erreur
        }

        // TODO : enregistrement de l'utilisateur
        return "redirect:/connexion";
    }

}