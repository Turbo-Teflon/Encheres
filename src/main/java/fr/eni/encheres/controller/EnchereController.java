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

    public EnchereController(EnchereService enchereService,@Qualifier("articleServiceBouchon") ArticleService articleService, CategorieService categorieService) {
        this.enchereService = enchereService;
        this.articleService = articleService;
        this.categorieService=categorieService;
    }
    
    
	@GetMapping("/accueil")
	public String accueil(Model model) {	    
	    List<Categorie> categories = categorieService.selectAll();
	    model.addAttribute("categories", categories);
	    List<Article> articles = articleService.selectAll(); 
	    model.addAttribute("articles", articles);   
	    return "accueil";
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

}
