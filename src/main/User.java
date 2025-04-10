package main;

public class User {
    private int    id;               // must be unique
    private String name;
    private String email;
    private String password;       // more than 6 char

    public User(int id, String name, String email, String password) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public User getUser(){return this;}

    protected User newUser(int id, String name, String email, String password){
        return new User(id, name, email, password);
    }

}
