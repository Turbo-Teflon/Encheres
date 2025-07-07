package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;

@Repository
@Profile("prod")
public class RetraitDAOImpl implements RetraitDAO {

	private static final String INSERT = "INSERT INTO RETRAITS (idArticle, rue, codePostal, ville) " +
	                                     "VALUES (:idArticle, :rue, :codePostal, :ville)";

	private static final String SELECT_BY_ARTICLE = "SELECT * FROM RETRAITS WHERE idArticle = :idArticle";
	
	private static final String DELETE = "DELETE FROM RETRAITS WHERE idArticle = :idArticle";
	private static final String UPDATE = "UPDATE UTILISATEURS SET rue = :rue, codePostal= :codePostal, ville = :ville WHERE idArticle = :id";

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	

	/**
	 * @param jdbcTemplate
	 */
	public RetraitDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(Retrait retrait) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", retrait.getArticle().getIdArticle());
		map.addValue("rue", retrait.getRue());
		map.addValue("codePostal", retrait.getCodePostal());
		map.addValue("ville", retrait.getVille());

		jdbcTemplate.update(INSERT, map);
	}

	@Override
	public Retrait selectByArticle(long idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);

		return jdbcTemplate.queryForObject(SELECT_BY_ARTICLE, map, new RetraitRowMapper());
	}
	
	@Override
	public void update(Retrait retrait) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", retrait.getArticle().getIdArticle());
		map.addValue("rue", retrait.getRue());
		map.addValue("codePostal", retrait.getCodePostal());
		map.addValue("ville", retrait.getVille());
		
		jdbcTemplate.update(UPDATE, map);
		
	}

	@Override
	public void deleteByArticle(long idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("idArticle", idArticle);
		
		jdbcTemplate.update(DELETE, map);
		
	}


	class RetraitRowMapper implements RowMapper<Retrait> {
		@Override
		public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
			Retrait retrait = new Retrait();
			retrait.setRue(rs.getString("rue"));
			retrait.setCodePostal(rs.getString("codePostal"));
			retrait.setVille(rs.getString("ville"));

			Article article = new Article();
			article.setIdArticle(rs.getInt("idArticle"));
			retrait.setArticle(article);

			return retrait;
		}
	}



}

