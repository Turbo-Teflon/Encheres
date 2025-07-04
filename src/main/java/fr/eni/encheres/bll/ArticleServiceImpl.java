package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;



@Service
public class ArticleServiceImpl implements ArticleService {

	private ArticleDAO articleDAO;
	private UtilisateurDAO utilisateurDAO;
	private EnchereDAO enchereDAO;
	
	public ArticleServiceImpl(ArticleDAO articleDAO, UtilisateurDAO utilisateurDAO, EnchereDAO enchereDAO) {
		this.articleDAO=articleDAO;
		this.utilisateurDAO=utilisateurDAO;
		this.enchereDAO=enchereDAO;
	}
	@Override
	public void insert(Article article) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public Article selectById(long id) {
		return articleDAO.selectById(id);
	}

	@Override
	public List<Article> selectAll() {
	
		List<Article> articles = this.articleDAO.selectAll();
		

		if (articles != null) {
			articles.forEach(a -> {
				readVendeurByArticle(a);
				readBestEnchereByArticle(a);
			});
		}
		
		return articles;
	
	}

	@Override
	public List<Article> selectByCategorie(long idCategorie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> selectByUtilisateur(long idUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Article article) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Article> selectEnCours() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void readVendeurByArticle(Article article) {
		Utilisateur utilisateur = utilisateurDAO.selectById(article.getUtilisateur().getIdUtilisateur());
		article.setUtilisateur(utilisateur);
		
	}
	@Override
	public void readBestEnchereByArticle(Article article) {
		Enchere bestEnchere = enchereDAO.selectBestByArticle(article.getIdArticle());
		article.setPrixActuel(bestEnchere.getMontantEnchere());
		
	}
	@Override
	public List<Article> encheresEnCours() {
		List<Article> articles = this.articleDAO.selectEncheresEnCours();
		

		if (articles != null) {
			articles.forEach(a -> {
				readVendeurByArticle(a);
				readBestEnchereByArticle(a);
			});
		}
		
		return articles;
	}
	@Override
	public List<Article> selectEncheresEnCoursFiltre(long idCategorie, String nomArticle) {
List<Article> articles = this.articleDAO.selectEncheresEnCoursFiltre(idCategorie, nomArticle);
		

		if (articles != null) {
			articles.forEach(a -> {
				readVendeurByArticle(a);
				readBestEnchereByArticle(a);
			});
		}
		
		return articles;
	}
	

}
