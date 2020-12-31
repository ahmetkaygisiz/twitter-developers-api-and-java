package com.akua.dto;

public class UserDAO {
    private long id;
    private String screenName;
    private String location;
    private String description;
    private String email;
    private int followersCount;
    private int friendsCount; // following

    public UserDAO(long id, String screenName, String location, String description, String email, int followersCount, int friendsCount) {
        this.id = id;
        this.screenName = screenName;
        this.location = location;
        this.description = description;
        this.email = email;
        this.followersCount = followersCount;
        this.friendsCount = friendsCount;
    }
}
