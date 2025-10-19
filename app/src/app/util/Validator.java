package app.util;

/**
 * Validator.java
 * 👉 Helper class for validation logic.
 * Example: validating user input or email formats.
 */
public class Validator {
    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }
}
