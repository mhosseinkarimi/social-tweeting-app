package util;

import java.util.ArrayList;

public interface TweetIO {

    public Tweet readTweet(String tweetsDir, String tweetID);
    public void writeTweet(String tweetsDir, Tweet tweet);
    /**
     * Searches through the tweets' usernames
     *
     * @param username The username of the publisher of the tweet
     * @return An ArrayList of the matching Tweets
     */
    public ArrayList<Tweet> searchOnUser(String username);


    /**
     * Searches through the Tweets using their ID
     *
     * @param tweetID the wanted tweetID
     * @return The matching tweet if exists and null if it doesn't exist
     */
    public Tweet searchOnID(String tweetID);

}
