package util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * TweetIO class handles the interactions of tweets stored in the database and TweetingService.
 *
 * @author Mohammad Hossein Karimi
 * @version 1.1
 */
public class TweetIO {
    // fields
    private ArrayList<Tweet> tweets;                    // Published tweets (Temporary, tweets will be stored on a database)
    private HashMap<String, HashSet<String>> retweets;  // A mapping from the retweeted tweet to the usernames of the retweeters
    private HashMap<String, HashSet<String>> likes;     // A mapping from the tweet ID to the username of the likers

    /**
     * Constructor of TweetIO class
     */
    public TweetIO() {
        tweets = new ArrayList<>();
        retweets = new HashMap<>();
        likes = new HashMap<>();
    }

    /**
     * Adds a tweet to the tweets collection
     *
     * @param newTweet The new Tweet
     */
    public void addTweet(Tweet newTweet) {
        tweets.add(newTweet);
    }

    /**
     * Removes a tweet from the tweets collection
     *
     * @param deletedTweet The deleted Tweet
     */
    public void removeTweet(Tweet deletedTweet) {
        tweets.remove(deletedTweet);
        retweets.remove(deletedTweet.getTweetID());
    }

    /**
     * Returns the Hashset of the usernames of the retweeters.
     *
     * @param tweetID The retweeted tweet's ID
     * @return Hashset<String> username of retweeters
     */
    public HashSet<String> getRetweets(String tweetID) {
        return retweets.getOrDefault(tweetID, null);
    }

    /**
     * Returns a Hashset of the usernames associated with likes of a tweet.
     * @param tweetID The tweet's ID
     * @return  Hashset<String> of liksers' usernames.
     */
    public HashSet<String> getLikes(String tweetID){
        return likes.getOrDefault(tweetID, null);
    }
    /**
     * Searches through the tweets' usernames
     *
     * @param username The username of the publisher of the tweet
     * @return An ArrayList of the matching Tweets
     */
    public ArrayList<Tweet> searchOnUser(String username) {
        ArrayList<Tweet> searchResults = new ArrayList<Tweet>();
        for (Tweet tweet : tweets) {
            if (tweet.getOwnerUsername().equals(username)) {
                searchResults.add(tweet);
            }
        }

        return searchResults;
    }

    /**
     * Searches through the likes of a certain username
     *
     * @param username The username of the account
     * @return ArrayList of the tweetIDs of liked tweets
     */
    public ArrayList<String> searchOnLikes(String username) {
        ArrayList<String> likedTweetsID = new ArrayList<>();
        for (String tweetID : likes.keySet()) {
            if (likes.get(tweetID).contains(username)) {
                likedTweetsID.add(tweetID);
            }
        }

        return likedTweetsID;
    }

    public ArrayList<String> searchOnChildTweets(String tweetID) {
        ArrayList<String> childTweets = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getParentTweetID() != null && tweet.getParentTweetID().equals(tweetID)) {
                childTweets.add(tweet.getTweetID());
            }
        }

        return childTweets;
    }

    /**
     * Searches through the Tweets using their ID
     *
     * @param tweetID the wanted tweetID
     * @return The matching tweet if exists and null if it doesn't exist
     */
    public Tweet searchOnID(String tweetID) {
        for (Tweet tweet : tweets) {
            if (tweet.getTweetID().equals(tweetID)) {
                return tweet;
            }
        }

        return null;
    }

    /**
     * Adds a retweet relation between the tweet ID and username of the retweeting account
     *
     * @param tweetID           The retweeted tweet's ID
     * @param retweeterUsername The username of the account retweeting
     */
    public void addRetweet(String tweetID, String retweeterUsername) {
        if (searchOnID(tweetID).getRetweetNumber() == 0) {
            retweets.put(tweetID, new HashSet<>());
        }
        retweets.get(tweetID).add(retweeterUsername);
        searchOnID(tweetID).changeRetweetNumber(1);
    }

    /**
     * Removes a retweet from the stored collection of retweets if it exists.
     *
     * @param tweetID           The retweeted tweet's ID
     * @param retweeterUsername The username of the account retweeting
     */
    public void removeRetweet(String tweetID, String retweeterUsername) {
        if (retweets.containsKey(tweetID)) {
            retweets.get(tweetID).remove(retweeterUsername);
        }
    }

    /**
     * Removes all the retweets associated with a tweet
     *
     * @param tweetID The tweet's ID
     */
    public void removeAllRetweets(String tweetID) {
        retweets.remove(tweetID);
    }

    /**
     * Adds a like to a tweet
     *
     * @param tweetID       The liked tweet's ID
     * @param likerUsername Username of the liking account
     */
    public void addLike(String tweetID, String likerUsername) {
        Tweet likedTweet = searchOnID(tweetID);
        if (likedTweet.getLikesNumber() == 0) {
            likes.put(tweetID, new HashSet<>());
        }
        if (likes.containsKey(tweetID) && !likes.get(tweetID).contains(likerUsername)) {
            likes.get(tweetID).add(likerUsername);
            likedTweet.changeLikeNumber(1);
        }
    }

    /**
     * Removes a like from the given tweet
     *
     * @param tweetID       ID of the tweet
     * @param likerUsername Username of the liker
     */
    public void removeLike(String tweetID, String likerUsername) {
        Tweet likedTweet = searchOnID(tweetID);
        if (likes.containsKey(tweetID) && !likes.get(tweetID).contains(likerUsername)) {
            likes.get(tweetID).remove(likerUsername);
            likedTweet.changeLikeNumber(-1);
        }
    }

    /**
     * Removes all the likes associated with a tweet
     *
     * @param tweetID The tweet's ID
     */
    public void removeAllLikes(String tweetID) {
        likes.remove(tweetID);
    }

    /**
     * Adds a reply to a given tweet
     *
     * @param tweetID         Replied tweet ID
     * @param replyText       Content of the reply
     * @param replierUsername Username of the replier
     */
    public void addReply(String tweetID, String replyText, String replierUsername) {
        Tweet reply = new Tweet(replierUsername, replyText, tweetID, LocalDateTime.now());
        addTweet(reply);
    }

    /**
     * Returns the String representation of retweets
     * @param tweetID  retweeted tweet ID
     * @param username Username of the retweeter
     * @return String representation of retweet
     */
    public String retweetString(String tweetID, String username){
        return "@" + username + " retweeted " + searchOnID(tweetID);
    }

    /**
     * Prints the retweet
     *
     * @param tweetID retweeted tweet ID
     * @param username Username of the retweeter
     */
    public void printRetweet(String tweetID, String username) {
        if (retweets.containsKey(tweetID) && retweets.get(tweetID).contains(username)) {
            System.out.println(retweetString(tweetID, username));
            System.out.println("");
        }
    }

    /**
     * Prints all the tweets in the tweet collection. Don't use in implementation. Just for testing.
     */
    public void printAllTweets() {
        System.out.println("================Tweets=============");
        for (Tweet tweet : tweets) {
            System.out.println(tweet);
            System.out.println("");
        }
    }
}