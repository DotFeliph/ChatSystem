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
    // Instância criada ao carregar a classe


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
        return users.values().stream().noneMatch(user -> user.getEmail().equals(email));
    }

    public boolean validateUsername(String username){
        return username != null && !username.isEmpty();
    }

    public boolean validatePassword(String password){return (password.length() < 7);}

    public void registerNewUser(String username, String email, String password){
        // user id is [numOfUsers + 1]
        users.put(
                numOfUsers++,
                new User(numOfUsers, username, email, password)
        );

    }

    public boolean validateUserId(String s_id){
        int id;
        try {
            id = Integer.parseInt(s_id);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

    public boolean deleteUser(){
        int id = validadeId();

        if(getUsers().containsKey(id)){
            getUsers().remove(id);
            // remove use from all chats he was in
            // (do i remove all the messages as well?)
            return true;
        }
        return false;
    }

    public void initChat(){
        int nUsers = 0;
        System.out.println("---New chat---");
        System.out.println("To");
        while(nUsers < 2) {


        }

        System.out.println("");
        // ask for at least 2 users id, or users
        // write user name and id OR list all users
        // listing all users
        int j = 0;

        for(Integer i : users.keySet()){
            j++;
            System.out.print("Name: "+users.get(i).getName()+"["+j+"]");
            System.out.println("Id: "+users.get(i).getId());
        }
        chats.add(new Chat());
    }


    public HashMap<Integer, User> getUsers() {
        return this.users;
    }

    public ArrayList<Chat> getChats() {
        return this.chats;
    }

}
