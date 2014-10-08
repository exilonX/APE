package com.example.ape.helper;

public class CommentInfo {
	public String username;
	public String comment;
	public String date;
	
	public CommentInfo() {
		this.username = new String();
		this.comment = new String();
		this.date = new String();
	}
	
	public CommentInfo(String usr, String com, String time) {
		this.username = usr;
		this.comment = com;
		this.date = time;
	}
	
}
