package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Article;

@Repository
public class EnchereDAOImpl implements EnchereDAO {

	private static final String INSERT = "INSERT INTO ENCHERES (idUtilisateur, idArticle, dateEnchere, montantEnchere) " +
	                                     "VALUES (:idUtilisateur, :idArticle, :dateEnchere, :montant)";

	private static final String SELECT_BY_ARTICLE = "SELECT * FROM ENCHERES WHERE idArticle = :idArticle";

	private static final String SELECT_BY_UTILISATEUR = "SELECT * FROM ENCHERES WHERE idUtilisateur = :idUtilisateur";

	private static final String SELECT_BEST_BY_ARTICLE = 
	    "SELECT * FROM ENCHERES WHERE idArticle = :idArticle ORDER BY montantEnchere DESC LIMIT 1";



	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public void insert(Enchere enchere) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUtilisateur", enchere.getUtilisateur().getIdUtilisateur());
		map.addValue("idArticle", enchere.getArticle().getIdArticle());
		map.addValue("dateEnchere", enchere.getDateEnchere());
		map.addValue("montant", enchere.getMontantEnchere());

		jdbcTemplate.update(INSERT, map);
	}

	@Override
	public List<Enchere> selectByArticle(int idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		return jdbcTemplate.query(SELECT_BY_ARTICLE, map, new EnchereRowMapper());
	}

	@Override
	public List<Enchere> selectByUtilisateur(int idUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUtilisateur", idUtilisateur);
		return jdbcTemplate.query(SELECT_BY_UTILISATEUR, map, new EnchereRowMapper());
	}

	@Override
	public Enchere selectBestByArticle(int idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		return jdbcTemplate.queryForObject(SELECT_BEST_BY_ARTICLE, map, new EnchereRowMapper());
	}

	@Override
	public void deleteByArticle(int idArticle) {
		// TODO Auto-generated method stub
		
	}



	class EnchereRowMapper implements RowMapper<Enchere> {
		@Override
		public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
			Enchere e = new Enchere();
			e.setDateEnchere(rs.getTimestamp("dateEnchere").toLocalDateTime());
			e.setMontantEnchere(rs.getInt("montantEnchere"));

			Utilisateur u = new Utilisateur();
			u.setIdUtilisateur(rs.getInt("idUtilisateur"));
			e.setUtilisateur(u);

			Article a = new Article();
			a.setIdArticle(rs.getInt("idArticle"));
			e.setArticle(a);

			return e;
		}
	}



}
