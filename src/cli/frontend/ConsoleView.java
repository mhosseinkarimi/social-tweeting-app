package cli.frontend;
import service.util.Tweet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * ConsoleView Class prints data for the user
 * @author mohammad mahdi ghadaksaz/ mohammad hossein karimi
 * @version 1.۰
 *
 */
public class ConsoleView{
    public int welcome() {
        System.out.println("******************************Welcome Menu*******************************");
        System.out.println("Welcome to Twitter");
        System.out.println("1) Sign in\n2) Sign up");
        Scanner scanner = new Scanner(System.in);

        return Integer.parseInt(scanner.nextLine());
    }

    public String[] signIn(){
        System.out.println("******************************Sign In*******************************");
        String[] userPass = new String[2];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        userPass[0]  = scanner.nextLine();
        System.out.print("Password: ");
        userPass[1] = scanner.nextLine();

        return userPass;
    }

    public String[] signUp(){
        System.out.println("******************************Sign Up*******************************");
        String[] userPass = new String[2];
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        userPass[0]  = scanner.nextLine();
        System.out.print("Password: ");
        userPass[1] = scanner.nextLine();

        return userPass;
    }

    public int menu(){
        System.out.println("******************************Menu*******************************");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose the number of the option [1-3]");
        System.out.println("1) TimeLine\n" +
                           "2) Search\n" +
                           "3) Post a tweet");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * ShowTweets method prints all tweets to the terminal
     * @param tweets
     * @param followings
     */
    public void ShowTweets(ArrayList<Tweet> tweets, ArrayList<String> followings){
        //iterates on tweets
        for(Tweet tweet : tweets){
            //calls method
            TweetDisplay(tweet, followings);
        }
    }

    /**
     * TweetDisplay method prints each tweet
     * @param tweet
     * @param followings
     */
    public void TweetDisplay(Tweet tweet, ArrayList<String> followings){

        //represents likers
        ArrayList<String> likers = new ArrayList<>();
        //calls method
        likers = Search(followings, tweet.getLikes());
        if(likers.size() > 0){
        //calls method
        System.out.println(likers + "liked" + "\n" + tweet.toString());}
        else
            System.out.println(tweet.toString());

    }

    /**
     * Search method searches among two arraylists and returns common usernames
     * @param followings
     * @param usernames
     * @return arraylist
     */
    public ArrayList<String> Search(ArrayList<String> followings, ArrayList<String> usernames){
        //output arraylist
        ArrayList<String> Output = new ArrayList<>();
        //iterates on usernames
        for(String username : usernames) {
            //iterates on followings
            for (String following : followings) {
                //adds if equals
                if (following.equals(username)) {
                    //adds following
                    Output.add(following);
                }
            }
        }

        return Output;
    }
}
