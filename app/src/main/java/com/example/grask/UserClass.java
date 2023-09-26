package com.example.grask;

import java.util.ArrayList;

public class UserClass {
    String phoneNumber, userName, userPassword;
    ArrayList<String> userPosts;

    public UserClass(String phoneNumber, String userName, String userPassword, ArrayList<String> userPosts) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userPosts = userPosts;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public ArrayList<String> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(ArrayList<String> userPosts) {
        this.userPosts = userPosts;
    }
}
