package com.example.ape.utilsFeed;

public class CommentInfo {
	public String username;
	public String comment;
	public String timestamp;
	
	public CommentInfo() {
		this.username = new String();
		this.comment = new String();
		this.timestamp = new String();
	}
	
	public CommentInfo(String usr, String com, String time) {
		this.username = usr;
		this.comment = com;
		this.timestamp = time;
	}
	
}
