package fr.eni.encheres.tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "pass123";
        String hash = encoder.encode(password);
        System.out.println("Mot de passe : " + password);
        System.out.println("Hash : " + hash);
    }
}
