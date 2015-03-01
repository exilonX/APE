package com.app.ape.volley.request;

/**
 * A set of constant values used in the communication with the web service
 * TAGS for the requests used to identify the requests in the request queue
 * Service link the url on which requests for data are made
 * @author MercaIonel
 *
 */
public class ConstRequest {
	// TAGS
	public static String TAG_JSON_ARRAY 	= "json_array_req";
	public static String TAG_JSON_OBJECT	= "json_obj_req";
	
	// Service links
	// If running under emulator then localhost == 10.0.2.2
	public static String GET_FEED 		= "http://apeserver.herokuapp.com/api/feed?";
	public static String BASE_URL 		= "http://apeserver.herokuapp.com";
	public static String GET_COMMENTS 	= "http://apeserver.herokuapp.com/api/reply/comments/";
	public static String POST_LOGIN_URL	= "http://apeserver.herokuapp.com/api/login";
	public static String POST_COMM_ADD	= "http://apeserver.herokuapp.com/api/reply/comment/add";
    public static String PUT_LIKE_ADD   = "http://apeserver.herokuapp.com/api/reply/like/add";
	
	public static String getFeedLink(int pageSize, int pageNumber) {
		return GET_FEED + "pageSize=" + pageSize + "&page=" + pageNumber;
	}
	
}
