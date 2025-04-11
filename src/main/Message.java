package main;

import java.util.Date;

public class Message {
    private int     id = 0;                   // must be unique
    private String  content;
    private String  author;
    private Date    timeSent;

    public int getId() {
        return id;
    }

    public void newId(int id) {
        this.id = id;
    }

    public Message(String author, String content){
        this.content = content;
        this.author = author;
    }


}
