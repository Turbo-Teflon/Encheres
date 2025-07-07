package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

@Repository
@Profile("prod")
public class EnchereDAOImpl implements EnchereDAO {

	private static final String INSERT = "INSERT INTO ENCHERES (idUtilisateur, idArticle, dateEnchere, montantEnchere) " +
	                                     "VALUES (:idUtilisateur, :idArticle, :dateEnchere, :montant)";

	private static final String SELECT_BY_ARTICLE = "SELECT * FROM ENCHERES WHERE idArticle = :idArticle";

	private static final String SELECT_BY_UTILISATEUR = "SELECT * FROM ENCHERES WHERE idUtilisateur = :idUtilisateur";

	private static final String SELECT_BEST_BY_ARTICLE = "SELECT TOP 1 * FROM ENCHERES WHERE idArticle = :idArticle ORDER BY montantEnchere DESC";
	
	private static final String DELETE_BY_ARTICLE = "DELETE FROM ENCHERES WHERE idArticle = :id";
	private static final String DELETE_BY_UTILISATEUR = "DELETE FROM ENCHERES WHERE idUtilisateur = :id";



	private NamedParameterJdbcTemplate jdbcTemplate;
	
	

	/**
	 * @param jdbcTemplate
	 */
	public EnchereDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(Enchere enchere) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUtilisateur", enchere.getUtilisateur().getIdUtilisateur());
		map.addValue("idArticle", enchere.getArticle().getIdArticle());
		map.addValue("dateEnchere", enchere.getDateEnchere());
		map.addValue("montantEnchere", enchere.getMontantEnchere());

		jdbcTemplate.update(INSERT, map);
	}

	@Override
	public List<Enchere> selectByArticle(long idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		return jdbcTemplate.query(SELECT_BY_ARTICLE, map, new EnchereRowMapper());
	}

	@Override
	public List<Enchere> selectByUtilisateur(long idUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idUtilisateur", idUtilisateur);
		return jdbcTemplate.query(SELECT_BY_UTILISATEUR, map, new EnchereRowMapper());
	}

	@Override
	public Enchere selectBestByArticle(long idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		return jdbcTemplate.queryForObject(SELECT_BEST_BY_ARTICLE, map, new EnchereRowMapper());
	}

	@Override
	public int deleteByArticle(long idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", idArticle);
		return jdbcTemplate.update(DELETE_BY_ARTICLE, map);
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



	@Override
	public int deleteByUtilisateur(long idUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", idUtilisateur);
		return jdbcTemplate.update(DELETE_BY_UTILISATEUR, map);
	}



}
