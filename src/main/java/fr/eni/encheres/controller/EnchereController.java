package fr.eni.encheres.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dto.UtilisateurFormDto;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnchereController {

	private final EnchereService enchereService;
	private final ArticleService articleService;
	private final CategorieService categorieService;
	private final UtilisateurService utilisateurService;

    



	

	public EnchereController(EnchereService enchereService, ArticleService articleService,
			CategorieService categorieService, UtilisateurService utilisateurService) {
		this.enchereService = enchereService;
		this.articleService = articleService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
	}
	
	
	@GetMapping("/accueil")
	public String accueil(HttpSession session, Model model) {
	    System.out.println("=====> TU ES DANS LA METHODE ACCUEIL");
	    System.out.println("==== PAGE ACCUEIL ====");

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
	    if (utilisateur == null) {
	        utilisateur = new Utilisateur(); // objet vide pour éviter le null
	    }
	    model.addAttribute("utilisateur", session.getAttribute("utilisateur")); // même si c’est null

	    System.out.println("Session ID dans accueil : " + session.getId());
	    System.out.println("Utilisateur dans session : " + utilisateur);

	    try {
	        List<Categorie> categories = categorieService.selectAll();
	        model.addAttribute("categories", categories);
	        System.out.println("Catégories récupérées : " + categories.size());
	    } catch (Exception e) {
	        System.out.println("Erreur récupération catégories : " + e.getMessage());
	    }

	    try {
	        List<Article> articles = articleService.selectEncheresOuvertes(0, "");
	        model.addAttribute("articles", articles);
	        System.out.println("Articles récupérés : " + articles.size());
	    } catch (Exception e) {
	        System.out.println("Erreur récupération articles : " + e.getMessage());
	    }

	    model.addAttribute("utilisateur", utilisateur); // pour afficher son pseudo ou autre dans la page

	    return "accueil";
	}

	
	 

	
	@GetMapping("/")
	public String redirectionAccueil() {
	    return "redirect:/accueil";
	}


 
    
    @GetMapping("/encheres/filtrer")
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
   	    
   	    return "accueil";
   	}
    
