package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        boolean is_running = true;

        while (is_running) {
            System.out.println("\n==== Chat System Menu ====");
            System.out.println("1. Register user");
            System.out.println("2. Create a new Chat");
            System.out.println("3. Send message");
            // inside chat
            // System.out.println("4. Delete message");
            System.out.println("4. Delete user");
            System.out.println("5. Show Chat");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int option;
            try {
                option = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (input) {
                case 1:
                    if(! register(input)){
                        System.out.println("User not registred.");
                    }
                    break;

                case 2: // Create Conversation
                    System.out.print("Enter participant IDs (comma-separated): ");
                    String[] participantIds = input.nextLine().split(",");
                    List<Integer> participants = new ArrayList<>();
                    for (String id : participantIds) {
                        try {
                            participants.add(Integer.parseInt(id.trim()));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID: " + id);
                        }
                    }
                    try {
                        chatSystem.criarConversa(participants);
                        System.out.println("Conversation created!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3: // Send Message
                    System.out.print("Enter conversation ID: ");
                    int conversationId = Integer.parseInt(input.nextLine());
                    System.out.print("Enter your user ID: ");
                    int userId = Integer.parseInt(input.nextLine());
                    System.out.print("Enter message: ");
                    String message = input.nextLine();
                    try {
                        chatSystem.enviarMensagem(conversationId, userId, message);
                        System.out.println("Message sent!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4: // Delete user
                    if(! delete(input)){
                        // didn`t delete
                    }

                case 5: // Display Conversation
                    System.out.print("Enter conversation ID: ");
                    int displayConvId = Integer.parseInt(input.nextLine());
                    try {
                        chatSystem.exibirConversa(displayConvId);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6: // Exit
                    isRunning = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        input.close();
    }

    public static boolean delete(Scanner input){

    }

    public static boolean register(Scanner input){

        while (true) {
            System.out.println("To cancel signing up type '0'");

            System.out.println("Username: "); String username = input.nextLine().strip();
            if(username.equals("0")) return false;

            if(! ChatSystem.getInstance().validateUsername(username)) {
                System.out.println("Invalid username, try again.");
                continue;
            }

            System.out.println("Email: "); String email = input.nextLine().strip();
            if(email.equals("0")) return false;

            if(! ChatSystem.getInstance().validateEmailFormat(email)){
                System.out.println("Invalid email. \n" +
                        "Emails need to have this structure[email@something.com]");
                continue;
            }

            if(! ChatSystem.getInstance().validateIfEmailExist(email)){
                System.out.println("This email is already being used.");
                continue;
            }

            System.out.println("Password: "); String password = input.nextLine().strip();
            if(password.equals("0")) return false;

            if(! ChatSystem.getInstance().validatePassword(password)){
                System.out.println("Password must contain at least 7 characters");
                continue;
            }

            ChatSystem.getInstance().registerNewUser(username, email, password);
            System.out.println("---SIGNING UP NEW USER---");

            System.out.println("Press anything to continue...");
            input.nextLine();

            return true;
        }
    }
}

