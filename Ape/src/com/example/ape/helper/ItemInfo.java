package com.example.ape.helper;

public class ItemInfo {
	
	private String username;
	private String title;
	private String date;
	private String thumb_url;
	
	public ItemInfo() {
		setUsername("");
		setTimestamp("");
		setTitle("");
		setThumb_image("");
	}
	
	public ItemInfo(String username, String title,
			String timestamp, String thumb_image) {
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
}