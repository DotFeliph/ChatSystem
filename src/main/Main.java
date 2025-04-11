package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean is_running = true;
        Scanner input = new Scanner(System.in);
        User loggedUser;

        while(is_running) {
            System.out.println("\n=== Chat System ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("->Choose option: ");

            try {
                int choice = Integer.parseInt(input.nextLine());

                switch(choice) {
                    case 1:
                        loggedUser = login(input);
                        if(loggedUser != null) {
                            chatMenu(input, loggedUser, is_running);
                        }
                        break;

                    case 2:
                        if(! register(input)) {
                            System.out.println("User did not sign up");
                        }
                        break;

                    case 3:
                        System.out.println("Exiting...");
                        System.out.println("[Press anything to continue...]");
                        input.nextLine();
                        is_running = false;
                        break;
                    default:
                        System.out.println("Invalid option!");}
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }

        input.close();

    }


    public static void chatMenu(Scanner input, User user, boolean is_running){
        while (is_running) {

            System.out.println("\n==== Chat System Menu ====");
            System.out.println("1. Create a new Chat");
            System.out.println("2. Delete user");
            System.out.println("3. Send message");
            System.out.println("4. Show Chat");
            System.out.println("0. Exit");
            System.out.print("->Enter your choice: ");

            // probably inside chat as well
            // inside chat
            // System.out.println("4. Delete message");
            int option;
            try {
                option = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (option) {

                case 1: // Create new chat
                    if(! createChat(input)){
                        System.out.println("Chat not created.");
                    }

                case 2: // Delete user
                    if(! deleteUser(input)){
                        System.out.println("---User not deleted---");
                    }

                case 3: // Send Message


                    break;
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
            if(! ChatSystem.getInstance().initChat(participants)){
                System.out.println("Error when creating the chat");
                System.out.println("Do you want do try agai? [y/n]");
                return ! (input.nextLine().strip().equalsIgnoreCase("n"));
            }
            System.out.println("---New chat created---");
            return true;
        }
    }

    public static boolean deleteUser(Scanner input){
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

            if(! ChatSystem.getInstance().isPasswordValid(password)){
                System.out.println("Password must contain at least 7 characters");
                continue;
            }

            ChatSystem.getInstance().registerNewUser(username, email, password);
            System.out.println("---NEW USER SIGN UP ---");

            System.out.println("[Press anything to continue...]");
            input.nextLine();

            return true;
        }
    }

    private static User login(Scanner input) {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = input.nextLine().strip();
        System.out.print("Password: ");
        String password = input.nextLine().strip();

        User user = ChatSystem.getInstance().authenticateUser(email, password);
        if(user == null) {
            System.out.println("Invalid credentials or user not found.");
            return null;
        }
        System.out.println("---Login successful---");
        System.out.println("User: " + user.getName() + "!");
        return user;
    }

        public static  void sendMessage(Scanner input){

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
        }
}

