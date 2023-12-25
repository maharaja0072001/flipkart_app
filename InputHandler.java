package com.flipkart;

import java.util.Scanner;

/**
 * <p>
 * Responsible for providing single instance of Scanner class.
 * </p>
 *
 * @author Maharaja S
 * @version 1   .0
 */
public class InputHandler {

    private static Scanner scannerInstance;

    /**
     * <p>
     * Default constructor of InputHandler class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private InputHandler() {}

    /**
     * <p>
     * Creates a single object of Scanner class and returns it.
     * </p>
     *
     * @return the single instance of Scanner class.
     */
    public static synchronized Scanner getScanner() {
        if (null == scannerInstance) {
            scannerInstance = new Scanner(System.in);
        }

        return scannerInstance;
    }
}
