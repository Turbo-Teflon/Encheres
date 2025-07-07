package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;

@Controller
public class EnchereController {

    private final EnchereService enchereService;
    private final ArticleService articleService;

    public EnchereController(EnchereService enchereService,@Qualifier("articleServiceBouchon") ArticleService articleService) {
        this.enchereService = enchereService;
        this.articleService = articleService;
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
}
