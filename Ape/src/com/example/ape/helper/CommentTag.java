package com.example.ape.helper;

public class CommentTag {
	public String 	_id;
	public int 		position;
	
	public CommentTag(String id, int pos){
		this._id 		= id;
		this.position 	= pos;
	}
	
	@Override
	public String toString() {
		return "The id is: " + this._id + " the position is: " +
				this.position;
	}
	
}
