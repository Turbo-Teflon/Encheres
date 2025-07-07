package fr.eni.encheres.bll.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bll.ArticleService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

@Primary
@Service("articleServiceBouchon")
public class ArticleServiceBouchon implements ArticleService {

	private final List<Article> articles;

	public ArticleServiceBouchon() {
		articles = new ArrayList<>();

		// Utilisateur fictif
		Utilisateur u1 = new Utilisateur();
		u1.setIdUtilisateur(1);
		u1.setPseudo("jojo44");

		// Catégories fictives
		Categorie c1 = new Categorie(1, "Informatique");
		Categorie c2 = new Categorie(2, "Mobilier");

		// Retraits
				Retrait r1 = new Retrait("10 allée des Alouettes", "44800", "Saint Herblain");
				Retrait r2 = new Retrait("5 place du Marché", "75010", "Paris");

		// Article 1
		Article a1 = new Article(1, "PC Gamer", "Très puissant avec 32Go RAM", 100, 210,
				LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(5));
		a1.setEtatVente("EN_COURS");
		a1.setUtilisateur(u1);
		a1.setCategorie(c1);
		a1.setPrixVente(0);
		a1.setRetrait(r1);

		// Article 2
		Article a2 = new Article(2, "Chaise en bois", "Chaise ancienne style Louis XV", 20, 35,
				LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(2));
		a2.setEtatVente("EN_COURS");
		a2.setUtilisateur(u1);
		a2.setCategorie(c2);
		a2.setPrixVente(0);
		a2.setRetrait(r2);
		
		

		a1.setRetrait(r1);
		a2.setRetrait(r2);


		// Ajout des articles à la liste
		articles.add(a1);
		articles.add(a2);
	}

	@Override
	public List<Article> encheresEnCours() {
		LocalDateTime now = LocalDateTime.now();
		return articles.stream().filter(a ->
				a.getDateDebutEncheres().isBefore(now) &&
				a.getDateFinEncheres().isAfter(now) &&
				"EN_COURS".equals(a.getEtatVente()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Article> selectEncheresEnCoursFiltre(long categorieId, String nomArticle) {
		return articles.stream()
				.filter(a -> (categorieId == 0 || a.getCategorie().getIdCategorie() == categorieId) &&
						a.getNomArticle().toLowerCase().contains(nomArticle.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public void insert(Article article) {
		article.setIdArticle(articles.size() + 1); // auto-incrément
		articles.add(article);
	}

	@Override
	public Article selectById(long id) {
		return articles.stream().filter(a -> a.getIdArticle() == id).findFirst().orElse(null);
	}

	@Override
	public List<Article> selectAll() {
		return new ArrayList<>(articles);
	}

	@Override
	public List<Article> selectByCategorie(long idCategorie) {
		return articles.stream()
				.filter(a -> a.getCategorie().getIdCategorie() == idCategorie)
				.collect(Collectors.toList());
	}

	@Override
	public List<Article> selectByUtilisateur(long idUtilisateur) {
		return articles.stream()
				.filter(a -> a.getUtilisateur().getIdUtilisateur() == idUtilisateur)
				.collect(Collectors.toList());
	}

	@Override
	public void update(Article updatedArticle) {
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getIdArticle() == updatedArticle.getIdArticle()) {
				articles.set(i, updatedArticle);
				return;
			}
		}
	}

	@Override
	public void delete(long id) {
		articles.removeIf(a -> a.getIdArticle() == id);
	}

	@Override
	public List<Article> selectEnCours() {
		LocalDateTime now = LocalDateTime.now();
		return articles.stream()
				.filter(a -> a.getDateDebutEncheres().isBefore(now) && a.getDateFinEncheres().isAfter(now))
				.collect(Collectors.toList());
	}

	@Override
	public void readVendeurByArticle(Article article) {
		Utilisateur vendeur = new Utilisateur();
		vendeur.setIdUtilisateur(1);
		vendeur.setPseudo("vendeurTest");
		vendeur.setEmail("vendeur@test.fr");

		article.setUtilisateur(vendeur);
	}

	@Override
	public void readBestEnchereByArticle(Article article) {
		Utilisateur meilleurEncherisseur = new Utilisateur();
		meilleurEncherisseur.setIdUtilisateur(2);
		meilleurEncherisseur.setPseudo("enchereMaster");
		meilleurEncherisseur.setEmail("enchere@master.fr");

		Enchere enchere = new Enchere();
		enchere.setUtilisateur(meilleurEncherisseur);
		enchere.setArticle(article);
		enchere.setDateEnchere(LocalDateTime.now().minusHours(1));
		enchere.setMontantEnchere(article.getPrixActuel() + 10); // Surenchère fictive

		article.setPrixActuel(enchere.getMontantEnchere());
		article.setEncheres(List.of(enchere));
	}
}
