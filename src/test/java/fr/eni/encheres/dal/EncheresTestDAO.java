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
		logger.info("Select All");
		users.forEach(e -> logger.info(e));
	}
	
	void test02_findDeleteInsertFind() {
		List<Utilisateur> users = utilisateurDAO.selectAll();
		Utilisateur u =  users.get(users.size()-1);
		//TODO finir cette foutue classe de test
	}
}
