package com.example.christine.uvpassenger.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Christine on 2/5/2018.
 */

public class LoginModel {
    @SerializedName("message")
    private String message;

    @SerializedName("email")
    private String email;

    @SerializedName("status")
    private String status;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("firstname")
    private String firstname;

    public LoginModel(String message, String email, String status, String lastname, String firstname) {
        this.message = message;
        this.email = email;
        this.status = status;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
