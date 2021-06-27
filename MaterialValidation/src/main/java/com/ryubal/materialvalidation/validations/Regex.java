package com.ryubal.materialvalidation.validations;

/**
 * Contains basic regular expressions that might be needed.
 */
public class Regex {
    /**
     * Validates one or more letters (a-z or A-Z)
     */
    public static final String LETTERS = "^[a-zA-Z]+$";

    /**
     * Validates one or more numbers (0 to 9)
     */
    public static final String NUMBERS = "^[0-9]+$";

    /**
     * Validates one or more letters or numbers (a-Z, A-Z or 0 to 9)
     */
    public static final String LETTERS_NUMBERS = "^[a-zA-Z0-9]+$";

    /**
     * Validates at least one character
     */
    public static final String NON_EMPTY = "^.+$";
}
