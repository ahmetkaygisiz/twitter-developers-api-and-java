package com.akua.service;

import twitter4j.*;

/**
 * Api Account Services
 * 
 */
public class AccountService extends TwitterService {

    private String accountScreenName;
    private long accountId;

    public AccountService() throws TwitterException {
        accountScreenName = twitter.getScreenName();
        accountId = twitter.getId();
    }

    public String getAccountDisplayName() throws TwitterException {
        return accountScreenName;
    }

    public User getUser() throws TwitterException {
        return twitter.showUser(accountScreenName);
    }

    public String createTweet(String tweet) throws TwitterException {
        Status status = twitter.updateStatus(tweet);

        return status.getText();
    }

    public String demedenYuru(String recepient, String msg) throws TwitterException {
        DirectMessage dm =  twitter.sendDirectMessage(recepient, msg);

        return dm.getText();
    }

    public Status getLastTweet() throws TwitterException {
        return getUser().getStatus();
    }

    public void removeLastTweet() throws TwitterException {
        twitter.destroyStatus(getLastTweet().getId());
    }

    public PagableResponseList<User> getFollowersList() throws TwitterException {
        return twitter.getFollowersList(accountScreenName, accountId);
    }

    public ResponseList<Status> getFavList() throws TwitterException {
        return twitter.favorites().getFavorites();
    }

    public ResponseList<DirectMessage> getDMList() throws TwitterException {
        return  twitter.getDirectMessages();
    }
}
