package com.example.parkingintelligent.response;

public class UserData {
    public String status;
    public String message;
    public UserModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }
}

class UserModel{
    public int id;
    public String username;
    public String phone;
    public String email;
    public String avatar;
    public String account;
    public boolean frozed;
}
