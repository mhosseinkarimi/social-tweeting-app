package observer;

import util.Account;
import util.AccountIOInterface;

import java.io.*;
import java.util.ArrayList;

/**
 * Observer class Handles follow/unfollow service and prints tweets
 * @author mohammad mahdi ghadaksaz
 * @version 1.1
 */
public class Observer implements AccountIOInterface {

    private String path = "";

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
        //reads file
        Temp = objectFileReader(path + "/" + username + ".bin");
        //casting
        account1 = (Account) Temp;
        //returns followers
        return account1.getFollowers();
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
        //reads file
        Account account1 = null;
        Temp = objectFileReader(path + "/" + username + ".bin");
        //casting
        account1 = (Account) Temp;
        //returns followings
        return account1.getFollowings();
    }

    /**
     * Follow method provide follow service for this program
     * @param username1
     * @param username2
     * @override
     */
    public void Follow(String username1, String username2){
        //temp object1
        Object Temp1 = null;
        //temp object2
        Object Temp2 = null;
        //account1
        Account account1 = null;
        //account2
        Account account2 = null;
        //reads file
        Temp1 = objectFileReader(path + "/" + username1 + ".bin");
        //casting
        account1 = (Account) Temp1;
        //checks user1 state
        if(account1.isSignedIn()){

            //reads file
            Temp2 = objectFileReader(path + "/" + username2 + ".bin");
            //casting
            account2 = (Account) Temp2;
            //add following for user1
            account1.addFollowing(username2);
            //add follower for user2
            account2.addFollower(username1);
            //writes user1
            objectFileWriter(path + "/" + username1 + ".bin", account1);
            //writes user2
            objectFileWriter(path + "/" + username2 + ".bin", account2);

        }

        else
            System.out.println("SignIn First!");

    }

    /**
     * unFollow method provides unFollowing service in this program
     * @param username1
     * @param username2
     */
    public void unFollow(String username1, String username2){
        //temp object1
        Object Temp1 = null;
        //temp object2
        Object Temp2 = null;
        //account1
        Account account1 = null;
        //account2
        Account account2 = null;
        //reads file
        Temp1 = objectFileReader(path + "/" + username1 + ".bin");
        //casting
        account1 = (Account) Temp1;
        //checks user1 state
        if(account1.isSignedIn()){

            //reads file
            Temp2 = objectFileReader(path + "/" + username2 + ".bin");
            //casting
            account2 = (Account) Temp2;
            //removes following for user1
            account1.removeFollowing(username2);
            //removes follower for user2
            account2.removeFollower(username1);
            //writes user1
            objectFileWriter(path + "/" + username1 + ".bin", account1);
            //writes user2
            objectFileWriter(path + "/" + username2 + ".bin", account2);

        }

        else
            System.out.println("SignIn First!");

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

}

