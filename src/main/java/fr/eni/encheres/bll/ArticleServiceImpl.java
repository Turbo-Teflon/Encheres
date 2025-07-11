package fr.eni.encheres.bll;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.encheres.exception.BuisnessException;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategorieDAO;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.UtilisateurDAO;



@Service
@Profile("prod")
public class ArticleServiceImpl implements ArticleService {

	private ArticleDAO articleDAO;
	private UtilisateurDAO utilisateurDAO;
	private EnchereDAO enchereDAO;
	private CategorieDAO categorieDAO;
	private final Log logger = LogFactory.getLog(getClass());
	

	
	/**
	 * @param articleDAO
	 * @param utilisateurDAO
	 * @param enchereDAO
	 * @param categorieDAO
	 */
	public ArticleServiceImpl(ArticleDAO articleDAO, UtilisateurDAO utilisateurDAO, EnchereDAO enchereDAO,
			CategorieDAO categorieDAO) {
		super();
		this.articleDAO = articleDAO;
		this.utilisateurDAO = utilisateurDAO;
		this.enchereDAO = enchereDAO;
		this.categorieDAO = categorieDAO;
	}

	@Override
	@Transactional(rollbackFor = BuisnessException.class)
	public void insert(Article article) throws BuisnessException{
		
		article.setDateDebutEncheres(LocalDateTime.now());
		article.setEtatVente("O");
		BuisnessException be = new BuisnessException();
		boolean isValid = isCategorieValid(article.getCategorie(), be);
		isValid &= isDateValid(article, be);
		if (isValid) {
			try {
				articleDAO.insert(article);
				
			} catch (DataAccessException e) {
				logger.error(e);
				be.add("Erreur d'acces à la base");
				throw be;
			}
		}else {
			throw be;
		}
	
	}
	
	private boolean isCategorieValid(Categorie cat, BuisnessException be) {
		if(this.categorieDAO.hasCategorie(cat.getIdCategorie())) return true;
		be.add("La Catégorie n'éxiste pas");
		return false;
	}
	
	private boolean isDateValid(Article a, BuisnessException be) {
		if(a.getDateFinEncheres().isAfter(a.getDateDebutEncheres())) return true;
		be.add("Durée d'Enchère trop courte !");
		return false;
	}

	@Override
	public Article selectById(long id) {
		return articleDAO.selectById(id);
	}

//	@Override
//	public List<Article> selectAll() {
//	
//		List<Article> articles = this.articleDAO.selectAll();
//		
//
//		if (articles != null) {
//			articles.forEach(a -> {
//				setVendeurByArticle(a);
//				setBestEnchereByArticle(a);
//			});
//		}
//		
//		return articles;
//	
//	}

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
	public void setVendeurByArticle(Article article) {
		Utilisateur utilisateur = utilisateurDAO.selectById(article.getUtilisateur().getIdUtilisateur());
		article.setUtilisateur(utilisateur);
		
	}
	@Override
	public void setBestEnchereByArticle(Article article) {
	    Enchere bestEnchere = enchereDAO.selectBestByArticle(article.getIdArticle());
	    if (bestEnchere != null) {
	        article.setPrixActuel(bestEnchere.getMontantEnchere());
	    } else {
	        article.setPrixActuel(article.getMiseAPrix());
	    }
	}

	@Override
	public List<Article> selectAll() {
		List<Article> articles = this.articleDAO.selectAll();
		

		if (articles != null) {
			articles.forEach(a -> {
				setVendeurByArticle(a);
				setBestEnchereByArticle(a);
			});
		}
		
		return articles;
	}
	
	
	@Override
	public void insertEnchere(Enchere enchere) {
		enchereDAO.insert(enchere);
		
	}
	@Override
	public List<Article> selectMesEncheres(long idUtilisateur, long idCategorie, String nomArticle) {
	    List<Article> articles = this.articleDAO.selectEncheresOuvertes(idCategorie, nomArticle);

	    if (articles != null) {
	        Iterator<Article> it = articles.iterator();

	        while (it.hasNext()) {
	            Article a = it.next();

	            setVendeurByArticle(a);
	            setBestEnchereByArticle(a);

	            Enchere best = enchereDAO.selectBestByArticle(a.getIdArticle());

	            if (best == null || best.getUtilisateur().getIdUtilisateur() != idUtilisateur) {
	                it.remove();
	            }
	        }
	    }

	    return articles;
	}


