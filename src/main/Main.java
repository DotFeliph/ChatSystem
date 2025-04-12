package main;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean is_running = true;
        Scanner input = new Scanner(System.in);
        User loggedUser;

        while (is_running) {
            System.out.println("\n=== Chat System ===");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("->Choose option: ");

            try {
                int choice = Integer.parseInt(input.nextLine());

                switch (choice) {
                    case 1:
                        loggedUser = login(input);
                        if (loggedUser != null) {
                            chatMenu(input, loggedUser);
                        }
                        break;

                    case 2:
                        if (!register(input)) {
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
                        System.out.println("Invalid option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }

        input.close();

    }

    private static void chatMenu(Scanner input, User user) {
        while (true) {

            System.out.println("\n==== Chat System Menu ====");
            System.out.println("1. Create a new Chat");
            System.out.println("2. Delete user");
            System.out.println("3. Send message");
            System.out.println("4. Show Chat");
            System.out.println("0. Exit");
            System.out.print("->Enter your choice: ");

            int option;
            try {
                option = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number between 0 and 4.");
                continue;
            }

            switch (option) {

                case 1: // Create new chat
                    if (!createChat(input, user)) {
                        System.out.println("Chat not created.");
                    }
                    break;
                case 2: // Delete user
                    if (!deleteUser(input, user)) {
                        System.out.println("---Operation cancelled---");
                    } else {
                        return;
                    }

                    break;
                case 3: // Send Message
                    // TODO: cancel operation
                    sendMessage(input, user);
                    break;

                case 4: // Display Conversation
                    if (ChatSystem.getInstance().getChats().isEmpty()) {
                        System.out.println("---You did not started a chat yet---");
                    }
                    showChats(input, user);

                    break;

                case 0: // Exit
                    System.out.println("Exiting...");
                    System.out.println("[Press anything to continue...]");
                    input.nextLine();
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

    }

    // tested FOR one user
    private static boolean createChat(Scanner input, User user) {

        if (ChatSystem.getInstance().getUsers().size() == 1) {
            System.out.println("The system needs at least 2 users in the platform to create a new chat");
            System.out.println("[Press anything to continue...]");
            input.nextLine();
            return false;
        }

        ArrayList<Integer> participants = new ArrayList<>();

        while (true) {

            System.out.println("---New chat---");
            ChatSystem.getInstance().listAllUsers(user);
            System.out.println("[To return to te menu type '0']");
            System.out.print("Enter participant IDs (comma-separated): ");

            String inp = input.nextLine().strip();
            if (inp.equals("0")) return false;

            String[] participant_ids = inp.split(",");

            participants.clear();

            for (String id : participant_ids) {
                if (!ChatSystem.getInstance().validateUserId(id)) {
                    System.out.println("Invalid ID: " + id);
                } else participants.add(Integer.parseInt(id.strip()));
            }

            if (participants.size() < 2) {
                System.out.println("A chat requires at least 2 participants.");
                continue;
            }

            if (!ChatSystem.getInstance().initChat(participants, user)) {
                System.out.println("Error when creating the chat");
                System.out.println("Do you want do try again? [y/n]");
                return !(input.nextLine().strip().equalsIgnoreCase("n"));
            }

            System.out.println("---New chat created---");
            return true;
        }
    }

    // tested for ONE user
    private static boolean deleteUser(Scanner input, User user) {

        while (true) {
            System.out.println("---Deleting User---");
            System.out.println("Want to enter Super user mode to delete any user: [Y/N]: ");
            String option = input.nextLine().strip().toLowerCase();

            while (!option.equals("n") && !option.equals("y")) {
                System.out.println("Invalid option.");
                System.out.println("Want to enter Super user mode to delete any user: [Y/N]: ");
                option = input.nextLine().strip().toLowerCase();

            }

            // user did not choose SU mode
            if (option.equals("n")) {
                System.out.println("User: " + user.getName());
                System.out.println("---[THIS OPERATION IS IRREVERSIBLE.]---\n" +
                        "You will be removed " +
                        "from all chats you participate, as well as your massages.\n" +
                        "Confirm: [Y/N]: ");
                option = input.nextLine().strip().toLowerCase();
                while (!option.equals("n") && !option.equals("y")) {

                    System.out.println("Invalid option.");
                    System.out.println("Confirm: [Y/N]: ");
                    option = input.nextLine().strip().toLowerCase();
                }
                // deleting user
                if (option.equals("y")) {
                    System.out.println("Deleting user: " + ChatSystem.getInstance().getUsers().get(user.getId()).getName());
                    System.out.println("[Press anything to continue...]");
                    input.nextLine();
                    return ChatSystem.getInstance().deleteUser(Integer.toString(user.getId()));
                }

                // normal user did not delete his account
                else return false;
            }

            // SU mode
            else {
                String su_username, su_password;
                System.out.println("SU Username: ");
                su_username = input.nextLine().strip().toLowerCase();

                System.out.println("SU Password: ");
                su_password = input.nextLine().strip().toLowerCase();

                // if credentials are invalid
                if (!ChatSystem.getInstance().authenticateSuperUser(su_username, su_password)) {
                    System.out.println("Username or password did not match.");
                    System.out.println("[Press anything to continue...]");
                    input.nextLine();
                    return false;
                } else {
                    System.out.print("Enter user ID: ");
                    String del_user_id = input.nextLine().strip();

                    if (!ChatSystem.getInstance().validateUserId(del_user_id)) {
                        System.out.println("Invalid user id, try another one.");
                        continue;
                    }

                    if (!ChatSystem.getInstance().deleteUser(del_user_id)) {
                        System.out.println("User not found. Want to try again? [Y/n]");
                        return (input.nextLine().strip().equalsIgnoreCase("n"));
                    }
                    System.out.println("---User deleted---");
                    System.out.println("[Press anything to continue...]");
                    input.nextLine();
                    return true;
                }
            }

        }
    }

    // tested, ok
    private static boolean register(Scanner input) {

        while (true) {
            System.out.println("[To cancel signing up type '0']");

            System.out.println("Username: ");
            String username = input.nextLine().strip();
            if (username.equals("0")) return false;

            if (!ChatSystem.getInstance().validateUsername(username)) {
                System.out.println("Invalid username, try again.");
                continue;
            }

            System.out.println("Email: ");
            String email = input.nextLine().strip();
            if (email.equals("0")) return false;

            if (!ChatSystem.getInstance().validateEmailFormat(email)) {
                System.out.println("Invalid email. \n" +
                        "Emails need to have this structure[email@something.com]");
                continue;
            }

            if (!ChatSystem.getInstance().validateIfEmailExist(email)) {
                System.out.println("This email is already being used.");
                continue;
            }

            System.out.println("Password: ");
            String password = input.nextLine().strip();
            if (password.equals("0")) return false;

            if (!ChatSystem.getInstance().isPasswordValid(password)) {
                System.out.println("Password must contain at least 7 characters");
                continue;
            }

            ChatSystem.getInstance().registerNewUser(username, email, password);
            System.out.println("---NEW USER SIGN UP ---");
            System.out.println("now you can login");

            System.out.println("[Press anything to continue...]");
            input.nextLine();

            return true;
        }
    }

    // tested, ok
    private static User login(Scanner input) {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = input.nextLine().strip();
        System.out.print("Password: ");
        String password = input.nextLine().strip();

        User user = ChatSystem.getInstance().authenticateUser(email, password);
        if (user == null) {
            System.out.println("Invalid credentials or user not found.");
            return null;
        }
        System.out.println("---Login successful---");
        System.out.println("User: " + user.getName() + "!");
        return user;
    }

    // tested for ONE user
    private static void sendMessage(Scanner input, User user) {
        if (ChatSystem.getInstance().getChats().isEmpty()) {
            System.out.println("To send a message you need to create a chat first.");
            System.out.println("[Press anything to continue...]");
            input.nextLine();
            return;
        }
        ChatSystem.getInstance().listAllChats();

        System.out.print("Enter conversation ID: ");
        int conversation_id = Integer.parseInt(input.nextLine());

        final Chat chat = ChatSystem.getInstance().getChats().get(conversation_id);

        if (!ChatSystem.getInstance().isUserInChat(user, chat)) {
            System.out.println("Only participants of the chat can send messages");
            System.out.println("[Press anything to continue...]");
            input.nextLine();
            return;
        }

        System.out.print("Enter message: ");
        String message = input.nextLine();
        try {
            ChatSystem.getInstance().sendMessage(user, chat, message);
            System.out.println("---Message sent---");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showChats(Scanner input, User user) {
        //todo: option to user delete message

        if (ChatSystem.getInstance().getChats().isEmpty()) {
            System.out.println("---There is no chats yet---");
            System.out.println("[Press anything to continue...]");
            input.nextLine();
            return;
        }

        ChatSystem.getInstance().listAllChats();
        System.out.println("-> Choose a chat: ");

        boolean not_valid = true;
        int choice;

        while(not_valid){
            try {
                choice = Integer.parseInt(input.nextLine());
                not_valid = false;
                Chat c = ChatSystem.getInstance().getChats().get(choice);
                if(! ChatSystem.getInstance().isUserInChat(user ,c )){
                    System.out.println("You can only see messages you are participate");
                    return;
                }
                ChatSystem.getInstance().showChat(c);
                return;
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a valid number!");
            }
        }
    }
}
