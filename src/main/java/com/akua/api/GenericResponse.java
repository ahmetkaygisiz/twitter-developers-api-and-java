package com.akua.api;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericResponse<T> {

    private String message;
    private T data;

    public GenericResponse(String message) {
        this.message = message;
    }

    public GenericResponse(T data) {
        this.data = data;
    }

    public GenericResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
