package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;


@Repository
public class ArticleDAOImpl implements ArticleDAO {
	
	private static final String INSERT = "INSERT INTO ARTICLES (nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAprix, prixVente, etatVente, idUtilisateur, idCategorie) \r\n"
			+ "	VALUES (:nomArticle, :description, :dateDebutEncheres, :dateFinEncheres, :miseAprix, :prixVente, :etatVente, :idUtilisateur, :idCategorie)";

	private static final String SELECT_BY_ID="SELECT * FROM ARTICLES WHERE idArticle = :id";
	private static final String SELECT_BY_CATEGORIE =  "SELECT * FROM ARTICLES WHERE idCategorie = :idCategorie";
	private static final String SELECT_BY_UTILISATEUR ="SELECT * FROM ARTICLES WHERE idUtilisateur = :idUtilisateur";
	private static final String SELECT_ALL = "SELECT * FROM ARTICLES";

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void insert(Article article) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nomArticle", article.getNomArticle());
		mapSqlParameterSource.addValue("description", article.getDescription());
		mapSqlParameterSource.addValue("dateDebutEncheres", article.getDateDebutEncheres());
		mapSqlParameterSource.addValue("dateFinEncheres", article.getDateFinEncheres());
		mapSqlParameterSource.addValue("idUtilisateur", article.getUtilisateur());
		mapSqlParameterSource.addValue("idCategorie", article.getCategorie());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			
			article.setIdArticle(keyHolder.getKey().longValue());
		}
		
	}

	@Override
	public Article selectById(long id) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(SELECT_BY_ID, mapSqlParameterSource, new ArticleRowMapper());
	}

	@Override
	public List<Article> selectAll() {
		return jdbcTemplate.query(SELECT_ALL, new ArticleRowMapper());
	}

	@Override
	public List<Article> selectByCategorie(long idCategorie) {
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("idCategorie", idCategorie);

	    return jdbcTemplate.query(SELECT_BY_CATEGORIE, map, new ArticleRowMapper());
	}


	@Override
	public List<Article> selectByUtilisateur(long idUtilisateur) {
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("idUtilisateur", idUtilisateur);

	    return jdbcTemplate.query(SELECT_BY_UTILISATEUR, params, new ArticleRowMapper());
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
	
	
	class ArticleRowMapper implements RowMapper<Article>{

		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Article a = new Article();
			a.setIdArticle(rs.getLong("idArticle"));
			a.setNomArticle(rs.getString("nomArticle"));
			a.setDescription(rs.getString("description"));
			a.setMiseAPrix(rs.getInt("miseAPrix"));
			a.setPrixVente(rs.getInt("prixVente"));
			a.setEtatVente(rs.getString("etatVente"));
			a.setDateDebutEncheres(rs.getTimestamp("dateDebutEncheres").toLocalDateTime());
			a.setDateFinEncheres(rs.getTimestamp("dateFinEncheres").toLocalDateTime());

			
			Utilisateur util = new Utilisateur();
			util.setIdUtilisateur(rs.getLong("idUtilisateur"));
			a.setUtilisateur(util);

			
			Categorie cat = new Categorie();
			cat.setIdCategorie(rs.getLong("idCategorie"));
			a.setCategorie(cat);

			return a;
		}
		
	}
	
	
	
}
