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
     * You client id, you have it from the google console when you register your project
     * https://console.developers.google.com/a
     */
    public static final String CLIENT_ID = "335158165520-dhivh6uniatvd0r14rnrkgpc2igg505s.apps.googleusercontent.com";
    /**
     * The redirect uri you have define in your google console for your project
     */
    public static final String REDIRECT_URI = "com.habbat.bookable:oauth2redirect";
    /**
     * The redirect root uri you have define in your google console for your project
     * It is also the scheme your Main Activity will react
     */
    public static final String REDIRECT_URI_ROOT = "com.habbat.bookable";

    public static final String KEY = "AIzaSyAxsSeJtwoHPy1vRaZaIvKWYMT5SJvY7eE";
    /**
     * You are asking to use a code when autorizing
     */
    public static final String CODE = "code";
    /**
     * You are receiving an error when autorizing, it's embedded in this field
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
     * The scope: what do we want to use
     * Here we want to be able to do anything on the user's GDrive
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
