package fr.eni.encheres.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import fr.eni.encheres.dto.UtilisateurFormDto;
import jakarta.servlet.http.HttpSession;

import jakarta.validation.Valid;


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

	@GetMapping("/")
	public String redirectionAccueil() {
		return "redirect:/accueil";
	}

	@GetMapping("/accueil")
	public String accueil(HttpSession session, Model model) {
		System.out.println("=====> TU ES DANS LA METHODE ACCUEIL");
		System.out.println("==== PAGE ACCUEIL ====");

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		if (utilisateur == null) {
			utilisateur = new Utilisateur();
		}
		model.addAttribute("utilisateur", utilisateur);

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

	@GetMapping("/encheres/filtrer")
	public String filtrerAccueilDefault(
			@RequestParam(name = "nomArticle", required = false, defaultValue = "") String nomArticle,
			@RequestParam(name = "categorie", required = false, defaultValue = "0") long idCategorie,
			@RequestParam(name = "etatEnchere", required = false, defaultValue = "ouvertes") String etatEnchere,
			Model model, HttpSession session) {

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		if (utilisateur == null) {
			utilisateur = new Utilisateur(); 
		}
		model.addAttribute("utilisateur", utilisateur);

		List<Article> articles;

		// Appel de méthode selon filtre choisi
		if ("terminees".equals(etatEnchere)) {
		    articles = articleService.selectEncheresTerminees(idCategorie, nomArticle);
		} else if ("remportees".equals(etatEnchere)) {
		    articles = articleService.selectMesEncheresRemportees(utilisateur.getIdUtilisateur(), idCategorie, nomArticle);
		} else {
		    articles = articleService.selectEncheresOuvertes(idCategorie, nomArticle);
		}


		List<Categorie> categories = categorieService.selectAll();
		model.addAttribute("categories", categories);
		model.addAttribute("articles", articles);
		model.addAttribute("nomArticle", nomArticle);
		model.addAttribute("categorie", idCategorie);
		model.addAttribute("etatEnchere", etatEnchere); // pour gérer le bouton radio sélectionné

		return "accueil";
	}

	@GetMapping("/creer")
	public String creerAnnonce(HttpSession session, Model model) {
		Article a = new Article();
		model.addAttribute("article", a);
		return "view-creer-vente";
	}
	
	@PostMapping("/vendre")
	public String postCreerAnnonce(HttpSession session, @ModelAttribute Article article) {
		
		article.setUtilisateur((Utilisateur) session.getAttribute("utilisateur"));
		this.articleService.insert(article);
		return "redirect:/accueil";
	}


	@ModelAttribute("utilisateur")
	public Utilisateur utilisateurActif(HttpSession session) {
		Object userInSession = session.getAttribute("utilisateurConnecte");
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

	

	@GetMapping("/encheres/detail")

	public String voirDetailEnchere(@RequestParam("idArticle") long idArticle,
			@RequestParam(name = "etatEnchere", required = false, defaultValue = "ouvertes") String etatEnchere,
			Model model) {
		Article article = articleService.selectById(idArticle);
		model.addAttribute("article", article);

		if ("terminees".equals(etatEnchere)) {
		    return "view-vente-detail-enchereTerminees";
		} else if ("remportees".equals(etatEnchere)) {
		    return "view-vente-detail-enchereRemportees";
		} else {
		    return "view-vente-detail-enchereOuvertes";
		}

	}

	@PostMapping("/encheres/detail/proposerEnchere")
	public String proposerEnchere(@RequestParam("idArticle") long idArticle,
			@RequestParam("montantEnchere") int montantEnchere, Model model, HttpSession session) {
		Article article = articleService.selectById(idArticle);
		model.addAttribute("article", article);

		Enchere enchere = new Enchere();
		enchere.setArticle(article);

		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		if (utilisateur == null) {
			utilisateur = new Utilisateur(); // objet vide pour éviter le null
		}
		model.addAttribute("utilisateur", utilisateur);

		enchere.setUtilisateur(utilisateur);
		enchere.setMontantEnchere(montantEnchere);
		enchere.setDateEnchere(LocalDateTime.now());

		enchereService.insert(enchere);

		return "redirect:/accueil";
	}
	
	
	@PostMapping("/encheres/retrait")
	public String effectuerRetrait(
	        @RequestParam("idArticle") long idArticle, HttpSession session) {

	    Article article = articleService.selectById(idArticle);
	    Utilisateur acheteur = (Utilisateur) session.getAttribute("utilisateur");

	    Utilisateur vendeur = article.getUtilisateur();

	    int prixFinal = article.getPrixActuel();

	    // Mise à jour des crédits
	    acheteur.setCredit(acheteur.getCredit() - prixFinal);
	    vendeur.setCredit(vendeur.getCredit() + prixFinal);

	    utilisateurService.update(acheteur);
	    utilisateurService.update(vendeur);

	   
	    return "redirect:/accueil";
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
		session.invalidate();
		return "redirect:/accueil";
	}

	@PostMapping("/creer-compte")

	public String creerComptePost(@Valid @ModelAttribute("utilisateurForm") UtilisateurFormDto formDto, BindingResult result, Model model, HttpSession session){
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
}}
