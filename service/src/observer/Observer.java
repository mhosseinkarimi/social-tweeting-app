package observer;

import util.Account;
import util.AccountIOInterface;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Observer class Handles follow/unfollow service and prints tweets
 * @author mohammad mahdi ghadaksaz
 * @version 1.1
 */
public class Observer implements AccountIOInterface {

    private String path = "C:\\Users\\mohammad\\Desktop\\project_ap\\users";

    /**
     * getFollowers returns followers
     * @param username
     * @return
     */
    @Override
    public ArrayList<String> getFollowers(String username) {
        //Temp object
        Object Temp = null;
        //account1
        Account account1 = null;
        //checks username
        if(isExists(username)) {
            //reads file
            Temp = objectFileReader(path + "\\" + username + ".bin");
            //casting
            account1 = (Account) Temp;
            //returns followers
            return account1.getFollowers();
        }
        else
            return null;
    }

    /**
     * getFollowings returns followings
     * @param username
     * @return followings
     */
    @Override
    public ArrayList<String> getFollowings(String username) {
        //temp object1
        Object Temp = null;
        //account1
        Account account1 = null;
        //checks username
        if(isExists(username)) {
            //reads file
            Temp = objectFileReader(path + "\\" + username + ".bin");
            //casting
            account1 = (Account) Temp;
            //returns followings
            return account1.getFollowings();
        }
        else
            return null;
    }

    /**
     * Follow method provide follow service for this program
     * @param username1
     * @param username2
     * @override
     */
    public void Follow(String username1, String username2){
        //checks username1
        if(!isExists(username1)){
            //message
            System.out.println(username1 + " is not existed!");
        }
        //checks username2
        else if(!isExists(username2)){
            //message
            System.out.println(username2 + " is not existed!");
        }

        else {
            //reads file
            Object Temp1 = objectFileReader(path + "\\" + username1 + ".bin");
            //casting
            Account account1 = null;
            //sets data
            account1 = (Account) Temp1;
            //checks user1 state
            if (account1.isSignedIn()) {
                //reads file
                Object Temp2 = objectFileReader(path + "\\" + username2 + ".bin");
                //casting
                Account account2 = (Account) Temp2;
                //searches whether username 1 has already followed username 2
                if(Search(account1.getFollowings(), account2.getUsername())){
                    //message
                    System.out.println("You have already followed " + username2 + " !");
                }
                else {
                    //add following for user1
                    account1.addFollowing(username2);
                    //add follower for user2
                    account2.addFollower(username1);
                    //writes user1
                    objectFileWriter(path + "\\" + username1 + ".bin", account1);
                    //writes user2
                    objectFileWriter(path + "\\" + username2 + ".bin", account2);
                    //message
                    System.out.println("Now you are following " + username2 + " !");
                }
            }
            if (!account1.isSignedIn())
                //message
                System.out.println("SignIn First!");
        }
    }

    /**
     * unFollow method provides unFollowing service in this program
     * @param username1
     * @param username2
     */
    public void unFollow(String username1, String username2){
            //checks first username
            if (!isExists(username1)) {
                //message
                System.out.println(username1 + "is not Existed");
            }
            //checks second username
            else if (!isExists(username2)) {
                //message
                System.out.println(username2 + "is not Existed");
            }
            else {
                //reads file
                Object Temp1 = objectFileReader(path + "\\" + username1 + ".bin");
                //casting
                Account account1 = null;
                //sets data
                account1 = (Account) Temp1;
                //checks user1 state
                if (account1.isSignedIn()) {
                    //reads file
                    Object Temp2 = objectFileReader(path + "\\" + username2 + ".bin");
                    //casting
                    Account account2 = (Account) Temp2;
                    //searches whether username 1 has already followed username 2
                    if(!Search(account1.getFollowings(), account2.getUsername())){
                        //message
                        System.out.println("You have not followed " + username2 + " !");
                    }
                    else {
                        //removes following for user1
                        account1.removeFollowing(username2);
                        //removes follower for user2
                        account2.removeFollower(username1);
                        //writes user1
                        objectFileWriter(path + "\\" + username1 + ".bin", account1);
                        //writes user2
                        objectFileWriter(path + "\\" + username2 + ".bin", account2);
                        //message
                        System.out.println(username2 + "is now omitted from your followings!");
                    }

                } else
                    //message
                    System.out.println("SignIn First!");
            }
        }


