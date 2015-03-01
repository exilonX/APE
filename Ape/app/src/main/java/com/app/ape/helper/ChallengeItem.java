package com.app.ape.helper;

/**
 * Created by root on 01.03.2015.
 */
public class ChallengeItem {

    private String _id;
    private String username;
    private String date;
    private String title;
    private String thumb_url;

    public ChallengeItem() {
        set_id("");
        setUsername("");
        setTimestamp("");
        setTitle("");
        setThumb_image("");
    }

    public ChallengeItem(String _id, String username, String title,
                    String timestamp, String thumb_image) {
        set_id(_id);
        setUsername(username);
        setThumb_image(thumb_image);
        setTitle(title);
        setTimestamp(timestamp);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return date;
    }

    public void setTimestamp(String timestamp) {
        this.date = timestamp;
    }

    public String getThumb_image() {
        return thumb_url;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_url = thumb_image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


}
