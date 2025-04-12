package main;


import java.util.*;

public final class ChatSystem {

    private Integer numOfUsers = 0;

    private HashMap<Integer, User>  users    = new HashMap<>();          // store all users
    private ArrayList<Chat>         chats    = new ArrayList<>();        // store all chats
    private static final ChatSystem instance = new ChatSystem();
    // instance created when loading class


    private ChatSystem(){ }

    public static ChatSystem getInstance() {
        return instance;
    }

    private boolean validateEmail(String email) {
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
        // user id is [numOfUsers]
        users.put(
                ++numOfUsers,
                new User(numOfUsers, username, email, password)
        );

    }

    public User searchUser(int id){
        return users.get(id).getUser();
    }

    // probably unnecessary
    public boolean validateUserId(String s_id){
        try {
             Integer.parseInt(s_id);
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
            users.remove(u_id);
            return true;
        }
        return false;
    }

    public void listAllChats(){
        int i = 1;
        for (Chat chat : chats){
            System.out.println( i + ". Chat [" + chat.showParticipants() + "]" );

        }

    }

    public void listAllUsers(){
        users.forEach((id , name) -> {
            System.out.println("Username: " + name.getName() +
                                "Id: [" + id + "]");
        });
    }

    public void listAllUsers(User user){
        users.forEach((id , name) -> {
            if(! id.equals(user.getId())){
                System.out.println("Username: " + name.getName() +
                "   Id: [" + id + "]");
            }
        });
    }

    public boolean initChat(ArrayList<Integer> ids, User user){
        ArrayList<Integer> valid_ids = new ArrayList<>();
        valid_ids.add(user.getId());

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

    public HashMap<Integer, User> getUsers() {
        return this.users;
    }

    public ArrayList<Chat> getChats() {
        return this.chats;
    }

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

    // SU validation
    public boolean authenticateSuperUser(String username, String password) {
        String su_username = "admin";
        String su_password = "admin";
        return Objects.equals(username, su_username)
                && Objects.equals(password, su_password);

    }

    public void showChat(Chat c){
        // todo: only show messages from current participants of the chat
        System.out.println(c.toString());
    }
}
