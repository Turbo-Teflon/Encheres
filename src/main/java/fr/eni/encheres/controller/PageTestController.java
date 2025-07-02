package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class PageTestController {

	@GetMapping("/accueil")
	public String accueil(Model model) {
	    model.addAttribute("user", "jojo44");
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

