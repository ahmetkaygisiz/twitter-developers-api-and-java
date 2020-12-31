package com.akua.service;

import com.akua.api.JacksonService;
import com.akua.api.GenericResponse;
import com.akua.dto.UserDAO;
import twitter4j.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

public class UserService extends TwitterService{

    private JacksonService jacksonService;

    public UserService(){
        this.jacksonService = new JacksonService();
    }

    public User getUser(String username) throws TwitterException {
        return twitter.showUser(username);
    }

    public Map<String, User> getFollowingUsers(String username) throws TwitterException {
        Map<String, User> followingMap = new HashMap<>();
        IDs friendsIDs = twitter.getFriendsIDs(username, -1);

        do {
            for ( long id : friendsIDs.getIDs()){
                followingMap.put(
                        twitter.showUser(id).getName(),
                        twitter.showUser(id));
            }

        } while (friendsIDs.hasNext());

        return followingMap;
    }

    public Map<String, UserDAO> getFollowersMap(String username) throws TwitterException {
        Map<String, UserDAO> hayranlarimMap = new HashMap<>();
        long cursor = -1L;

        while(cursor != 0){
            PagableResponseList<User> followersList = twitter.getFollowersList(username, cursor);

            followersList.listIterator().forEachRemaining( i -> {
                hayranlarimMap.put(i.getScreenName(), new UserDAO(
                        i.getId(),
                        i.getName(),
                        i.getLocation(),
                        i.getDescription(),
                        i.getEmail(),
                        i.getFollowersCount(),
                        i.getFriendsCount()
                ));
            });

            cursor = followersList.getNextCursor();
        }

        return hayranlarimMap;
    }

    public List getUserFavs(String username) throws TwitterException {
        ResponseList<Status> favorites = twitter.getFavorites(username);
        List<String> favList = new ArrayList<>();
        favorites.forEach(i -> {
            favList.add(i.getText());
        });
        return favList;
    }

    public GenericResponse<List<Status>> getUserTweets(String username) throws TwitterException {
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

        return new GenericResponse<>(myStatuses);
    }

    public void exportTweetsToJson(String username, String folderPath) throws IOException, TwitterException {
        String outputPath = folderPath + "/" + username + "_" + new Date().toString() + ".json";

        Map<String, UserDAO> followersMap = getFollowersMap(username);

        jacksonService.objectToJsonFile(followersMap, outputPath);
    }
}
