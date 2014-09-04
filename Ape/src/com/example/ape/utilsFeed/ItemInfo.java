package com.example.ape.utilsFeed;

public class ItemInfo {
	
	private String username;
	private String title;
	private String timestamp;
	private String thumb_image;
	
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
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getThumb_image() {
		return thumb_image;
	}
	
	public void setThumb_image(String thumb_image) {
		this.thumb_image = thumb_image;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}