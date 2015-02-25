package com.app.ape.helper;

public class CommentTag {
	public String 	_id;
	public int 		position;
	public String 	imageUrl;
	public String	username;
	public String	date;
	public String 	description;
	
	public CommentTag(String id, int pos, String imageUrl){
		this._id 			= id;
		this.position 		= pos;
		this.imageUrl 		= imageUrl;
		this.username		= null;
		this.date			= null;
		this.description	= null;
	}
	
	public CommentTag(String id, int pos, String imageUrl,
			String username, String date, String description) {
		this._id 			= id;
		this.position 		= pos;
		this.imageUrl		= imageUrl;
		this.username		= username;
		this.date			= date;
		this.description	= description;
	}
	
	@Override
	public String toString() {
		return "The id is: " + this._id + " the position is: " +
				this.position;
	}
	
}
