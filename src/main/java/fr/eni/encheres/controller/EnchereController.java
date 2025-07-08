package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dto.UtilisateurFormDto;
import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("utilisateur")
public class EnchereController {

	private final EnchereService enchereService;
	private final ArticleService articleService;
	private final CategorieService categorieService;
	private final UtilisateurDAO utilisateurDAO;

	

	public EnchereController(EnchereService enchereService, ArticleService articleService,
			CategorieService categorieService, UtilisateurDAO utilisateurDAO) {
		this.enchereService = enchereService;
		this.articleService = articleService;
		this.categorieService = categorieService;
		this.utilisateurDAO = utilisateurDAO;
	}
	
	@GetMapping("/")
	public String redirectionAccueil() {
	    return "redirect:/accueil";
	}

	@GetMapping("/accueil")
	public String accueil(Model model) {
		List<Categorie> categories = categorieService.selectAll();
		model.addAttribute("categories", categories);
		List<Article> articles = articleService.selectAll();
		model.addAttribute("articles", articles);
		return "accueil";
	}
	
	@GetMapping("/creer")
	public String getMethodName(@RequestParam String param) {
		return "view-creer-vente";
	}
	

	@ModelAttribute("utilisateur")
	public Utilisateur utilisateurActif(HttpSession session) {
		Object userInSession = session.getAttribute("utilisateurConnecte");
		if (userInSession instanceof Utilisateur) {
			return (Utilisateur) userInSession;
		}
		return null;
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

		utilisateurDAO.insert(utilisateur);
		return "redirect:/connexion";
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
			@RequestParam(name = "ventesTerminees") boolean ventesTerminees, Model model, HttpSession session) {
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
		if (utilisateur == null) {
			return "redirect:/connexion";
		}
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
				articles = articleService.selectMesEncheresRemportees(utilisateur.getIdUtilisateur(), categorie,
						nomArticle);
			}
		} else if (filtreType.equals("ventes")) {
			if (ventesEnCours) {
				articles = articleService.selectMesVentesEnCours(utilisateur.getIdUtilisateur(), categorie, nomArticle);
			} else if (ventesNonDebutees) {
				articles = articleService.selectMesVentesNonDebutees(utilisateur.getIdUtilisateur(), categorie,
						nomArticle);
			} else {
				articles = articleService.selectMesVentesTerminees(utilisateur.getIdUtilisateur(), categorie,
						nomArticle);
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
	@Autowired
	public void testEncoder(PasswordEncoder passwordEncoder) {
	    System.out.println(">>> PasswordEncoder utilisé : " + passwordEncoder.getClass().getSimpleName());
	    System.out.println(">>> Résultat du match : " + passwordEncoder.matches("pass123", "pass123"));
	}

}
