package com.app.ape.helper;

import java.util.List;

public class ItemInfo {
	
	private String _id;
	private String username;
	private String title;
	private String date;
	private String thumb_url;
    //private List<LikeInfo> likes;
    private String noOfLikes;
    private String isMyLike;
	
	public ItemInfo() {
		set_id("");
		setUsername("");
		setTimestamp("");
		setTitle("");
		setThumb_image("");
        //setLikes(new ArrayList<LikeInfo>());
        setNoOfLikes("0");
        setMyLike("false");
	}
	
	public ItemInfo(String _id, String username, String title,
			String timestamp, String thumb_image, List<LikeInfo> likes, String noOfLikes, String isMyLike) {
		set_id(_id);
		setUsername(username);
		setThumb_image(thumb_image);
		setTitle(title);
		setTimestamp(timestamp);
       // setLikes(likes);
        setNoOfLikes(noOfLikes);
        setMyLike(isMyLike);
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

//    public List<LikeInfo> getLikes() {
//        return likes;
//    }

//    public void setLikes(List<LikeInfo> likes) {
//        this.likes = likes;
//    }

    public String getNoOfLikes() {
        return noOfLikes;
    }

    public void setNoOfLikes(String noOfLikes) {
        this.noOfLikes = noOfLikes;
    }

    public String isMyLike() {
        return isMyLike;
    }

    public void setMyLike(String isMyLike) {
        this.isMyLike = isMyLike;
    }

    @Override
    public String toString() {
        return this.title + "  " + this.thumb_url;
    }
}