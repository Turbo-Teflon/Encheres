package fr.eni.encheres.tools;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String password = "pass123";
        String hash = encoder.encode(password);
        System.out.println("Mot de passe : " + password);
        System.out.println("Hash : " + hash);
    }
}
