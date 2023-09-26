package com.example.grask;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedCustomeClass {
    private static final String PREFERENCES_NAME = "MyAppPreferences";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    // Initialize SharedPreferences in the constructor
    private SharedCustomeClass(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Create a singleton instance of SharedPreferencesManager
    private static SharedCustomeClass instance;

    public static synchronized SharedCustomeClass getInstance(Context context) {
        if (instance == null) {
            instance = new SharedCustomeClass(context);
        }
        return instance;
    }

    // Store a string value in SharedPreferences
    public void setString(String key, String value) {
        editor.putString(key, value).apply();
    }

    // Retrieve a string value from SharedPreferences
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    // Store an integer value in SharedPreferences
    public void setInt(String key, int value) {
        editor.putInt(key, value).apply();
    }

    // Retrieve an integer value from SharedPreferences
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    // Store a boolean value in SharedPreferences
    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    // Retrieve a boolean value from SharedPreferences
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    // Remove a value from SharedPreferences
    public void remove(String key) {
        editor.remove(key).apply();
    }

    // Clear all data from SharedPreferences
    public void clear() {
        editor.clear().apply();
    }

}
