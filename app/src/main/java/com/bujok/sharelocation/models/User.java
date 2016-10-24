package com.bujok.sharelocation.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String fcmToken;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, String fcmToken) {
        this.username = username;
        this.email = email;
        this.fcmToken = fcmToken;
    }

}
// [END blog_user_class]
