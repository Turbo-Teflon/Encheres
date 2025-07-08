package fr.eni.encheres.configuration.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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

	@Bean
	public UserDetailsManager userDetailsManager(DataSource dataSource) {
	    JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
	    users.setUsersByUsernameQuery("SELECT pseudo, motDePasse, 1 AS enabled FROM Utilisateurs WHERE pseudo = ?");
	    
	    users.setAuthoritiesByUsernameQuery("SELECT pseudo, 'ROLE_USER' FROM Utilisateurs WHERE pseudo = ?");

	    return users;
	}   

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/accueil", "/connexion", "/creer-compte", "/css/**", "/js/**", "/images/**", "/fragments").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form            	
                .loginPage("/connexion")
                .defaultSuccessUrl("/accueil", true)
                .failureUrl("/connexion?error")
                .usernameParameter("identifiant")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler(utilisateurService))
                
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/deconnexion")
                .logoutSuccessUrl("/connexion?logout")
                .permitAll()
            );

        return http.build();
    }
    
    @Autowired
    private UtilisateurService utilisateurService;

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(UtilisateurService utilisateurService) {
        return (request, response, authentication) -> {
            String pseudo = authentication.getName();
            System.out.println("pseudo récupéré après login : " + pseudo);
            Utilisateur utilisateur = utilisateurService.selectByPseudo(pseudo);
            System.out.println("utilisateur trouvé : " + utilisateur);
            request.getSession().setAttribute("utilisateur", utilisateur); 
            response.sendRedirect("/accueil");
        };
    }


    /*@Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println(">>> PasswordEncoder utilisé : DelegatingPasswordEncoder");
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
