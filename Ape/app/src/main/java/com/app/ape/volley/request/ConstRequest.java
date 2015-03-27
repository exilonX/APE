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

//    public static String BASE_URL 		= "http://52.11.44.10:8080";
    public static String BASE_URL       = "http://192.168.0.105:8080";
    public static String GET_FEED 		= BASE_URL + "/api/feed?";
    public static String GET_COMMENTS 	= BASE_URL + "/api/reply/comments/";
    public static String POST_LOGIN_URL	= BASE_URL + "/api/login";
    public static String POST_COMM_ADD	= BASE_URL + "/api/reply/comment/add";
    public static String PUT_LIKE_ADD   = BASE_URL + "/api/reply/like/add";
    public static String POST_REPLY     = BASE_URL + "/api/challenge_reply";


    public static String getFeedLink(int pageSize, int pageNumber) {
        return GET_FEED + "pageSize=" + pageSize + "&page=" + pageNumber;
    }
}
