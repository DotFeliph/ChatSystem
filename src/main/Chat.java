package main;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Chat {

    int num_messages = 0;

    private ArrayList<User>    participants;
    private ArrayList<Message> messages;

    public Chat(ArrayList<Integer> ids){
        participants = new ArrayList<>();
        for(Integer id : ids){
            participants.add(ChatSystem.getInstance().searchUser(id));

        }

        messages = new ArrayList<>();
    }

    public void removeUser(User u){
        participants.remove(u);
        // todo: remove messages user sent

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

    public boolean isUserInChat(User u){
        return participants.contains(u);
    }

    public void createMessage(User u, String content){
        messages.add(new Message(u.getName(), content));

        // message id is [num_messages - 1]
        messages.getLast().newId(num_messages++);
    }

    public String showParticipants() {
        return participants.stream()
                .map(User::getName)
                .collect(Collectors.joining(", "));

    }

    public String toString() {
        if (messages.isEmpty()) {
            return "Chat is empty.";
        }

        return messages.stream()
                .map(Message::toString)
                .collect(Collectors.joining("\n"));
    }

}
