package com.app.ape.helper;

import android.os.Bundle;

import com.app.ape.constants.Const;

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

    public CommentTag(Bundle args) {
        this._id 			= args != null ? args.getString(Const.KEY_ID) : null;
        this.position 		= args != null ? args.getInt(Const.KEY_POSITION) : null;
        this.imageUrl		= args != null ? args.getString(Const.KEY_THUMBNAIL) : null;
        this.username		= args != null ? args.getString(Const.KEY_USR) : null;
        this.date			= args != null ? args.getString(Const.KEY_DATE_COMM) : null;
        this.description	= args != null ? args.getString(Const.KEY_TITLE) : null;
    }
	
	@Override
	public String toString() {
		return "The id is: " + this._id + " the position is: " +
				this.position + " the username : " + this.username
                + " the imageUrl : " + this.imageUrl;
	}

    public Bundle toBundle() {
        Bundle args = new Bundle();
        args.putString(Const.KEY_ID, this._id);
        args.putInt(Const.KEY_POSITION, this.position);
        args.putString(Const.KEY_THUMBNAIL, this.imageUrl);
        args.putString(Const.KEY_USR, this.username);
        args.putString(Const.KEY_DATE_COMM, this.date);
        args.putString(Const.KEY_TITLE, this.description);

        return args;
    }


	
}
