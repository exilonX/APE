package com.example.ape.volley.request;

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
	public static String GET_FEED 		= "http://apeserver.herokuapp.com/api/feed?pageSize=7&page=1";
	public static String BASE_URL 		= "http://apeserver.herokuapp.com";
	public static String GET_COMMENTS 	= "http://apeserver.herokuapp.com/api/reply_comments/";
	
	
}
