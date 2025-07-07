package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.CategorieServiceImpl;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategorieDAO;
import jakarta.servlet.http.HttpSession;

@Controller

public class PageTestController {
	
	
	private CategorieService categorieService;
	private ArticleService articleService;
	private UtilisateurService utilisateurService;
	
	
	public PageTestController ( CategorieService categorieService, ArticleService articleService, UtilisateurService utilisateurService) {
		
		this.categorieService=categorieService;
		this.articleService=articleService;
		this.utilisateurService=utilisateurService;
	}

//	@GetMapping("/accueil")
//	public String accueil(HttpSession session, Model model) {
//	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
//	 
//
//	    if (utilisateur == null) {
//	        return "redirect:/login";
//	    }
//
//	    model.addAttribute("user", utilisateur.getPseudo());
//
//	    List<Categorie> categories = categorieService.selectAll();
//	    model.addAttribute("categories", categories);
//
//	
//	    List<Article> articles = articleService.encheresEnCours();
//	    model.addAttribute("articles", articles);
//
//	    return "accueil"; 
//	}
	
	@GetMapping("/accueil-utilisateur")
	public String accueilUtilisateur(HttpSession session, Model model) {
	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");

	    if (utilisateur == null) {
	        return "redirect:/accueil";
	    }

	    model.addAttribute("user", utilisateur.getPseudo());

	    List<Categorie> categories = categorieService.selectAll();
	    model.addAttribute("categories", categories);

	
	    List<Article> articles = articleService.selectAll();
	    model.addAttribute("articles", articles);

	    return "accueil-utilisateur"; 
	}

	
	@GetMapping("/encheres/filtrer")
	public String filtrer(@RequestParam(name = "categorie", defaultValue = "0") long categorie,
		    @RequestParam(name = "nomArticle", defaultValue = "") String nomArticle,
		    @RequestParam(name = "filtreType", defaultValue = "achats") String filtreType,
		    @RequestParam(name = "encheresOuvertes") boolean encheresOuvertes,
		    @RequestParam(name = "mesEncheres") boolean mesEncheres,
		    @RequestParam(name = "mesEncheresRemportees") boolean mesEncheresRemportees,
		    @RequestParam(name = "ventesEnCours") boolean ventesEnCours,
		    @RequestParam(name = "ventesNonDebutees") boolean ventesNonDebutees,
		    @RequestParam(name = "ventesTerminees") boolean ventesTerminees,
		    Model model, HttpSession session
			) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
	    model.addAttribute("user", utilisateur.getPseudo());
		List<Categorie> categories = categorieService.selectAll();
	    model.addAttribute("categories", categories);
//	    List<Article> articles = articleService.selectEncheresOuvertes(categorie, nomArticle); 
//	    model.addAttribute("articles", articles);
	    model.addAttribute("nomArticle", nomArticle);
	    model.addAttribute("categorie", categorie);
	    
	    List<Article> articles = new ArrayList<>();

	   
	    if (filtreType.equals("achats")) {
	        if (encheresOuvertes) {
	            articles = articleService.selectEncheresOuvertes(categorie, nomArticle);
	        } else if (mesEncheres) {
	            articles = articleService.selectMesEncheres(utilisateur.getIdUtilisateur(), categorie, nomArticle);
	        } else {
	            articles = articleService.selectMesEncheresRemportees(utilisateur.getIdUtilisateur(), categorie, nomArticle);
	        }
	    } else if (filtreType.equals("ventes")) {
	        if (ventesEnCours) {
	            articles = articleService.selectMesVentesEnCours(utilisateur.getIdUtilisateur(), categorie, nomArticle);
	        } else if (ventesNonDebutees) {
	            articles = articleService.selectMesVentesNonDebutees(utilisateur.getIdUtilisateur(), categorie, nomArticle);
	        } else {
	            articles = articleService.selectMesVentesTerminees(utilisateur.getIdUtilisateur(), categorie, nomArticle);
	        } 
	    }
	    
	    model.addAttribute("encheresOuvertes", encheresOuvertes);
	    model.addAttribute("mesEncheres", mesEncheres);
	    model.addAttribute("mesEncheresRemportees", mesEncheresRemportees);
	    model.addAttribute("ventesEnCours", ventesEnCours);
	    model.addAttribute("ventesNonDebutees", ventesNonDebutees);
	    model.addAttribute("ventesTerminees", ventesTerminees);
	    return "accueil-utilisateur";
	}

    @GetMapping("/connexion")
    public String connexion() {
        return "view-connexion";
    }

    @GetMapping("/creer-compte")
    public String creerCompte(@ModelAttribute Utilisateur utilisateur) {
        return "view-creer-compte";
    }
    
    @PostMapping("/creer-compte")
    public String creerUtilisateur(@ModelAttribute Utilisateur utilisateur, HttpSession session) {
    	utilisateurService.insert(utilisateur);
    	session.setAttribute("utilisateurConnecte", utilisateur);
    	  return "redirect:/accueil-utilisateur";
    }
    

    @GetMapping("/profil")
    public String profil() {
        return "view-profil-enchere";
    }
    
    @GetMapping("/creer-vente")
    public String creerVente() {
        return "view-creer-vente";
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

