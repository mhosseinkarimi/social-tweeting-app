package service.timeline;

import service.util.Account;
import service.util.AccountIO;
import service.util.Tweet;
import service.util.TweetIO;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class TimeLine implements TweetIO, AccountIO {
    //fields
    private final String tweetsDir;

    /**
     * Constructor of TimeLine
     */
    public TimeLine() {
        tweetsDir = "../../../files/users/";
    }

    /**
     * Gets the followers of an account
     *
     * @param username Username of the account
     * @return ArrayList of followers' usernames
     */
    @Override
    public ArrayList<String> getFollowers(String username) {
        Object account = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(tweetsDir + username + ".bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            account = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Account account1 = (Account) account;

        assert account1 != null;
        return account1.getFollowers();
    }

    /**
     * Gets the followings of an account
     *
     * @param username Username of the account
     * @return ArrayList of following accounts' usernames
     */
    @Override
    public ArrayList<String> getFollowings(String username) {
        Object account = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(tweetsDir + username + ".bin");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            account = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Account account1 = (Account) account;

        assert account1 != null;
        return account1.getFollowings();
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(tweetsDir, tweetID + ".bin")))) {
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
     * @param tweet     The new tweet
     */
    @Override
    public void writeTweet(String tweetsDir, Tweet tweet) {
        String tweetName = tweet.getTweetID();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(tweetsDir, tweetName + ".bin")))) {
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

    /**
     * Searches through the retweets of a certain username
     *
     * @param username The username of the account
     * @return ArrayList of the tweetIDs of retweeted tweets
     */
    public ArrayList<Tweet> searchOnRetweets(String username) {
        ArrayList<String> retweetedTweetsID = new ArrayList<>();
        ArrayList<Tweet> retweetedTweets = new ArrayList<>();
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

        for (String tweetID : retweetedTweetsID) {
            retweetedTweets.add(searchOnID(tweetID));
        }
        return retweetedTweets;
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
     * Sorts tweets based on their times
     *
     * @param timeObjectMap Mapping between the tweet and LocalDateTime
     * @return A sorted LinkedHashmap
     */
    private static LinkedHashMap<Tweet, LocalDateTime> sortOnTime(HashMap<Tweet, LocalDateTime> timeObjectMap) {
        // Create a list from elements of HashMap
        List<Map.Entry<Tweet, LocalDateTime>> list = new LinkedList<Map.Entry<Tweet, LocalDateTime>>(timeObjectMap.entrySet());
        // Sort the list
        list.sort(new Comparator<Map.Entry<Tweet, LocalDateTime>>() {
            public int compare(Map.Entry<Tweet, LocalDateTime> o1,
                               Map.Entry<Tweet, LocalDateTime> o2) {
                return -(o1.getValue()).compareTo(o2.getValue());
            }
        });
        // put data from sorted list to hashmap
        LinkedHashMap<Tweet, LocalDateTime> temp = new LinkedHashMap<Tweet, LocalDateTime>();
        for (Map.Entry<Tweet, LocalDateTime> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    private ArrayList<Tweet> sortTweetsOnTime(ArrayList<Tweet> tweets) {
        HashMap<Tweet, LocalDateTime> timeTweetMap = new HashMap<>();
        ArrayList<Tweet> sortedTweets = new ArrayList<>();
        assert tweets != null;
        for (Tweet tweet : tweets) {
            timeTweetMap.put(tweet, tweet.getTweetDate());
        }
        LinkedHashMap<Tweet, LocalDateTime> sortedTweetTimeMap = sortOnTime(timeTweetMap);
        Set<Map.Entry<Tweet, LocalDateTime>> entrySet = sortedTweetTimeMap.entrySet();
        for (Map.Entry<Tweet, LocalDateTime> tweetLocalDateTimeEntry : entrySet) {
            sortedTweets.add(tweetLocalDateTimeEntry.getKey());
        }

        return sortedTweets;
    }
}