    /**
     * deleteFollower method deletes username2 from followers of username1
     * @param username1
     * @param username2
     */
    public void deleteFollower(String username1, String username2){
        //checks first username
        if (!isExists(username1)) {
            //message
            System.out.println(username1 + "is not Existed");
        }
        //checks second username
        else if (!isExists(username2)) {
            //message
            System.out.println(username2 + "is not Existed");
        }
        else{
            //reads file
            Object Temp1 = objectFileReader(path + "\\" + username1 + ".bin");
            //casting
            Account account1 = null;
            //sets data
            account1 = (Account) Temp1;
            //checks user1 state
            if (account1.isSignedIn()) {
                //reads file
                Object Temp2 = objectFileReader(path + "\\" + username2 + ".bin");
                //casting
                Account account2 = (Account) Temp2;
                //searches whether username 1 has already followed username 2
                if(!Search(account1.getFollowers(), username2)) {
                    //message
                    System.out.println(username2 + "You has not followed you!");
                }
                else {
                    //removes follower for user1
                    account1.removeFollower(username2);
                    //removes following for user2
                    account2.removeFollowing(username1);
                    //writes user1
                    objectFileWriter(path + "\\" + username1 + ".bin", account1);
                    //writes user2
                    objectFileWriter(path + "\\" + username2 + ".bin", account2);
                    //message
                    System.out.println(username2 + "is now omitted from your followers!");
                }
            }
            else
                //message
                System.out.println("SignIn First!");
        }
    }

    public void Tweets(String username){
        //checks first username
        if (!isExists(username)) {
            //message
            System.out.println(username + "is not Existed");
        }
        else{

        }
    }


    /**
     * objectFileWriter method writes new byte base files
     * @param Path
     * @param Account
     */
    public void objectFileWriter(String Path, Object Account){

        try {
            //FileOutputStream
            FileOutputStream fileOutputStream = new FileOutputStream(Path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(Account);
        }catch (FileNotFoundException e){

            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    /**
     * objectFileReader method reads byte base files
     * @return account
     */
    public Object objectFileReader(String Path){
        //temp variable
        Object account = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            account = objectInputStream.readObject();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        //Output variable
        return account;
    }

    /**
     * getUsers method reads usernames from file
     * @return usernames
     */
    public ArrayList<String> getUsers() {

        //Represents usernames
        ArrayList<String> list = new ArrayList<>();

        try {

            //new scanner
            Scanner s = new Scanner(new File(path + "\\usernames.txt"));
            //output arraylist
            list = new ArrayList<String>();
            while (s.hasNext()) {
                list.add(s.next());
            }
            s.close();

            //IOException
        } catch (IOException e) {
            e.printStackTrace();
        }

        //returns arraylist
        return list;
    }

    /**
     * isExists searches among usernames
     * @param username
     * @return true/false
     */
    public boolean isExists(String username){

        //Output variable
        boolean Exists = false;
        //searches on usernames
        for(String UserName: getUsers()){
            //checks whether usernames are equal
            if(username.equals(UserName)){
                Exists = true;
                break;
            }
        }
        //Returns output variable
        return Exists;
    }

    /**
     * Search method searches within an arrayList and checks whether a username exists or not
     * @param UserNames
     * @param username
     * @return true/false
     */
    public boolean Search(ArrayList<String> UserNames, String username){

      //Output variable
      boolean Exists = false;
      //Searches within usernames
      for(String Username : UserNames){
          //Checks
          if(username.equals(Username))
              Exists = true;
              break;
      }
        //Returns output variable
        return Exists;
    }


}