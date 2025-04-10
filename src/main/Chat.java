package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Chat {

    private ArrayList<User>    participants;
    private ArrayList<Message> messages;

    public Chat(){
        participants = new ArrayList<>();
        messages = new ArrayList<>();
    };

    public Chat getChat(){return this;}

    public boolean createChat(User... usr){

        for(User u : usr) {

        }
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

}
