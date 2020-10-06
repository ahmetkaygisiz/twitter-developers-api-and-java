import configuration.TwitterConfiguration;
import service.TwitterService;
import service.UserService;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        UserService userService = new UserService("kaaygisiz");

        try{

//            User user = userService.getUser("kaaygisiz");
//            System.out.println(user.toString());

//             tweeet at.
//             userService.createTweet("Hello from the twitter4j side.");

//             dm gönder
//            userService.DeMedenYuru("ctznfthwrld","Hey there! I am using twitter developer api!");

//            Map<String, User> followingMap = userService.getFollowingUsers();
//            System.out.println("followingMap.size() = " + followingMap.size());

//            Map<String, User> hayranlarim = userService.getFollowersMap();
//            System.out.println("hayranlarim = " + hayranlarim.size());

//              List<Status> statuses = userService.getAllYourTweets();
//              statuses.size();
//              for (Status s : statuses)
//                  System.out.println("s = " + s);

            userService.exportTweetsToCSV(System.getProperty("user.dir"));
        }catch (TwitterException | IOException te){
            te.getCause();
        }
    }
}
