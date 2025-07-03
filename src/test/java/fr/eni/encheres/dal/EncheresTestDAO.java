package fr.eni.encheres.dal;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import fr.eni.encheres.bo.Utilisateur;

@SpringBootTest
public class EncheresTestDAO {
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private UtilisateurDAO utilisateurDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	void test01_selectAll() {
		String sql = "SELECT COUNT(*) FROM UTILISATEURS";
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		List<Utilisateur> users = utilisateurDAO.selectAll();
		assertNotNull(users);
		assertEquals(count, users.size());
		logger.info(" test 01 - Select All");
		users.forEach(e -> logger.info(e));
	}
	
	@Test
	void test02_findDeleteInsert() {
		List<Utilisateur> users = utilisateurDAO.selectAll();
		Utilisateur u =  users.get(users.size()-1);
		assertNotNull(u);
		logger.info("test 02");
		logger.info(u);
		int l = utilisateurDAO.delete(u.getIdUtilisateur());
		assertEquals(1, l);
		l = utilisateurDAO.insert(u);
		assertEquals(1, l);
		logger.info(u);
		
		
		
	}
}
