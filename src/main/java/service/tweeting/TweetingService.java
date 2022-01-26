package service.tweeting;

import service.util.Tweet;
import service.util.TweetIO;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Tweeting Service handles the General tweeting and sharing services such as:
 * - Posting a tweet
 * - Liking a tweet
 * - Retweeting
 * - Replying
 */
public class TweetingService implements TweetIO {
    // fields
    String tweetsDir;

    /**
     * Constructor of TweetingService class
     */
    public TweetingService() {
        tweetsDir = "../../../files/tweets";
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
        writeTweet(tweetsDir, new Tweet(ownerUsername, tweetText, null, LocalDateTime.now()));
    }

    /**
     * Deletes an existing tweet tree (Deletes all retweets and replies)
     *
     * @param tweetID ID of deleted tweet
     */
    public void deleteTweet(String tweetID) {
        if (tweetExists(tweetID)) {
            File tweetFile = new File(tweetsDir, tweetID+".bin");
            boolean deleteResult = tweetFile.delete();// Deleting the tweet

            // Deleting the child tweets
            if (deleteResult) {
                ArrayList<String> childTweets = searchOnChildTweets(tweetID);
                if (childTweets.size() != 0) {
                    for (String childTweetID : childTweets) {
                        deleteTweet(childTweetID);
                    }
                }
            }
        }
    }

    /**
     * Searches on child tweets (replies)
     *
     * @param tweetID Parent tweet's ID
     * @return ArrayList child tweet's IDs
     */
    public ArrayList<String> searchOnChildTweets(String tweetID) {
        ArrayList<String> searchResults = new ArrayList<>();
        File[] tweetsList = new File(tweetsDir).listFiles();
        if (tweetsList != null) {
            for (File tweet : tweetsList) {
                String tweetName = tweet.getName();
                Tweet readTweet = readTweet(tweetsDir, tweetName);
                if (readTweet.getParentTweetID() != null && readTweet.getParentTweetID().equals(tweetID)) {
                    searchResults.add(readTweet.getTweetID());
                }
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
        File[] tweetsList = new File(tweetsDir).listFiles();
        if (tweetsList != null) {
            for (File tweet : tweetsList) {
                String tweetName = tweet.getName();
                Tweet readTweet = readTweet(tweetsDir, tweetName);
                if (readTweet.getLikes().contains(username)) {
                    likedTweetsID.add(readTweet.getTweetID());
                }
            }
        }

        return likedTweetsID;
    }

    /**
     * Searches through the retweets of a certain username
     *
     * @param username The username of the account
     * @return ArrayList of the tweetIDs of retweeted tweets
     */
    public ArrayList<String> searchOnRetweets(String username) {
        ArrayList<String> retweetedTweetsID = new ArrayList<>();
        File[] tweetsList = new File(tweetsDir).listFiles();
        if (tweetsList != null) {
            for (File tweet : tweetsList) {
                String tweetName = tweet.getName();
                Tweet readTweet = readTweet(tweetsDir, tweetName);
                if (readTweet.getRetweets().contains(username)) {
                    retweetedTweetsID.add(readTweet.getTweetID());
                }
            }
        }

        return retweetedTweetsID;
    }

    /**
     * Likes a tweet
     *
     * @param tweetID       The liked tweet's ID
     * @param likerUsername The liker's username
     */
    public void likeTweet(String tweetID, String likerUsername) {
        Tweet tweet = searchOnID(tweetID);
        if (tweet != null) {
            tweet.addLike(likerUsername);
        }
    }

    /**
     * Retract a given like
     *
     * @param tweetID       Liked tweet's ID
     * @param likerUsername Username of the liker account
     */
    public void retractLike(String tweetID, String likerUsername) {
        Tweet tweet = searchOnID(tweetID);
        if (tweet != null) {
            tweet.removeLike(likerUsername);
        }
    }

    /**
     * Edits a tweet's content
     *
     * @param tweetID      The edited tweet's ID
     * @param newTweetText New tweet content
     */
    public void editTweet(String tweetID, String newTweetText) {
        Tweet tweet = searchOnID(tweetID);
        if (tweet != null) {
            tweet.editText(newTweetText);
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
            Tweet tweet = searchOnID(tweetID);
            tweet.addRetweet(retweeterUsername);
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
        Tweet replyTweet = new Tweet(replierUsername, replyText, tweetID, LocalDateTime.now());
    }

    /**
     * Implements readTweet from TweetIO
     *
     * @param tweetsDir tweets directory path (Should be on [Project Location Path]/Social\ Network/files/tweets
     * @param tweetID   the tweet's ID
     * @return The matching tweet
     */
    @Override
    public Tweet readTweet(String tweetsDir, String tweetID) {
        Tweet tweet;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(tweetsDir, tweetID+".bin")))) {
            tweet = (Tweet) ois.readObject();
            return tweet;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes a tweet in the tweets directory
     *
     * @param tweetsDir tweets directory path (Should be on [Project Location Path]/Social\ Network/files/tweets
     * @param tweet   The new tweet
     */
    @Override
    public void writeTweet(String tweetsDir, Tweet tweet) {
        String tweetName = tweet.getTweetID();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(tweetsDir, tweetName+".bin")))) {
            oos.writeObject(tweet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches on the usernames published tweets
     *
     * @param username The username of the publisher of the tweet
     * @return ArrayList of published tweets
     */
    @Override
    public ArrayList<Tweet> searchOnUser(String username) {
        ArrayList<Tweet> searchResults = new ArrayList<>();
        File[] tweetsList = new File(tweetsDir).listFiles();
        if (tweetsList != null) {
            for (File tweet : tweetsList) {
                String tweetName = tweet.getName();
                if (tweetName.startsWith(username)) {
                    searchResults.add(readTweet(tweetsDir, tweetName));
                }
            }
            return searchResults;
        }
        return null;
    }

    /**
     * Searches tweets using the ID of the tweet
     *
     * @param tweetID the wanted tweetID
     * @return The matching tweet
     */
    @Override
    public Tweet searchOnID(String tweetID) {
        return readTweet(tweetsDir, tweetID);
    }
}
