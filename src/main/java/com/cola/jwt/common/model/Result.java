package com.cola.jwt.common.model;

import lombok.Data;

@Data
public class Result<T> {
    // result status
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int LOGIN_ERROR = 401;
    //system error
    public static final int ERROR_SYSTEM = 90;
    // status
    private int status;
    // detail information
    private String msg;
    // data
    private T data;

    public Result() {
    }

    private Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private Result(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(SUCCESS, msg);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(SUCCESS, msg, data);
    }

    public static <T> Result<T> success(int status, String msg, T data) {
        return new Result<>(status, msg, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(ERROR, msg);
    }

    public static <T> Result<T> error(String msg, T data) {
        return new Result<>(ERROR, msg, data);
    }

    public static <T> Result<T> error(int status, String msg, T data) {
        return new Result<>(status, msg, data);
    }
}