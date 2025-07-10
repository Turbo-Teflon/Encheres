package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dto.UtilisateurFormDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@SessionAttributes("categories")
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
	
	@ModelAttribute("utilisateur")
	public Utilisateur utilisateurActif(HttpSession session) {
		Object userInSession = session.getAttribute("utilisateur");
		if (userInSession instanceof Utilisateur) {
			return (Utilisateur) userInSession;
		}
		return null;
	}
	
	@ModelAttribute("categories")
	public List<Categorie> categories(){
		System.out.println("Mise en session des catégories");
		return this.categorieService.selectAll();
	}

	@GetMapping("/")
	public String redirectionAccueil() {
		return "redirect:/accueil";
	}

	@GetMapping("/accueil")
	public String accueil(HttpSession session, Model model) {
		System.out.println("=====> TU ES DANS LA METHODE ACCUEIL");
		System.out.println("==== PAGE ACCUEIL ====");

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

		if (utilisateur != null) {

		    model.addAttribute("utilisateur", utilisateur);
		} else {
		    session.removeAttribute("utilisateur"); // pour être sûr de vider un inactif
		    model.addAttribute("utilisateur", null);
		}


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

		return "accueil";
	}

	@GetMapping("/creer")
	public String creerAnnonce(HttpSession session, Model model) {
		Article a = new Article();
		model.addAttribute("article", a);
		return "view-creer-vente";
	}	

	@GetMapping("/encheres/detail")
	public String voirDetailEnchere(@RequestParam("id") long idArticle, Model model) {
		Article article = articleService.selectById(idArticle);
		if (article == null) {
			return "redirect:/accueil";
		}
		List<Enchere> encheres = enchereService.selectByArticle(idArticle);
		model.addAttribute("article", article);
		model.addAttribute("encheres", encheres);
		return "view-achat-detail-enchere";
	}

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
		model.addAttribute("utilisateurForm", new UtilisateurFormDto());
		return "view-creer-compte-enchere";
	}

	@GetMapping("/profil")
	public String profil(HttpSession session, Model model) {
	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
	    System.out.println("Utilisateur en session : " + utilisateur);
	    if (utilisateur == null) {

	        session.invalidate(); // Déconnecte si l'utilisateur est inactif
	        model.addAttribute("erreur", "Votre compte est désactivé.");
	        return "redirect:/connexion";
	    }

	    model.addAttribute("utilisateur", utilisateur);
	    return "view-profil-enchere";
	}

	@GetMapping("/modifier-profil")
	public String modifierProfilForm(Model model, HttpSession session) {
	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
	    if (utilisateur == null) {
	        return "redirect:/profil";
	    }
	    
	    UtilisateurFormDto dto = new UtilisateurFormDto();
	    dto.setPseudo(utilisateur.getPseudo());
	    dto.setNom(utilisateur.getNom());
	    dto.setPrenom(utilisateur.getPrenom());
	    dto.setEmail(utilisateur.getEmail());
	    dto.setTelephone(utilisateur.getTelephone());
	    dto.setRue(utilisateur.getRue());
	    dto.setCodePostal(utilisateur.getCodePostal());
	    dto.setVille(utilisateur.getVille());
	    dto.setMain(utilisateur.isMain()); 

	    model.addAttribute("utilisateurForm", dto);
	    return "view-modifier-profil-enchere";
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
		session.invalidate();
		return "redirect:/accueil";
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
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		if (utilisateur == null) {
			return "redirect:/connexion";
		}
		model.addAttribute("user", utilisateur.getPseudo());
		List<Categorie> categories = categorieService.selectAll();
		model.addAttribute("categories", categories);
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
		model.addAttribute("articles", articles);
		model.addAttribute("encheresOuvertes", encheresOuvertes);
		model.addAttribute("mesEncheres", mesEncheres);
		model.addAttribute("mesEncheresRemportees", mesEncheresRemportees);
		model.addAttribute("ventesEnCours", ventesEnCours);
		model.addAttribute("ventesNonDebutees", ventesNonDebutees);
		model.addAttribute("ventesTerminees", ventesTerminees);
		return "accueil-utilisateur";
	}

	@PostMapping("/creer-compte")
	public String creerComptePost(@Valid @ModelAttribute("utilisateurForm") UtilisateurFormDto formDto, BindingResult result, Model model, HttpSession session)
 {
		if (result.hasErrors()) {
		    return "view-creer-compte-enchere";
		}

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
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String password = formDto.getMotDePasse();
		String hash = encoder.encode(password);
		utilisateur.setMotDePasse(hash);
		
		try {
			utilisateurService.insert(utilisateur);
			session.setAttribute("utilisateur", utilisateur);
			return "redirect:/accueil";
		} catch (DuplicateKeyException e) {
			String message = e.getMessage();

			if (message.contains("UQ__Utilisat__EA0EEA22")) {
				model.addAttribute("erreur", "Ce pseudo est déjà utilisé.");
			} else if (message.toLowerCase().contains("email")) {
				model.addAttribute("erreur", "Cet email est déjà utilisé.");
			} else {
				model.addAttribute("erreur", "Une erreur est survenue.");
			}

			return "view-creer-compte-enchere";
		}
	}
	@PostMapping("/vendre")
	public String postCreerAnnonce(HttpSession session, @ModelAttribute Article article) {
		
		article.setUtilisateur((Utilisateur) session.getAttribute("utilisateur"));
		this.articleService.insert(article);
		return "redirect:/accueil";
	}
	@PostMapping("/connexion")
	public String connexion(@RequestParam String pseudo,
	                        @RequestParam String motDePasse,
	                        HttpSession session,
	                        Model model) {
	    try {
	        Utilisateur utilisateur = utilisateurService.login(pseudo, motDePasse);

	        session.setAttribute("utilisateur", utilisateur);
	        System.out.println("Connexion réussie pour : " + utilisateur.getPseudo());

	        return "redirect:/accueil";

	    } catch (RuntimeException e) {
	        System.out.println("Erreur dans /connexion : " + e.getMessage());
	        model.addAttribute("erreur", e.getMessage());
	        return "view-connexion-enchere";
	    }
	}


	@PostMapping("/supprimer-compte")
	public String supprimerCompte(
	    @RequestParam("pseudo") String pseudo,
	    @RequestParam("motDePasse") String motDePasse,
	    HttpSession session,
	    Model model) {

	    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

	    if (utilisateur == null || !utilisateur.getPseudo().equals(pseudo)) {
	        model.addAttribute("erreur", "Identifiants invalides.");
	        return "view-modifier-profil-enchere";
	    }

	    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    if (!encoder.matches(motDePasse, utilisateur.getMotDePasse())) {
	        model.addAttribute("erreur", "Mot de passe incorrect.");
	        return "view-modifier-profil-enchere";
	    }

	    utilisateurService.delete(utilisateur.getIdUtilisateur());
	    session.invalidate();

	    return "redirect:/accueil";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
	    model.addAttribute("erreur", e.getMessage());
	    return "view-connexion-enchere"; // page de repli
	}


}
