package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Chat {

    private ArrayList<User>    participants;
    private ArrayList<Message> messages;

    public Chat(ArrayList<Integer> ids){
        participants = new ArrayList<>();
        messages = new ArrayList<>();
        for(Integer id : ids){
            participants.add(ChatSystem.getInstance().searchUser(id));

        }
    }

    public Chat getChat(){return this;}

    public void removeUser(User u){
        participants.remove(u);
    }

    public boolean isUserInChat(int id){
        // or // User u = ChatSystem.getInstance().getUsers().get(id).getUser();

        User u = ChatSystem.getInstance().searchUser(id);
        return participants.contains(u);
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public String listParticipants(){
        participants.
    }

    @Override
    public String toString() {

        return participants.stream()
                .map(User::getName)
                .collect(Collectors.joining(", "));

    }
}
