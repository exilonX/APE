package com.app.ape.helper;

public class LikeInfo {
    public String username;
    public String date;

    public LikeInfo() {
        this.username = new String();
        this.date = new String();
    }

    public LikeInfo(String usr, String time) {
        this.username = usr;
        this.date = time;
    }

}
