package com.akua;

import com.akua.api.RequestData;
import com.akua.api.JacksonService;
import com.akua.api.GenericResponse;
import com.akua.dto.UserDAO;
import com.akua.service.AccountService;
import com.akua.service.UserService;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) throws TwitterException {
        AccountService accountService = new AccountService();
        UserService userService = new UserService();
        JacksonService jacksonService = new JacksonService();

    	port(8080);
    	get("/", (req,res)-> "URL List");

    	/* Post Tweet */
        post("/tweets", (req, res) -> {
            res.type("application/json");

            RequestData requestData = jacksonService.jsonToObject(req.body(), RequestData.class);
            accountService.createTweet(requestData.getData());

            return jacksonService.objectToJson(new GenericResponse<>("Tweet sent!"));
        });

        /* Account Tweets & Favs */
        path("/account", () -> {
            get("/followers/:username", (req, res) -> {
                Map<String, UserDAO> followersMap = userService.getFollowersMap(req.params(":username"));
                return jacksonService.objectToJson(followersMap);
            });

            get("/following/:username", (req, res) -> {
               return "";
            });

            // THIS IS UGLYYYYYY
            get("/favorites/:username", (req, res) -> {
                res.type("application/json");
                List<String> userFavs = userService.getUserFavs(req.params(":username"));
                return jacksonService.objectToJson(userFavs);
            });

            // THIS IS UGLYYYYYY
            get("/tweets/:username", (req, res) -> {
                GenericResponse<List<Status>> userTweets = userService.getUserTweets(req.params(":username"));
                return jacksonService.objectToJson(userTweets);
            });

        });

        /* DM's */

        path("/direct-messages", () -> {
            // dm - get from
            get("/:username", (req, res) -> "");

            // dm - send to user
            post("/:username", (req, res) -> "");

            // dm - get all
            get("/", (req, res) -> "");
        });


        /* ARCHIVE */

        path("/archive", () -> {
            // get - favorites
            get("/favorites/:username", (req, res) -> {
                return "";
            });

            // get - tweets
            get("/tweets/:username", (req, res) -> {
                return "";
            });

            // get - followers
            get("/followers/:username", (req, res) -> {
                res.type("application/json");

                userService.exportTweetsToJson(req.params(":username"), "/data");

                return jacksonService.objectToJson(new GenericResponse<>("Created!"));
            });
        });
    }
}

