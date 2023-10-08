package com.example.grask;

import java.util.Map;

public class CommentClass {
    String userName, userComment;
    int liked, disliked;

    @Override
    public String toString() {
        return "CommentClass{" +
                "userName='" + userName + '\'' +
                ", userComment='" + userComment + '\'' +
                ", liked=" + liked +
                ", disliked=" + disliked +
                '}';
    }

    public CommentClass(String userName, String userComment, int liked, int disliked) {
        this.userName = userName;
        this.userComment = userComment;
        this.liked = liked;
        this.disliked = disliked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public int getLiked() {
        return liked;
    }

    public void setLiked(int liked) {
        this.liked = liked;
    }

    public int getDisliked() {
        return disliked;
    }

    public void setDisliked(int disliked) {
        this.disliked = disliked;
    }


}
