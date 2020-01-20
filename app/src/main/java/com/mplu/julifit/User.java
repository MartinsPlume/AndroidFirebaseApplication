package com.mplu.julifit;

public class User {

    private String email,name,home;
    private boolean admin = false;

    public User(String email, String name, String home, boolean admin){
        this.email=email;
        this.name=name;
        this.home=home;
        this.admin=admin;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getHome() {
        return home;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
