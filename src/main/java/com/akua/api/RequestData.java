package com.akua.api;

import com.fasterxml.jackson.annotation.JsonAlias;

public class RequestData {
    @JsonAlias("data")
    private String data;

    public RequestData(){

    }

    public RequestData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
