package com.example.grask;

import java.util.ArrayList;

public class PostClass {
    String post, userName, empty;
    ArrayList<String> arrayList;

    public PostClass(String post, String userName, String empty, ArrayList<String> arrayList) {
        this.post = post;
        this.userName = userName;
        this.empty = empty;
        this.arrayList = arrayList;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmpty() {
        return empty;
    }

    public void setEmpty(String empty) {
        this.empty = empty;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}
