package util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * This class represents a tweet as a building block of tweeting service.
 * Tweeting structures are connected via a hierarchical relation between a tweet and parent tweet.
 *
 * @author Mohammad Hossein Karimi
 * @version 1.1
 */
public class Tweet implements Serializable {
    //fields
    protected static final int tweetLength = 256;
    protected String ownerUsername;           // Username of the tweet publisher
    protected String tweetText;               // Content of the tweet
    protected final String parentTweetID;     // ID of the parent tweet (useful in hierarchical structures like retweets or replies
    protected final LocalDateTime tweetDate;  // Date of the publication of the tweet
    protected int likesNumber;                // Number of the likes
    protected final String tweetID;           // ID of the tweet
    protected int retweetNumber;              // Number of the retweets
    private ArrayList<String> retweets;  // A mapping from the retweeted tweet to the usernames of the retweeters
    private ArrayList<String> likes;     // A mapping from the tweet ID to the username of the likers


    /**
     * Constructor of Tweet class.
     *
     * @param ownerUsername Username of the tweet publisher
     * @param tweetText     Content of the tweet
     * @param parentTweetID ID of the parent tweet (useful in hierarchical structures like retweets or replies
     * @param tweetDate     Date of the publication of the tweet
     */
    public Tweet(String ownerUsername, String tweetText, String parentTweetID, LocalDateTime tweetDate) {
        this.ownerUsername = ownerUsername;
        this.parentTweetID = parentTweetID;
        this.tweetDate = tweetDate;
        likesNumber = 0;
        retweetNumber = 0;
        if (checkTweetLength(tweetText)) {
            this.tweetText = tweetText;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        tweetID = this.ownerUsername + '-' + this.tweetDate.format(dtf);
        retweets = new ArrayList<>();
        likes = new ArrayList<>();
    }

    /**
     * Checks if the tweet's length is in legal range
     *
     * @param tweetText The suggested tweet length
     * @return A boolean value. true if tweet passes the test
     */
    protected boolean checkTweetLength(@org.jetbrains.annotations.NotNull String tweetText) {
        return tweetText.length() <= tweetLength;
    }

    /**
     * Getter method for publisher's username.
     *
     * @return ownerUsername
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Used for updating the username of tweet publisher.
     *
     * @param ownerUsername New ownerUsername
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * Provides access to parent ID of the tweet
     *
     * @return parentTweetID
     */
    public String getParentTweetID() {
        return parentTweetID;
    }

    /**
     * Provides access to the Time of tweet publication
     *
     * @return tweetDate
     */
    public LocalDateTime getTweetDate() {
        return tweetDate;
    }

    /**
     * Provides  access to number of likes of the tweet
     *
     * @return likeNumbers
     */
    public int getLikesNumber() {
        return likesNumber;
    }

    /**
     * Updates the number of likes of the tweet
     *
     * @param change The change of number of likes. Can be positive or negative
     */
    public void changeLikeNumber(int change) {
        likesNumber += change;
    }

    /**
     * Updates the number of retweets of the tweet
     *
     * @param change The change of number of retweets. Can be positive or negative
     */
    public void changeRetweetNumber(int change) {
        retweetNumber += change;
    }

    /**
     * Edits the Content of the tweet
     *
     * @param newText The new tweet of the
     */
    public void editText(String newText) {
        if (checkTweetLength(newText)) {
            tweetText = newText;
        }
    }

    /**
     * Provides access to tweet's content
     *
     * @return tweetText
     */
    public String getTweetText() {
        return tweetText;
    }

    /**
     * Provides access to tweet's ID
     *
     * @return tweetID
     */
    public String getTweetID() {
        return tweetID;
    }

    /**
     * Provides access to number of retweets
     *
     * @return retweetNumber
     */
    public int getRetweetNumber() {
        return retweetNumber;
    }

    /**
     * Getter Method for likes
     *
     * @return ArrayList of usernames of likers
     */
    public ArrayList<String> getLikes() {
        return likes;
    }

    /**
     * Getter method for retweets
     *
     * @return ArrayList of username of the account that retweeted
     */
    public ArrayList<String> getRetweets() {
        return retweets;
    }

    /**
     * Adds the username of the accounts retweeting the tweet
     *
     * @param username Username of the account retweeted the tweet
     */
    public void addRetweet(String username) {
        retweets.add(username);
        changeRetweetNumber(1);
    }

    /**
     * Removes a retweet
     *
     * @param username Username of the account retweeted
     */
    public void removeRetweet(String username) {
        retweets.remove(username);
        changeRetweetNumber(-1);
    }

    /**
     * Adds a like on tweet
     *
     * @param username Username of the liker
     */
    public void addLike(String username) {
        likes.add(username);
        changeLikeNumber(1);
    }

    /**
     * Removes a like on tweet
     *
     * @param username Username of the liker
     */
    public void removeLike(String username) {
        likes.remove(username);
        changeLikeNumber(-1);
    }

    /**
     * Overrides toString method
     *
     * @return String representation of the Tweet
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "@" + ownerUsername + '\n' + tweetText + '\n' + tweetDate.format(dtf) + '\n' + "Retweets: " + Integer.toString(retweetNumber) + ", " + "Likes: " + Integer.toString(likesNumber);
    }
}
