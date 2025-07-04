package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.CategorieServiceImpl;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategorieDAO;

@Controller

public class PageTestController {
	
	
	private CategorieService categorieService;
	private ArticleService articleService;
	
	
	public PageTestController ( CategorieService categorieService, ArticleService articleService) {
		
		this.categorieService=categorieService;
		this.articleService=articleService;
	}

	@GetMapping("/accueil")
	public String accueil(Model model) {
	    model.addAttribute("user", "jojo44");
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
    public String creerCompte() {
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

    // Ajoute les autres pages ici si besoin
}

