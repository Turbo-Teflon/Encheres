package fr.eni.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UtilisateurService utilisateurService;
	public SecurityConfig(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // Gestion des utilisateurs avec JDBC
    @Bean
    public UserDetailsManager utilisateurs(DataSource dataSource) {
        JdbcUserDetailsManager utilisateurs = new JdbcUserDetailsManager(dataSource);
        utilisateurs.setUsersByUsernameQuery(
            "SELECT pseudo AS username, motDePasse AS password, 1 AS enabled FROM UTILISATEURS WHERE pseudo = ?"
        );
        utilisateurs.setAuthoritiesByUsernameQuery(
            "SELECT pseudo AS username, 'ROLE_USER' AS authority FROM UTILISATEURS WHERE pseudo = ?"
        );
        return utilisateurs;
    }

    // Configuration des règles de sécurité
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/connexion", "/creer-compte", "/css/**", "/images/**, /accueil").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/connexion")
                .failureUrl("/connexion?error")
                .usernameParameter("identifiant")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler(utilisateurService))
                .defaultSuccessUrl("/accueil", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/deconnexion")
                .logoutSuccessUrl("/connexion?logout")
                .permitAll()
            );

        return http.build();
    }
    @Bean
    public CommandLineRunner testHashManuel(PasswordEncoder encoder) {
        return args -> {
            String hash = "$2a$10$ZESv0BIvq4G6gobpWdoOzeb3sOV7CbkDjeHvMcdMZq9H4RcMOL3SO";
            System.out.println("MATCH TEST MANUEL → " + encoder.matches("pass123", hash));
        };
    }    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(UtilisateurService utilisateurService) {
        return (request, response, authentication) -> {
            String pseudo = authentication.getName();
            Utilisateur utilisateur = utilisateurService.selectByPseudo(pseudo);
            request.getSession().setAttribute("utilisateurConnecte", utilisateur);
            response.sendRedirect("/accueil");
        };
    }
}
