package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.UtilisateurDAO;



@Service
public class ArticleServiceImpl implements ArticleService {

	private ArticleDAO articleDAO;
	private UtilisateurDAO utilisateurDAO;
	
	public ArticleServiceImpl(ArticleDAO articleDAO, UtilisateurDAO utilisateurDAO) {
		this.articleDAO=articleDAO;
		this.utilisateurDAO=utilisateurDAO;
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
			articles.forEach(a -> selectVendeur(a));
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
	public void selectVendeur(Article article) {
		Utilisateur utilisateur = utilisateurDAO.selectById(article.getUtilisateur().getIdUtilisateur());
		article.setUtilisateur(utilisateur);
		
	}
	

}
