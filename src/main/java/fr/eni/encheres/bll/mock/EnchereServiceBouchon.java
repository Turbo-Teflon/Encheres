package fr.eni.encheres.bll.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

@Primary
@Service
public class EnchereServiceBouchon implements EnchereService {

    private final List<Enchere> encheres = new ArrayList<>();

    public EnchereServiceBouchon() {
        // Création d’utilisateurs fictifs
        Utilisateur u1 = new Utilisateur();
        u1.setIdUtilisateur(1);
        u1.setPseudo("jojo44");

        Utilisateur u2 = new Utilisateur();
        u2.setIdUtilisateur(2);
        u2.setPseudo("enchereMaster");

        // Création de la catégorie
        Categorie c1 = new Categorie(1, "Informatique");

        // Création de l'article
        Article a1 = new Article(
                1,
                "PC Gamer",
                "Très puissant",
                100,
                210,
                LocalDateTime.now().minusDays(2),
                LocalDateTime.now().plusDays(3)
        );
        a1.setCategorie(c1);
        a1.setUtilisateur(u2); // le vendeur

        // Ajout d'un lieu de retrait
        Retrait r1 = new Retrait("10 allée des Alouettes", "44800", "Saint Herblain");
        a1.setRetrait(r1);

        // Enchère gagnante par u1
        Enchere e1 = new Enchere();
        e1.setUtilisateur(u1); // acheteur
        e1.setArticle(a1);
        e1.setDateEnchere(LocalDateTime.now().minusDays(1));
        e1.setMontantEnchere(210);

        // Ajout à la liste
        encheres.add(e1);
    }

    @Override
    public List<Enchere> selectAll() {
        return new ArrayList<>(encheres);
    }

    @Override
    public List<Enchere> selectByArticle(long idArticle) {
        return encheres.stream()
            .filter(e -> e.getArticle().getIdArticle() == idArticle)
            .collect(Collectors.toList());
    }

    @Override
    public List<Enchere> selectByUtilisateur(long idUtilisateur) {
        return encheres.stream()
            .filter(e -> e.getUtilisateur().getIdUtilisateur() == idUtilisateur)
            .collect(Collectors.toList());
    }

    @Override
    public void insert(Enchere enchere) {
        encheres.add(enchere);
    }
}
