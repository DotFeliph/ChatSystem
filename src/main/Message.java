package main;

import java.util.Date;

public class Message {
    private       int     id = 0;                   // must be unique
    private final String  content;
    private final String  author;
    private       Date    timeSent;


    public void newId(int id) {
        this.id = id;
    }

    public Message(String author, String content){
        this.content = content;
        this.author = author;
    }

    public String toString() {
        return "[" + id + "] " + author + ": " + content;
    }


}
