package com.akua;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
    	port(8080);
    	get("/", (req,res)-> "Turning to spark java app");
    }
}
