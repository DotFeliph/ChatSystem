package main;

import javax.script.ScriptEngine;
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

                case 2: // Create new chat
                    if(! createChat(input)){
                        ///
                    }

                case 3: // Send Message
                    System.out.print("Enter conversation ID: ");
                    int conversationId = Integer.parseInt(input.nextLine());
                    System.out.print("Enter your user ID: ");
                    int userId = Integer.parseInt(input.nextLine());
                    System.out.print("Enter message: ");
                    String message = input.nextLine();
                    try {
//                        chatSystem.enviarMensagem(conversationId, userId, message);
                        System.out.println("Message sent!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4: // Delete user
                    if(! deleteChat(input)){
                        System.out.println("---User not deleted---");
                    }

                case 5: // Display Conversation
                    System.out.print("Enter conversation ID: ");
                    int displayConvId = Integer.parseInt(input.nextLine());
                    try {
//                        chatSystem.exibirConversa(displayConvId);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6: // Exit
//                    isRunning = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        input.close();
    }

    public static boolean createChat(Scanner input){
        ArrayList<Integer> participants = new ArrayList<>();

        while (true){
            System.out.println("---New chat---");
            ChatSystem.getInstance().listAllUsers();
            System.out.println("[To return to te menu type '0']");
            System.out.print("Enter participant IDs (comma-separated): ");

            String inp = input.nextLine().strip();
            if (inp.equals("0")) return false;

            String[] participant_ids =inp.split(",");
            participants.clear();

            for (String id : participant_ids) {
                if(! ChatSystem.getInstance().validateUserId(id)) {
                    System.out.println("Invalid ID: " + id);
                }
                else participants.add( Integer.parseInt(id.strip()) );
            }

            if (participants.size() < 2){
                System.out.println("A chat requires at least 2 participants.");
                continue;
            }
            ChatSystem.getInstance().initChat(participants);
            System.out.println("---New chat created---");
            return true;
        }
    }


    public static boolean deleteChat(Scanner input){
        while (true){
            System.out.println("---Deleting User---");
            System.out.println("[To return to the menu type '0']");
            System.out.print("Enter user ID: ");

            String del_user_id = input.nextLine().strip();
            if(del_user_id.equals("0")) return false;

            if(! ChatSystem.getInstance().validateUserId(del_user_id)){
                System.out.println("Invalid user id, try another one.");
                continue;
            }

            if(! ChatSystem.getInstance().deleteUser(del_user_id)){
                System.out.println("User not found. Want to try again? [Y/n]");
                return ! (input.nextLine().strip().equalsIgnoreCase("n"));
            }
            System.out.println("---User deleted---");
            return true;
        }
    }

    public static boolean register(Scanner input){

        while (true) {
            System.out.println("[To cancel signing up type '0']");

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

            System.out.println("[Press anything to continue...]");
            input.nextLine();

            return true;
        }
    }
}

