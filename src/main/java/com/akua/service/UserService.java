package com.akua.service;

import twitter4j.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService extends TwitterService{

    private final String username;

    public UserService(String username){
        this.username = username;
    }

    public User getUser() throws TwitterException {
        return twitter.showUser(username);
    }

    public Map<String, User> getFollowingUsers() throws TwitterException {
        Map<String, User> followingMap = new HashMap<>();
        IDs ids = twitter.getFriendsIDs(-1);

        do {
            for ( long id : ids.getIDs())
                followingMap.put(twitter.showUser(id).getName(), twitter.showUser(id));
        }while (ids.hasNext());

        return followingMap;
    }

    public Map<String, User> getFollowersMap() throws TwitterException {
        Map<String, User> hayranlarimMap = new HashMap<>();
        long cursor = -1L;

        while(cursor != 0){
            PagableResponseList<User> followersList = twitter.getFollowersList(username, cursor);

            followersList.listIterator().forEachRemaining( i -> {
                hayranlarimMap.put(i.getName(), i);
            });

            cursor = followersList.getNextCursor();
        }

        return hayranlarimMap;
    }

    public List<Status> getAllUserTweets() throws TwitterException {
        int pageNumber = 1;

        Paging page = new Paging (pageNumber, 100);
        List<Status> myStatuses = new ArrayList<>();

        do{
            ResponseList<Status> userTimeline  = twitter.getUserTimeline(username, page);
            myStatuses.addAll(userTimeline);

            if (userTimeline.size() == 0)
                break;
            page.setPage(pageNumber++);

        } while(true);

        return myStatuses;
    }

    public void exportTweetsToCSV(String folderPath) throws IOException, TwitterException {
        FileWriter writer = new FileWriter(folderPath + "/my-tweet-archive.csv");

        List<Status> myTweets = getAllUserTweets();

        for (Status s : myTweets){
            writer.write(s.getText().replaceAll("\n"," "));
            writer.write(";");
            writer.write("\n");
        }

        writer.close();
    }
}
