package be.ucll.java.ent.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SecretsGenerator {

    private SecretsGenerator() {
        // Prevent instantiation of class. Static methods calls only
    }

    // https://www.ascii-code.com/

    public static String generateApiKey() {
        int lowestAsciiChar = 48;
        int highestAsciiChar = 122;
        int targetStringLength = 20;
        Random random = new Random();
        return random.ints(lowestAsciiChar, highestAsciiChar + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /*
      Generate a 15 characters long password randomly build from
      3 uppercase, 3 lowercase, 3 numeric, 3 special and 3 alphanumeric characters
     */
    public static String generatePassword() {
        String upperCaseLetters = RandomStringUtils.random(3, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(3, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(3);
        String specialChar = RandomStringUtils.random(3, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(3);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    public static void main(String[] args) {
        System.out.println("Voorbeeld paswoord: " + generatePassword());
        System.out.println("Voorbeeld API Key: " + generateApiKey());
    }

}
