package fr.eni.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	UserDetailsManager utilisateurs(DataSource dataSource) {
		
		JdbcUserDetailsManager utilisateurs = new JdbcUserDetailsManager(dataSource);
		utilisateurs.setUsersByUsernameQuery("SELECT PSEUDO, PASSWORD, 1 FROM UTILISATEUR WHERE PSEUDO = ?");
		utilisateurs.setAuthoritiesByUsernameQuery("SELECT PSEUDO, role FROM roles WHERE PSEUDO = ?");
		return utilisateurs;
	}

}
