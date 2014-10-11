package com.example.ape.helper;

public class CommentTag {
	public String 	_id;
	public int 		position;
	public String 	imageUrl;
	
	public CommentTag(String id, int pos, String imageUrl){
		this._id 		= id;
		this.position 	= pos;
		this.imageUrl 	= imageUrl;
	}
	
	@Override
	public String toString() {
		return "The id is: " + this._id + " the position is: " +
				this.position;
	}
	
}
