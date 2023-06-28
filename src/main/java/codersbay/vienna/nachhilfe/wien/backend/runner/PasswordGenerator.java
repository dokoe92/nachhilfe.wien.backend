package codersbay.vienna.nachhilfe.wien.backend.runner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Password in the data.sql are not encrypted so use the password which was inserted in the data.sql and put it in the password generator to access the user in the StartUpRunner
 */
public class PasswordGenerator {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String generateBcryptPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

}
