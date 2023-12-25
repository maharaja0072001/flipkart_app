package com.flipkart.view.datavalidation;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <p>
 * Responsible for validating the input given by the users
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class UserDataValidator {

    private static UserDataValidator userDataValidatorInstance;

    /**
     * <p>
     * Default constructor of UserDataValidator class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private UserDataValidator() {}

    /**
     * <p>
     * Creates a single object of UserDataValidator class and returns it.
     * </p>
     *
     * @return the single instance of UserDataValidator class.
     */
    public static UserDataValidator getInstance() {
        if (null == userDataValidatorInstance) {
            userDataValidatorInstance = new UserDataValidator();
        }

        return userDataValidatorInstance;
    }

    /**
     * <p>
     * Validates the name by the given regular expression pattern.
     * </p>
     *
     * @param name Refers the name entered by the user to ba validated.
     * @return true if the entered name is valid or false otherwise.
     */
    public boolean isValidName(final String name) {
        return Pattern.matches("^[A-Za-z][A-Za-z\\s]{2,30}$", name);
    }

    /**
     * <p>
     * Validates the password by the given regular expression pattern.
     * </p>
     *
     * @param password Refers the password entered by the user to be validated.
     * @return true if the entered password is valid or false otherwise.
     */
    public boolean isValidPassword(final String password) {
        return Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", password);
    }

    /**
     * <p>
     * Validates the email id by the given regular expression pattern.
     * </p>
     *
     * @param emailId Refers the email id entered by the user to be validated.
     * @return true if the entered email id is valid or false otherwise.
     */
    public boolean isValidEmail(final String emailId) {
        return Pattern.matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[A-Za-z0-9]{2,}([.][a-zA-Z0-9]{2,})+$", emailId);
    }

    /**
     * <p>
     * Validates the mobile number by the given regular expression pattern.
     * </p>
     *
     * @param mobileNumber Refers the mobile number entered by the user to be validated.
     * @param choice the choice of the user for country code.
     * @return true if the entered mobile number is valid or false otherwise.
     */
    public boolean isValidMobileNumber(final String mobileNumber, final String choice) {
        final String forAmerica = "^1[2-9][\\d]{2}[2-9][\\d]{2}[\\d]{4}$";
        final String forAustralia = "^61(4|04)\\d{8}$";
        final String forGermany = "^49[1][5-79]\\d{9}$";
        final String forChina = "^86[1][3-9][0-9]{9}$";
        final String forIndia = "^91[6789]\\d{9}$";

        switch (choice) {
            case "1":
                return Pattern.matches(forAmerica, mobileNumber);
            case "2":
                return Pattern.matches(forAustralia, mobileNumber);
            case "3":
                return Pattern.matches(forChina, mobileNumber);
            case "4":
                return Pattern.matches(forGermany, mobileNumber);
            case "5":
                return Pattern.matches(forIndia, mobileNumber);
        }

        return false;
    }

    /**
     * <p>
     * Validates the choice entered by the user by the given regular expression pattern.
     * </p>
     *
     * @param choice Refers the choice entered by the user to be validated.
     * @return true if the entered choice is valid or false otherwise.
     */
    public boolean isChoiceValid(final String choice) {
        return Pattern.matches("^[1-5]$", choice);
    }

    /**
     * <p>
     * Checks whether the given object is null or not
     * </p>
     *
     * @param object Refers the object to be checked for null.
     * @return true if the object is null or false otherwise.
     */
    public boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    /**
     * <p>
     * Checks whether the given input contains a character for navigating back.
     * </p>
     *
     * @param input Refers the input to be checked for the presence of the character.
     * @return true if the input contains the character for navigating back or false otherwise.
     */
    public boolean containsToNavigateBack(final String input) {
        return "$".equals(input);
    }

    public boolean containsToShowFilterMenu(final String input) {
        return "#".equals(input);
    }

    /**
     * <p>
     * validates the input given by the user for the access.
     * </p>
     *
     * @param input Refers the input to be validated.
     * @return true if the input is valid for access or false otherwise.
     */
    public boolean validateAccess(final String input) {
        return "1".equals(input);
    }
}
