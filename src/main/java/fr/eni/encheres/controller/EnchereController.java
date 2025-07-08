package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnchereController {

    private final EnchereService enchereService;
    private final ArticleService articleService;
    private final CategorieService categorieService;

    public EnchereController(EnchereService enchereService, ArticleService articleService, CategorieService categorieService) {
        this.enchereService = enchereService;
        this.articleService = articleService;
        this.categorieService=categorieService;
    }
    
    
    @GetMapping("/accueil")
    public String accueil(@RequestParam(name = "categorie", defaultValue = "0") long idCategorie,
		    @RequestParam(name = "nomArticle", defaultValue = "") String nomArticle, HttpSession session, Model model) {	
    	
    	
        List<Categorie> categories = categorieService.selectAll();
        List<Article> articles = articleService.selectEncheresOuvertes(idCategorie,nomArticle); 
        
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        
	Object utilisateur = session.getAttribute("utilisateur");
	System.out.println(utilisateur);
    	
    	if (utilisateur == null) {
         
            return "view-accueil-public-enchere";
        }

      
        model.addAttribute("categorie", idCategorie);
        model.addAttribute("nomArticle", nomArticle);
        model.addAttribute("filtreType", "achats"); 

        
        model.addAttribute("encheresOuvertes", false);
        model.addAttribute("mesEncheres", false);
        model.addAttribute("mesEncheresRemportees", false);

        model.addAttribute("ventesEnCours", false);
        model.addAttribute("ventesNonDebutees", false);
        model.addAttribute("ventesTerminees", false);

        return "view-accueil-utilisateur-enchere";
    }

	
    // Détail des enchères d’un article
    @GetMapping("/encheres/detail")
    public String voirDetailEnchere(@RequestParam("id") long idArticle, Model model) {
        Article article = articleService.selectById(idArticle);
        List<Enchere> encheres = enchereService.selectByArticle(idArticle);

        model.addAttribute("article", article);
        model.addAttribute("encheres", encheres);
        return "view-achat-detail-enchere";
    }

    // Enchères gagnées par un utilisateur fictif (exemple user id = 1)
    @GetMapping("/encheres/gagnees")
    public String voirEncheresGagnees(Model model) {
        long idUtilisateur = 1; // à remplacer par l’utilisateur connecté
        List<Enchere> encheres = enchereService.selectByUtilisateur(idUtilisateur);

        model.addAttribute("encheres", encheres);
        return "view-gain-achat-enchere";
    }
    
    @GetMapping("/encheres/filtrerPublic")
   	public String filtrerAccueilDefault(  
   			@RequestParam(name = "nomArticle", required = false, defaultValue = "") String nomArticle,
   		    @RequestParam(name = "categorie", required = false, defaultValue = "0") long idCategorie,
   		    Model model
   			) {
   		
    	List<Article> articles = articleService.selectEncheresOuvertes(idCategorie, nomArticle);
   		List<Categorie> categories = categorieService.selectAll();
   	    model.addAttribute("categories", categories);
   	    model.addAttribute("articles", articles);
   	    model.addAttribute("nomArticle", nomArticle);
   	    model.addAttribute("categorie", idCategorie);
   	    
   	    return "view-accueil-public-enchere";
   	}
    
    @GetMapping("/encheres/filtrer")
	public String filtrerAccueilUser(  
			@RequestParam(name = "nomArticle", required = false, defaultValue = "") String nomArticle,
		    @RequestParam(name = "categorie", required = false, defaultValue = "0") long idCategorie,
		    @RequestParam(name = "filtreType", required = false, defaultValue = "achats") String filtreType,

		    @RequestParam(name = "encheresOuvertes", required = false, defaultValue = "false") boolean encheresOuvertes,
		    @RequestParam(name = "mesEncheres", required = false, defaultValue = "false") boolean mesEncheres,
		    @RequestParam(name = "mesEncheresRemportees", required = false, defaultValue = "false") boolean mesEncheresRemportees,

		    @RequestParam(name = "ventesEnCours", required = false, defaultValue = "false") boolean ventesEnCours,
		    @RequestParam(name = "ventesNonDebutees", required = false, defaultValue = "false") boolean ventesNonDebutees,
		    @RequestParam(name = "ventesTerminees", required = false, defaultValue = "false") boolean ventesTerminees,
		    Model model, HttpSession session
			) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
	    model.addAttribute("user", utilisateur.getPseudo());
		List<Categorie> categories = categorieService.selectAll();
	    model.addAttribute("categories", categories);
	    model.addAttribute("nomArticle", nomArticle);
	    model.addAttribute("categorie", idCategorie);
	    
	    List<Article> articles = new ArrayList<>();

	   
	    if (filtreType.equals("achats")) {
	        if (encheresOuvertes) {
	            articles = articleService.selectEncheresOuvertes(idCategorie, nomArticle);
	        } else if (mesEncheres) {
	            articles = articleService.selectMesEncheres(utilisateur.getIdUtilisateur(), idCategorie, nomArticle);
	        } else {
	            articles = articleService.selectMesEncheresRemportees(utilisateur.getIdUtilisateur(), idCategorie, nomArticle);
	        }
	    } else if (filtreType.equals("ventes")) {
	        if (ventesEnCours) {
	            articles = articleService.selectMesVentesEnCours(utilisateur.getIdUtilisateur(), idCategorie, nomArticle);
	        } else if (ventesNonDebutees) {
	            articles = articleService.selectMesVentesNonDebutees(utilisateur.getIdUtilisateur(), idCategorie, nomArticle);
	        } else {
	            articles = articleService.selectMesVentesTerminees(utilisateur.getIdUtilisateur(), idCategorie, nomArticle);
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

}
