package com.project.cookbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSession {
    // SharedPreferences Reference
    SharedPreferences pref;

    // Editor reference for Shared Preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shred pref mode
    int PRIVATE_MODE = 0;

    //Shared Pref file name
    public static final String PREFER_NAME = "Reg";

    // All Shared Pref Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // Make variable public to access from outside
    public static final String KEY_NAME = "Name";
    public static final String KEY_EMAIL = "Email";

    // Password
    public static final String KEY_PASSWORD = "Password";

    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // Create Login Session
    public void createUserLoginSession(String uName, String uPassword) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in preferences
        editor.putString(KEY_NAME, uName);

        // Storing password in preferences
        editor.putString(KEY_PASSWORD, uPassword);

        // commit changes
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        // Use hashmap tp store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        return user;
    }

    public void logOutUser() {
        // Clear all user data from Shared Preferences
        //editor.clear();
        //editor.commit();
        editor.putBoolean(IS_USER_LOGIN, false);
        editor.commit();

        // After logout redirect user to Login Page
        Intent i = new Intent(_context, Login.class);

        // Closing All Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }
    // Check for Login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
















