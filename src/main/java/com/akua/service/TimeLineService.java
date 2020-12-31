package com.akua.service;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

public class TimeLineService extends TwitterService{

    public List<String> getTimeLine() throws TwitterException {
        List<String> timeline = twitter.getHomeTimeline().stream().map(i -> {
            String tweet = "@" +  i.getUser().getName() + " : " + i.getText() + " " ;
            return tweet;
        }).collect(Collectors.toList());

        return timeline;
    }
    public void printTimeLine(int page, int pageSize) throws TwitterException {
        ResponseList<Status> statusList = twitter.getHomeTimeline(new Paging(page,pageSize));

        for (Status status: statusList){
            long userId = status.getUser().getId();
            String userName = status.getUser().getName();
            String tweetText = status.getText();

            System.out.println(userId+" @"+userName+" :"+tweetText);
        }
    }


}
