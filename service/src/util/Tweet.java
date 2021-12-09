package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Tweet {
    //fields
    private static final int tweetLength = 256;
    private String ownerUsername;
    private String tweetText;
    private final String parentTweetID;
    private final LocalDateTime tweetDate;
    private int likesNumber;
    private ArrayList<String> likers;

    public Tweet(String ownerUsername, String tweetText, String parentTweetID, LocalDateTime tweetDate) {
        this.ownerUsername = ownerUsername;
        this.parentTweetID = parentTweetID;
        this.tweetDate = tweetDate;
        likesNumber = 0;
        likers = new ArrayList<>();
        if (checkTweetLength(tweetText)) {
            this.tweetText = tweetText;
        }

    }

    protected boolean checkTweetLength(@org.jetbrains.annotations.NotNull String tweetText) {
        return tweetText.length() <= tweetLength;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getParentTweetID() {
        return parentTweetID;
    }

    public LocalDateTime getTweetDate() {
        return tweetDate;
    }

    public void likeTweet(String likerUsername) {
        likers.add(likerUsername);
        likesNumber++;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "@" + ownerUsername + '\n' + tweetText + '\n' + tweetDate.format(dtf) + '\n' +
                "Likes: " + Integer.toString(likesNumber);
    }
}
