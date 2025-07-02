package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;


/**
 * Contrôleur REST pour exposer les utilisateurs au format JSON.
 * Toutes les routes de ce contrôleur commenceront par /api/utilisateurs.
 * 
 * Convention : le préfixe /api permet de distinguer les routes REST 
 * des routes MVC qui renvoient des pages HTML via Thymeleaf.
 */
@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurRestController {

    @Autowired
    @Qualifier("utilisateurDAOBouchon")
    private UtilisateurDAO utilisateurDAO;

    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurDAO.selectAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable("id") int id) {
        return utilisateurDAO.selectById(id);
    }

    @GetMapping("/pseudo/{pseudo}")
    public Utilisateur getByPseudo(@PathVariable("pseudo") String pseudo) {
        return utilisateurDAO.selectByPseudo(pseudo);
    }

    @GetMapping("/email/{email}")
    public Utilisateur getByEmail(@PathVariable("email") String email) {
        return utilisateurDAO.selectByEmail(email);
    }
    @PostMapping
    public void ajouterUtilisateur(@RequestBody Utilisateur nouvelUtilisateur) {
        utilisateurDAO.insert(nouvelUtilisateur);
    }
}
