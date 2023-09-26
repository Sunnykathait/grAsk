package com.example.grask;

public class SettingItemClass {
    private String text;
    private int imageResource;

    public SettingItemClass(String text, int imageResource) {
        this.text = text;
        this.imageResource = imageResource;
    }

    public String getText() {
        return text;
    }

    public int getImageResource() {
        return imageResource;
    }


}


