package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Categorie;

@Profile("prod")
@Repository
public class CategorieDAOImpl implements CategorieDAO {
	
	private static final String SELECT_ALL = "SELECT * FROM CATEGORIES";
	private static final String SELECT_BY_ID = "SELECT * FROM CATEGORIES WHERE idCategorie = :idCategorie";
	private static final String COUNT_CAT = "SELECT count(*) FROM CATEGORIES idCategorie = :idCategorie";


	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<Categorie> selectAll() {
	    return jdbcTemplate.query(SELECT_ALL, new CategorieRowMapper());
	}


	@Override
	public Categorie selectById(long id) {
	    MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("idCategorie", id);
	    
	    return jdbcTemplate.queryForObject(SELECT_BY_ID, map, new CategorieRowMapper());
	}

	

	class CategorieRowMapper implements RowMapper<Categorie> {
		
	    @Override
	    public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException  {
	        Categorie c = new Categorie();
	        c.setIdCategorie(rs.getLong("idCategorie"));
	        c.setLibelle(rs.getString("libelle"));
	        return c;
	    }
	}



	@Override
	public boolean hasCategorie(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
	    map.addValue("idCategorie", id);
	    int nbCat = jdbcTemplate.queryForObject(COUNT_CAT, map, Integer.class);
		return nbCat != 0;
	}

}