//    @GetMapping("/encheres/filtrer")
//	public String filtrer(  
//			@RequestParam(name = "nomArticle", required = false, defaultValue = "") String nomArticle,
//		    @RequestParam(name = "categorie", required = false, defaultValue = "0") long idCategorie,
//		    @RequestParam(name = "filtreType", required = false, defaultValue = "achats") String filtreType,
//
//		    @RequestParam(name = "encheresOuvertes", required = false, defaultValue = "false") boolean encheresOuvertes,
//		    @RequestParam(name = "mesEncheres", required = false, defaultValue = "false") boolean mesEncheres,
//		    @RequestParam(name = "mesEncheresRemportees", required = false, defaultValue = "false") boolean mesEncheresRemportees,
//
//		    @RequestParam(name = "ventesEnCours", required = false, defaultValue = "false") boolean ventesEnCours,
//		    @RequestParam(name = "ventesNonDebutees", required = false, defaultValue = "false") boolean ventesNonDebutees,
//		    @RequestParam(name = "ventesTerminees", required = false, defaultValue = "false") boolean ventesTerminees,
//		    Model model, HttpSession session
//			) {
//		Utilisateur utilisateurTest = new Utilisateur();
//		   utilisateurTest.setIdUtilisateur(1);
//		List<Categorie> categories = categorieService.selectAll();
//	    model.addAttribute("categories", categories);
//	    model.addAttribute("nomArticle", nomArticle);
//	    model.addAttribute("categorie", idCategorie);
//	    
//	    
//	    List<Article> articles = new ArrayList<>();
//
//	    boolean noFiltreEnchereVente =
//	            !encheresOuvertes &&
//	            !mesEncheres &&
//	            !mesEncheresRemportees &&
//	            !ventesEnCours &&
//	            !ventesNonDebutees &&
//	            !ventesTerminees;
//
//	    if (noFiltreEnchereVente) {
//	       
//	        articles = articleService.selectEncheresOuvertes(idCategorie, nomArticle);
//	    } else if ("achats".equals(filtreType)) {
//	        if (encheresOuvertes) {
//	            articles = articleService.selectEncheresOuvertes(idCategorie, nomArticle);
//	        } else if (mesEncheres) {
//	            articles = articleService.selectMesEncheres(utilisateurTest.getIdUtilisateur(), idCategorie, nomArticle);
//	        } else if (mesEncheresRemportees) {
//	            articles = articleService.selectMesEncheresRemportees(utilisateurTest.getIdUtilisateur(), idCategorie, nomArticle);
//	        }
//	    } else if ("ventes".equals(filtreType)) {
//	        if (ventesEnCours) {
//	            articles = articleService.selectMesVentesEnCours(utilisateurTest.getIdUtilisateur(), idCategorie, nomArticle);
//	        } else if (ventesNonDebutees) {
//	            articles = articleService.selectMesVentesNonDebutees(utilisateurTest.getIdUtilisateur(), idCategorie, nomArticle);
//	        } else if (ventesTerminees) {
//	            articles = articleService.selectMesVentesTerminees(utilisateurTest.getIdUtilisateur(), idCategorie, nomArticle);
//	        }
//	    }
//
//	    model.addAttribute("articles", articles);
//
//	    
//	    model.addAttribute("encheresOuvertes", encheresOuvertes);
//	    model.addAttribute("mesEncheres", mesEncheres);
//	    model.addAttribute("mesEncheresRemportees", mesEncheresRemportees);
//	    model.addAttribute("ventesEnCours", ventesEnCours);
//	    model.addAttribute("ventesNonDebutees", ventesNonDebutees);
//	    model.addAttribute("ventesTerminees", ventesTerminees);
//	    return "view-accueil-enchere";
//    }



	@ModelAttribute("utilisateur")
	public Utilisateur utilisateurActif(HttpSession session) {
		Object userInSession = session.getAttribute("utilisateurConnecte");
		if (userInSession instanceof Utilisateur) {
			return (Utilisateur) userInSession;
		}
		return null;
	}

	
	@GetMapping("/encheres/detail")
	public String voirDetailEnchere(@RequestParam("idArticle") long idArticle, Model model) {
		Article article = articleService.selectById(idArticle);
		model.addAttribute("article", article);
		
	

		return "view-vente-detail-enchere";
	}
	
	
	@PostMapping("/encheres/detail/proposerEnchere")
	public String proposerEnchere(@RequestParam("idArticle") long idArticle, @RequestParam("montantEnchere") int montantEnchere, Model model) {
		Article article = articleService.selectById(idArticle);
		model.addAttribute("article", article);
	
		Enchere enchere = new Enchere();
	    enchere.setArticle(article);
	    Utilisateur utilisateurTest = new Utilisateur();
	    utilisateurTest.setIdUtilisateur(1);
	    enchere.setUtilisateur(utilisateurTest); 
	    enchere.setMontantEnchere(montantEnchere);
	    enchere.setDateEnchere(LocalDateTime.now());


	    enchereService.insert(enchere);
	    
		return "redirect:/accueil";
	}

	// Enchères gagnées par un utilisateur fictif (exemple user id = 1)
	@GetMapping("/encheres/gagnees")
	public String voirEncheresGagnees(Model model) {
		long idUtilisateur = 1; // à remplacer par l’utilisateur connecté
		List<Enchere> encheres = enchereService.selectByUtilisateur(idUtilisateur);

		model.addAttribute("encheres", encheres);
		return "view-gain-achat-enchere";
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
	public String profil(HttpSession session, Model model) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
		if (utilisateur == null) {
			return "redirect:/connexion";
		}
		model.addAttribute("utilisateur", utilisateur);
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
	public String creerComptePost(@ModelAttribute UtilisateurFormDto formDto, Model model) {
		if (!formDto.getMotDePasse().equals(formDto.getConfirmation())) {
			model.addAttribute("erreur", "Les mots de passe ne correspondent pas.");
			return "view-creer-compte-enchere";
		}

		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setPseudo(formDto.getPseudo());
		utilisateur.setNom(formDto.getNom());
		utilisateur.setPrenom(formDto.getPrenom());
		utilisateur.setEmail(formDto.getEmail());
		utilisateur.setTelephone(formDto.getTelephone());
		utilisateur.setRue(formDto.getRue());
		utilisateur.setCodePostal(formDto.getCodePostal());
		utilisateur.setVille(formDto.getVille());
		utilisateur.setCredit(100);
		utilisateur.setAdministrateur(false);

		utilisateurService.insert(utilisateur);
		return "redirect:/connexion";
	}

	
	@Autowired
	public void testEncoder(PasswordEncoder passwordEncoder) {
	    System.out.println(">>> PasswordEncoder utilisé : " + passwordEncoder.getClass().getSimpleName());
	    System.out.println(">>> Résultat du match : " + passwordEncoder.matches("pass123", "pass123"));

	}

}
