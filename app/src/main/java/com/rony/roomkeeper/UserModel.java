package com.rony.roomkeeper;

public class UserModel {

    String name;
    String email;
    String password;

    String profile_picture;
    String num;

    public UserModel() {
    }

    public UserModel(String name, String email, String password,String num) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public  String getNum(){
        return num;
    }
    public void setNum(String num){
        this.num = num;
    }

    public UserModel(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}