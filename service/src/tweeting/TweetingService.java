package tweeting;

import util.Tweet;
import util.TweetIO;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Tweeting Service handles the General tweeting and sharing services such as:
 * - Posting a tweet
 * - Liking a tweet
 * - Retweeting
 * - Replying
 */
public class TweetingService extends TweetIO {
    /**
     * Constructor of Tweeting Service
     */
    public TweetingService() {
        super();
    }

    /**
     * Checks the existence of a tweet in the global tweet collection
     *
     * @param tweetID ID of the tweet
     * @return Boolean value, true if the tweet exists
     */
    public boolean tweetExists(String tweetID) {
        return searchOnID(tweetID) != null;
    }

    /**
     * Creates a new tweet without any parents. Should be used in case of first-hand tweets not replies.
     *
     * @param ownerUsername Username of the publisher of the tweet
     * @param tweetText     Content of the tweet
     */
    public void createTweet(String ownerUsername, String tweetText) {
        addTweet(new Tweet(ownerUsername, tweetText, null, LocalDateTime.now()));
    }

    /**
     * Deletes an existing tweet tree (Deletes all retweets and replies)
     *
     * @param tweetID ID of deleted tweet
     */
    public void deleteTweet(String tweetID) {
        if (tweetExists(tweetID)) {
            removeTweet(searchOnID(tweetID));   // Deleting the tweet

            // Deleting the child tweets
            ArrayList<String> childTweets = searchOnChildTweets(tweetID);
            if (childTweets.size() != 0) {
                for (String childTweetID : childTweets) {
                    deleteTweet(childTweetID);
                }
            }

            // Removing retweets
            removeAllRetweets(tweetID);

            // Removing likes
            removeAllLikes(tweetID);
        }
    }

    /**
     * Likes a tweet
     *
     * @param tweetID       The liked tweet's ID
     * @param likerUsername The liker's username
     */
    public void likeTweet(String tweetID, String likerUsername) {
        if (tweetExists(tweetID)) {
            addLike(tweetID, likerUsername);
        }
    }

    /**
     * Edits a tweet's content
     *
     * @param tweetID      The edited tweet's ID
     * @param newTweetText New tweet content
     */
    public void editTweet(String tweetID, String newTweetText) {
        if (tweetExists(tweetID)) {
            Tweet targetTweet = searchOnID(tweetID);
            targetTweet.editText(newTweetText);
        }

    }

    /**
     * Shares a tweet with preserving the authority of the publisher of the tweet
     *
     * @param tweetID           Retweeted tweet's ID
     * @param retweeterUsername Username of account that retweets
     */
    public void retweet(String tweetID, String retweeterUsername) {
        if (tweetExists(tweetID)) {
            addRetweet(tweetID, retweeterUsername);
        } else {
            System.out.println("Sorry, the tweet seems to be missing!");
        }
    }

    /**
     * Replies a tweet by creating a new tweet
     *
     * @param tweetID         The replied tweet ID
     * @param replyText       Text of the reply
     * @param replierUsername Username of the replier
     */
    public void reply(String tweetID, String replyText, String replierUsername) {
        addReply(tweetID, replyText, replierUsername);
    }

}
