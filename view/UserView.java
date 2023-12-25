package com.flipkart.view;

import com.flipkart.controller.UserController;
import com.flipkart.model.User;
import com.flipkart.InputHandler;
import com.flipkart.view.datavalidation.UserDataValidator;

import java.util.Scanner;

/**
 * <p>
 * Responsible for creating, validating the users and updating and printing the user details.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class UserView extends View {

    private static final Scanner SCANNER = InputHandler.getScanner();
    private static final UserController USER_CONTROLLER = UserController.getInstance();
    private static final HomePageView HOME_PAGE_VIEW = HomePageView.getInstance();
    private static final UserDataValidator USER_DATA_VALIDATOR = UserDataValidator.getInstance();
    private static UserView userViewInstance;


    /**
     * <p>
     * Default constructor for UserView class.Kept private to restrict from creating object outside this class.
     * </p>
     */
    private UserView() {}

    /**
     * <p>
     * Creates a single object of UserView class and returns it.
     * </p>
     *
     * @return the single instance of UserView class.
     */
    public static synchronized UserView getInstance() {
        if (null == userViewInstance) {
            userViewInstance = new UserView();
        }

        return userViewInstance;
    }

    /**
     * <p>
     * Creates a new user by getting details from the user.
     * </p>
     *
     */
    public void createNewUser() {
        final User user = new User();

        System.out.println("Enter your details to create new user or press '$' to go back");
        final String name = getName();

        if (USER_DATA_VALIDATOR.isNull(name)) {
           return;
        } else {
            user.setName(name);
        }
        final String mobileNumber = getMobileNumber();

        if (USER_DATA_VALIDATOR.isNull(mobileNumber)) {
            return;
        } else {
            user.setMobileNumber(mobileNumber);
        }
        final String emailId = getEmailId();

        if (USER_DATA_VALIDATOR.isNull(emailId)) {
            return;
        } else {
            user.setEmailId(emailId);
        }
        final String password = getPassword();

        if (USER_DATA_VALIDATOR.isNull(password)) {
            return;
        } else {
            user.setPassword(password);
        }

        if (USER_CONTROLLER.createUser(user)) {
            System.out.println("User created successfully");
            HOME_PAGE_VIEW.showHomePage(user);
        } else {
            System.out.println("User already exists. Please login");
            validateUser();
        }
    }              

    /**
     * <p>
     * Authenticates the existing user by the details given by the user.
     * </p>
     */
    public void validateUser() {
        String mobileNumber = null;
        String emailId = null;
        final Integer choice = getChoice("Press '1' to enter mobile number or '2' to enter email. Press '$' to go back");

        if (USER_DATA_VALIDATOR.isNull(choice)) {
            return;
        }

        switch (choice) {
            case 1:
                mobileNumber = getMobileNumber();

                if (USER_DATA_VALIDATOR.isNull(mobileNumber)) {
                    return;
                }
                break;
            case 2:
                emailId = getEmailId();

                if (USER_DATA_VALIDATOR.isNull(emailId)) {
                    return;
                }
                break;
            default:
                System.out.println("Enter a valid choice");
                validateUser();
                return;
        }

        System.out.println("Enter the Password :");
        final String password = SCANNER.nextLine();

        if (USER_DATA_VALIDATOR.containsToNavigateBack(password)) {
            return;
        }
        final User user = USER_CONTROLLER.getExistingUser(mobileNumber, emailId, password);

        if (null != user) {
            System.out.println("Login successful");
            HOME_PAGE_VIEW.showHomePage(user);
        } else {
            System.out.println("Wrong credentials or user doesn't exist");
        }
    }

    /**
     * <p>
     * shows the profile of the user and updates the details chosen by the user.
     * </p>
     *
     * @param user Refers the current {@link User}
     */
    public void viewAndEditProfile(final User user) {
        final String profile = String.format("User profile:\nName : %s\nEmail Id : %s\nMobile : +%s\nEnter '1' to edit details or press $ to go back",
                user.getName(), user.getEmailId(), user.getMobileNumber());
        final Integer choice = getChoice(profile);

        if (USER_DATA_VALIDATOR.isNull(choice)) {
            return;
        }

        if (1 == choice) {
            final User updatedUser = updateUserDetails(user);

            USER_CONTROLLER.updateUser(updatedUser);
        } else {
            viewAndEditProfile(user);
        }
    }

    /**
     * <p>
     * Updates the details of the current user .
     * </p>
     *
     * @param user Refers the current {@link User}
     */
    private User updateUserDetails(final User user) {
        System.out.println("Do you want to edit name ?. Press '1' for yes or any key to no.");
        String input = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.validateAccess(input)) {
            final String name = getName();

            if (!USER_DATA_VALIDATOR.isNull(name)) {
                user.setName(name);
                System.out.println("Name is updated");
            }
        } else if (USER_DATA_VALIDATOR.containsToNavigateBack(input)) {
            return user;
        }
        System.out.println("Do you want to edit email id ?. Press '1' for yes or any key to no.");

        input = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.validateAccess(input)) {
            final String emailId = getEmailId();

            if (!USER_DATA_VALIDATOR.isNull(emailId)) {
                user.setEmailId(emailId);
                System.out.println("Email id is updated");
            }
        } else if (USER_DATA_VALIDATOR.containsToNavigateBack(input)) {
            return user;
        }
        System.out.println("Do you want to edit mobile number ?. Press '1' for yes or any key to no.");

        input = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.validateAccess(input)) {
            final String mobileNumber = getMobileNumber();

            if (!USER_DATA_VALIDATOR.isNull(mobileNumber)) {
                user.setMobileNumber(mobileNumber);
                System.out.println("Mobile number id is updated");
            }
        } else if (USER_DATA_VALIDATOR.containsToNavigateBack(input)) {
            return user;
        }
        System.out.println("Do you want to edit password ?. Press '1' for yes or any key to no.");
        input = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.validateAccess(input)) {
            final String password = getPassword();

            if (!USER_DATA_VALIDATOR.isNull(password)) {
                user.setPassword(password);
                System.out.println("Password id is updated");

            }
        }
        
        return user;
    }

    /**
     * <p>
     * Gets a valid name from the user and returns it.
     * </p>
     *
     * @return the name of the user.
     */
    private String getName() {
        System.out.println("Enter your name :");
        final String name = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.containsToNavigateBack(name)) {
            return null;
        }

        if (USER_DATA_VALIDATOR.isValidName(name)) {
            return name;
        } else {
            System.out.println("Entered Name is invalid");
        }

        return getName();
    }

    /**
     * <p>
     * Gets a valid password from the user and returns it.
     * </p>
     *
     * @return the password of the user.
     */
    private String getPassword() {
        System.out.println(String.join(" ", "Create a password:[Password should contain",
                "an uppercase, a lowercase, a special character and a digit. Minimum length is 8]"));
        final String password = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.containsToNavigateBack(password)) {
            return null;
        }

        if (USER_DATA_VALIDATOR.isValidPassword(password)) {
            return password;
        } else {
            System.out.println("Entered password is invalid");
        }

        return getPassword();
    }

    /**
     * <p>
     * Gets a valid email id from the user and returns it.
     * </p>
     *
     * @return the email id of the user.
     */
    private String getEmailId() {
        System.out.println("Enter your email id:");
        final String emailId = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.containsToNavigateBack(emailId)) {
            return null;
        }

        if (USER_DATA_VALIDATOR.isValidEmail(emailId)) {
            return emailId;
        } else {
            System.out.println("Entered email id is invalid.");
        }

        return getEmailId();
    }

    /**
     * <p>
     * Gets a valid mobile number from the user and returns it.
     * </p>
     *
     * @return the mobile number of the user.
     */
    private String getMobileNumber() {
        System.out.println("Choose your country code :\n1.America\n2.Australia\n3.China\n4.Germany\n5.India");
        final String choice = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.containsToNavigateBack(choice)) {
            return null;
        }

        if (!USER_DATA_VALIDATOR.isChoiceValid(choice)) {
            System.out.println("Invalid choice");

            return getMobileNumber();
        }
        System.out.println("Enter your mobile number with country code:");
        final String mobileNumber = SCANNER.nextLine().trim();

        if (USER_DATA_VALIDATOR.containsToNavigateBack(mobileNumber)) {
            return null;
        }

        if (USER_DATA_VALIDATOR.isValidMobileNumber(mobileNumber, choice)) {
            return mobileNumber;
        } else {
            System.out.println("Entered mobile number is invalid.");
        }

        return getMobileNumber();
    }
}