	public List<Article> selectEncheresOuvertes(long idCategorie, String nomArticle) {
		List<Article> articles = this.articleDAO.selectEncheresOuvertes(idCategorie, nomArticle);
				

				if (articles != null) {
					articles.forEach(a -> {
						setVendeurByArticle(a);
						setBestEnchereByArticle(a);
					});
				}
				
				return articles;
			}
	@Override
	public List<Article> selectMesEncheresRemportees(long idUtilisateur, long idCategorie, String nomArticle) {
		 List<Article> articles = this.articleDAO.selectEncheresTerminees(idCategorie, nomArticle);

		    if (articles != null) {
		        Iterator<Article> it = articles.iterator();

		        while (it.hasNext()) {
		            Article a = it.next();

		            setVendeurByArticle(a);
		            setBestEnchereByArticle(a);

		            Enchere best = enchereDAO.selectBestByArticle(a.getIdArticle());

		            if (best == null || best.getUtilisateur().getIdUtilisateur() != idUtilisateur) {
		                it.remove();
		            }
		        }
		    }

		    return articles;
	}
	public List<Article> selectMesVentesEnCours(long idUtilisateur, long idCategorie, String nomArticle) {
	    List<Article> articles = this.articleDAO.selectEncheresOuvertes(idCategorie, nomArticle);

	    if (articles != null) {
	        Iterator<Article> it = articles.iterator();

	        while (it.hasNext()) {
	            Article a = it.next();

	            setVendeurByArticle(a); 
	            setBestEnchereByArticle(a);

	          
	            if (a.getUtilisateur() == null || a.getUtilisateur().getIdUtilisateur() != idUtilisateur) {
	                it.remove();
	            }
	        }
	    }

	    return articles;
	}
	@Override
	public List<Article> selectMesVentesNonDebutees(long idUtilisateur, long idCategorie, String nomArticle) {
		   List<Article> articles = this.articleDAO.selectEncheresNonDebutees(idCategorie, nomArticle);

		    if (articles != null) {
		        Iterator<Article> it = articles.iterator();

		        while (it.hasNext()) {
		            Article a = it.next();

		            setVendeurByArticle(a); 
		            setBestEnchereByArticle(a);

		          
		            if (a.getUtilisateur() == null || a.getUtilisateur().getIdUtilisateur() != idUtilisateur) {
		                it.remove();
		            }
		        }
		    }

		    return articles;
	}
	@Override
	public List<Article> selectMesVentesTerminees(long idUtilisateur, long idCategorie, String nomArticle) {
		 List<Article> articles = this.articleDAO.selectEncheresTerminees(idCategorie, nomArticle);

		    if (articles != null) {
		        Iterator<Article> it = articles.iterator();

		        while (it.hasNext()) {
		            Article a = it.next();

		            setVendeurByArticle(a); 
		            setBestEnchereByArticle(a);

		          
		            if (a.getUtilisateur() == null || a.getUtilisateur().getIdUtilisateur() != idUtilisateur) {
		                it.remove();
		            }
		        }
		    }

		    return articles;
	}
	@Override
	public void encherir(long idUtilisateur, long idArticle, int montantEnchere) {
		Utilisateur utilisateur = utilisateurDAO.selectById(idUtilisateur);
		Article article = articleDAO.selectById(idArticle);
		
		Enchere enchere = new Enchere();
		
		enchere.setUtilisateur(utilisateur);
		enchere.setArticle(article);
		enchere.setMontantEnchere(montantEnchere);
		enchere.setDateEnchere(LocalDateTime.now());
		
		this.insertEnchere(enchere);
		
	}

	

}
