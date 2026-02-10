package com.revhire.ui;

import com.revhire.model.User;
import com.revhire.service.AuthService;

import java.util.Scanner;

public class AuthUI {

    private final Scanner sc = new Scanner(System.in);
    private final AuthService authService = new AuthService();

    public void start() {
        while (true) {
            System.out.println("\n====== Welcome to RevHire ======");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine(); // clear invalid input
                continue;
            }
            sc.nextLine(); // consume leftover newline

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    forgotPassword();
                    break;
                case 0:
                    System.out.println("Thank you for using RevHire. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }


    private void register() {
        System.out.print("Username: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        System.out.print("Role (JOB_SEEKER / EMPLOYER): ");
        String role = sc.nextLine().toUpperCase();

        System.out.print("Security Question: ");
        String q = sc.nextLine();

        System.out.print("Security Answer: ");
        String a = sc.nextLine();

        String result = authService.register(new User(name, email, pass, role, q, a));

        if ("SUCCESS".equals(result))
            System.out.println(" Registration successful!");
        else
            System.out.println("Not Registered " + result);
    }

//    private void login() {
//        System.out.print("Enter Email: ");
//        String email = sc.nextLine();
//
//        System.out.print("Enter Password: ");
//        String pass = sc.nextLine();
//
//        User user = authService.login(email, pass);
//
//        if (user != null) {
//            System.out.println("Login successful!");
//            System.out.println("Welcome " + user.getUsername() + " (" + user.getRole() + ")");
//            // Role-based redirection hook here
//        } else {
//            System.out.println(" Invalid email or password");
//        }
//    }
    private void login() {
        try {
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Password: ");
            String password = sc.nextLine();

            User user = authService.login(email, password);

            if (user.getRole().equalsIgnoreCase("JOB_SEEKER")) {
                JobSeekerProfileUI jobSeekerUI = new JobSeekerProfileUI();
                jobSeekerUI.showDashboard(user);   // EXACT CALL
            } else {
                //System.out.println("Employer module not implemented yet.");
            	EmployerUI.showEmployerDashboard(user.getUserId());
            }

        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void forgotPassword() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Security Answer: ");
        String ans = sc.nextLine();

        System.out.print("New Password: ");
        String newPass = sc.nextLine();

        String result = authService.changePasswordByEmail(email, ans, newPass);

        if ("SUCCESS".equals(result))
            System.out.println(" Password updated successfully!");
        else
            System.out.println("Not Updated " + result);
    }
}
