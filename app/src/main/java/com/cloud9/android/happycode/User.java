package com.cloud9.android.happycode;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by paulvancappelle on 29-11-16.
 * <p>
 * User is a Singleton
 */
public class User {

    // Name, email address, and profile photo Url
    private static String sName;
    private static String sEmail;

    // The user's ID, unique to the Firebase project. Do NOT use this value to
    // authenticate with your backend server, if you have one. Use
    // FirebaseUser.getToken() instead.
    private static String sUid;

    private static User mUser;


    // constructor
    private User(FirebaseUser firebaseUser) {
        sName = firebaseUser.getDisplayName();
        sEmail = firebaseUser.getEmail();
        sUid = firebaseUser.getUid();
        setIsLoggedIn(true);
    }

    public static User get() {
        return mUser;
    }

    public static void set() {
        // if we have a loggedin user, set mUser
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            mUser = new User(firebaseUser);
        }
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
        mUser = null;
    }

    private void setIsLoggedIn(boolean isLoggedIn) {
        boolean sIsLoggedIn = isLoggedIn;
    }

    public String getName() {
        return sName;
    }

    public void setName(String name) {
        User.sName = name;
    }

    public String getEmail() {
        return sEmail;
    }

    public void setEmail(String email) {
        User.sEmail = email;
    }

    public String getUid() {
        return sUid;
    }

}
