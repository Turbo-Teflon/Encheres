package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;

@Repository
@Profile("prod")
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String INSERT = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit) "
			+ "VALUES (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse, 100)";


	private static final String SELECT_BY_ID = "SELECT * FROM UTILISATEURS WHERE idUtilisateur = :id";


	private static final String SELECT_BY_EMAIL = "SELECT * FROM UTILISATEURS WHERE email = :email";

	private static final String SELECT_ALL = "SELECT * FROM UTILISATEURS";
	private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = :pseudo, nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, rue = :rue, codePostal = :codePostal, ville = :ville, motDePasse = :motDePasse, credit = :credit, administrateur = :administrateur, actif = :actif WHERE idUtilisateur = :id";

	private static final String DELETE = "DELETE FROM UTILISATEURS WHERE idUtilisateur = :id";
	private static final String SELECT_BY_PSEUDO = "SELECT * FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String SELECT_ROLE = "SELECT role FROM ROLES WHERE idUtilisateur = :id";
	private static final String UPDATE_ROLE = "UPDATE Roles SET role = :role WHERE idUtilisateur = :id";
	private static final String INSERT_ROLE = "INSERT INTO ROLES (idUtilisateur, role) VALUES (:id, :role)";
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_USER = "ROLE_USER";

	private NamedParameterJdbcTemplate jdbcTemplate;
	
	/**
	 * @param jdbcTemplate
	 */
		public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int insert(Utilisateur utilisateur) {
		int nbLigne;
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("codePostal", utilisateur.getCodePostal());
		map.addValue("ville", utilisateur.getVille());
		map.addValue("motDePasse", utilisateur.getMotDePasse());
		map.addValue("credit", utilisateur.getCredit());
		map.addValue("administrateur", utilisateur.isAdministrateur());

		KeyHolder keyHolder = new GeneratedKeyHolder();
		nbLigne = jdbcTemplate.update(INSERT, map, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			
			utilisateur.setIdUtilisateur(keyHolder.getKey().longValue());
		}
		
		return nbLigne;
	}

	@Override
	public Utilisateur selectById(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource("id", id);
		return jdbcTemplate.queryForObject(SELECT_BY_ID, map, new UtilisateurRowMapper());
	}

	@Override
	public Utilisateur selectByPseudo(String pseudo) {
		MapSqlParameterSource map = new MapSqlParameterSource("pseudo", pseudo);
		return jdbcTemplate.queryForObject(SELECT_BY_PSEUDO, map, new UtilisateurRowMapper());
	}

	@Override
	public Utilisateur selectByEmail(String email) {
		MapSqlParameterSource map = new MapSqlParameterSource("email", email);
		return jdbcTemplate.queryForObject(SELECT_BY_EMAIL, map, new UtilisateurRowMapper());
	}
	
	@Override
	public List<Utilisateur> selectAll() {
		
		return jdbcTemplate.query(SELECT_ALL, new UtilisateurRowMapper());
	}


	@Override
	public void update(Utilisateur utilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", utilisateur.getIdUtilisateur());
		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("codePostal", utilisateur.getCodePostal());
		map.addValue("ville", utilisateur.getVille());
		map.addValue("motDePasse", utilisateur.getMotDePasse());
		map.addValue("credit", utilisateur.getCredit());
		map.addValue("administrateur", utilisateur.isAdministrateur());
		map.addValue("actif", utilisateur.isActif());


		jdbcTemplate.update(UPDATE, map);
		
		if(utilisateur.isAdministrateur()) {
			updateRole(utilisateur.getIdUtilisateur(), ROLE_ADMIN);
		}else {
			updateRole(utilisateur.getIdUtilisateur(), ROLE_USER);

		}
	}

	@Override
	public int delete(long id) {
		MapSqlParameterSource map = new MapSqlParameterSource("id", id);
		return jdbcTemplate.update(DELETE, map);
	}

	@Override
	public boolean isAdmin(Utilisateur u) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", u.getIdUtilisateur());
		String role = jdbcTemplate.queryForObject(SELECT_ROLE, map, String.class);
		return role == ROLE_ADMIN;
	}

	@Override
	public void updateRole(long id, String role) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("role", role);
		map.addValue("id", id);
		jdbcTemplate.update(UPDATE_ROLE, map);
	}
	
	@Override
	public void insertRole(long id, String role) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		map.addValue("role", role);
		jdbcTemplate.update(INSERT_ROLE, map);
	}

	class UtilisateurRowMapper implements RowMapper<Utilisateur> {
		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur u = new Utilisateur();
			u.setIdUtilisateur(rs.getInt("idUtilisateur"));
			u.setPseudo(rs.getString("pseudo"));
			u.setNom(rs.getString("nom"));
			u.setPrenom(rs.getString("prenom"));
			u.setEmail(rs.getString("email"));
			u.setTelephone(rs.getString("telephone"));
			u.setRue(rs.getString("rue"));
			u.setCodePostal(rs.getString("codePostal"));
			u.setVille(rs.getString("ville"));
			u.setMotDePasse(rs.getString("motDePasse"));
			u.setCredit(rs.getInt("credit"));
			u.setAdministrateur(rs.getBoolean("administrateur"));
			return u;
		}
	}
	

}
