package fr.eni.encheres.configuration.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class MonCustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UtilisateurService utilisateurService;

    public MonCustomLoginSuccessHandler(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String pseudo = authentication.getName();
        System.out.println("pseudo récupéré après login : " + pseudo);

        Utilisateur utilisateur = utilisateurService.selectByPseudo(pseudo);
        System.out.println("utilisateur trouvé : " + utilisateur);        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession();
        session.setAttribute("utilisateur", utilisateur);

        System.out.println("Session ID dans loginSuccessHandler : " + session.getId());

        response.sendRedirect(request.getContextPath() + "/accueil");
    }
}
