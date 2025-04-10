package main;

import jdk.jshell.Snippet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChatSystem {

    private Integer numOfUsers = Integer.valueOf(0);

    private HashMap<Integer, User>  users    = new HashMap<>();          // store all users
    private ArrayList<Chat>         chats    = new ArrayList<>();        // store all chats
    private static final ChatSystem instance = new ChatSystem();
    // instance created when loading class


    private ChatSystem(){ }

    public static ChatSystem getInstance() {
        return instance;
    }

    public boolean validateEmail(String email) {
        return validateIfEmailExist(email) && validateEmailFormat(email);
    }

    public boolean validateEmailFormat(String email){return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$");}

    public boolean validateIfEmailExist(String email) {
        // iterate for each User, and returns true if none email matches the new email
        return users.values().stream().noneMatch(user ->
                user.getEmail().equals(email));
    }

    public boolean validateUsername(String username){
        return username != null && !username.isEmpty();
    }

    public boolean isPasswordValid(String password){return (password.length() > 6);}

    public void registerNewUser(String username, String email, String password){
        // user id is [numOfUsers + 1]
        users.put(
                numOfUsers++,
                new User(numOfUsers, username, email, password)
        );

    }

    public User searchUser(int id){
        return users.get(id).getUser();
    }

    // probably unnecessary
    public boolean validateUserId(String s_id){
        int id;
        try {
            id = Integer.parseInt(s_id);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    public boolean deleteUser(String s_id){
        int u_id = Integer.parseInt(s_id);

        if(users.containsKey(u_id)){
            // iterate for every chat
            chats.forEach(chat -> {
                // if it finds the user
                if(chat.isUserInChat(u_id)){
                    // deletes it
                    chat.removeUser(searchUser(u_id));
                }
            });
            return true;
        }
        return false;
    }

    public void listAllChats(){
        int i = 1;
        for (Chat chat : chats){
            System.out.println( i + ". Chat [" + chat.toString() + "]" );

        }

    }

    public void listAllUsers(){
        users.forEach((id , name) -> {
            System.out.println("Username: " + name.getName() +
                                "Id: [" + id + "]");
        });
    }

    public boolean initChat(ArrayList<Integer> ids){
        ArrayList<Integer> valid_ids = new ArrayList<>();

        for(Integer i_ids : ids){
            if(! users.containsKey(i_ids)){
                return false;
            }
            else{
                valid_ids.add(i_ids);
            }
        }
        chats.add(new Chat(valid_ids));
        return true;
    }

    public void userLogin(){

    }

    public HashMap<Integer, User> getUsers() {
        return this.users;
    }

    public ArrayList<Chat> getChats() {
        return this.chats;
    }

    //user validation

    public User authenticateUser(String email, String password) {
        for(User user : users.values()) {
            if(user.getEmail().equalsIgnoreCase(email) &&
                    user.validatePassword(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserInChat(User u, Chat c){
        return c.isUserInChat(u);
    }

    public void sendMessage(User u, Chat c, String content){
        c.createMessage(u, content);
    }
}
