package com.akua.service;

import twitter4j.*;

public class TwitterService {

    protected Twitter twitter;

    public TwitterService(){
        twitter = TwitterFactory.getSingleton();
    }

}
