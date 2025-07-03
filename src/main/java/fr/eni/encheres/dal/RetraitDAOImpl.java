package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;

@Repository
public class RetraitDAOImpl implements RetraitDAO {

	private static final String INSERT = "INSERT INTO RETRAITS (idArticle, rue, codePostal, ville) " +
	                                     "VALUES (:idArticle, :rue, :codePostal, :ville)";

	private static final String SELECT_BY_ARTICLE = "SELECT * FROM RETRAITS WHERE idArticle = :idArticle";


	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteByArticle(long idArticle) {
		// TODO Auto-generated method stub
		
	}


	class RetraitRowMapper implements RowMapper<Retrait> {
		@Override
		public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
			Retrait retrait = new Retrait();
			retrait.setRue(rs.getString("rue"));
			retrait.setCodePostal(rs.getString("code_postal"));
			retrait.setVille(rs.getString("ville"));

			Article article = new Article();
			article.setIdArticle(rs.getInt("idArticle"));
			retrait.setArticle(article);

			return retrait;
		}
	}



}

