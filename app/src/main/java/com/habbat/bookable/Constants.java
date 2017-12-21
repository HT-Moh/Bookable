package com.habbat.bookable;

/**
 * Created by hackolos on 18.12.17.
 */

public final  class Constants {
    private Constants() {
        // restrict instantiation
    }

    //API Calls
    public enum ApiCalls{
        OAUTH,
        //The volume collection, is a collection of every volume resource managed by Google Books.
        // As such, you cannot list all volume resources, but you can list all volumes that match a set of search terms.
        GETVOLUMES,
        GETYOURVOLUMES,
        //A bookshelf collection consists of all the bookshelf resources managed by Google Books.
        //Bookshelves must always be referenced in the context of a specific user's library. Bookshelves can contain zero or more volumes.
        GETBOOKSHELF,
    }

    /**
     * Client id from google console
     */
    public static final String CLIENT_ID = "335158165520-dhivh6uniatvd0r14rnrkgpc2igg505s.apps.googleusercontent.com";
    /**
     * Redirect url Defined on google consolde
     */
    public static final String REDIRECT_URI = "com.habbat.bookable:oauth2redirect";
    /**
     * the redirect root uri and main schema activity
     */
    public static final String REDIRECT_URI_ROOT = "com.habbat.bookable";

    /**
     * Default credetials from google console
     */

    public static final String KEY = "AIzaSyAxsSeJtwoHPy1vRaZaIvKWYMT5SJvY7eE";
    /**
     * Asking to use a code when autorizing
     */
    public static final String CODE = "code";
    /**
     * Error
     */
    public static final String ERROR_CODE = "error";
    /**
     * GrantType:You are using a code when retrieveing the token
     */
    public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    /**
     * GrantType:You are using a refresh_token when retrieveing the token
     */
    public static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    /**
     * The scope: Books API
     */
    public static final String API_SCOPE = "https://www.googleapis.com/auth/books";
    /**
     * The code returned by the server at the authorization's first step
     */
    public static String code;
    /**
     * The error returned by the server at the authorization's first step
     */
    public static String error;


}
