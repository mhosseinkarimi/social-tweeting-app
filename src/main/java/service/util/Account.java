package service.util;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


/**
 * Account class represents users in this program
 * @author MohammadMahdi Ghadaksaz/ MohammadHossein Karimi
 * @version 1.1
 */
public class Account implements Serializable {

    //Represents name of user
    private String name;
    //Represents lastname of user
    private String lastname;
    //Represents userName of User
    private String username;
    //Represents date of birth of user
    private Date dateOfBirth;
    //Represents date of membership of user
    private LocalDate dateOfMembership;
    //Represents Password Of user
    private String password;
    //Represents biography Of user
    private String biography;
    //Represents state of user
    private boolean signedIn;
    //Represents followers
    private ArrayList followers;
    //represents followings
    private ArrayList followings;


    /**
     * Constructor Of Account Class
     * @param name name of user
     * @param lastname lastname of user
     * @param username username of user
     * @param dateOfBirth date of birth of user
     * @param password password of user
     * @param biography biography of user
     */
    public Account(String name, String lastname, String username, Date dateOfBirth, String password, String biography){

        //Sets Data to The Field
        this.name = name;
        //Sets data to the field
        this.lastname = lastname;
        //Sets data to the field
        this.username = username;
        //Sets data to the field
        this.dateOfBirth = dateOfBirth;
        //Sets data to the field
        this.password = password;
        //Sets data to the field
        this.biography = biography;
        //Sets data to the field
        this.dateOfMembership = LocalDate.now();
        //Represents followers
        followers = new ArrayList<>();
        //represents followings
        followings = new ArrayList<>();
    }

    /**
     * getName method returns user's name
     * @return name
     */
    public String getName() {
        //returns name
        return name;
    }

    /**
     * getLastname method returns user's lastname
     * @return lastname
     */
    public String getLastname() {
        //returns lastname
        return lastname;
    }

    /**
     * getUsername method returns user's username
     * @return username
     */
    public String getUsername() {
        //returns username
        return username;
    }

    /**
     * getPassword method returns user's password in SHA-256 format
     * @return password
     */
    public String getPassword() {
        //returns password
        return password;
    }

    /**
     * getDateOfBirth method returns user's date of birth
     * @return date of birth
     */
    public Date getDateOfBirth() {
        //returns date of birth
        return dateOfBirth;
    }

    /**
     * getBiography method returns user's biography
     * @return biography
     */
    public String getBiography() {
        //returns biography
        return biography;
    }

    /**
     * isSignedIn method returns user's state
     * @return signedIn
     */
    public boolean isSignedIn() {
        //returns signedIn
        return signedIn;
    }

    /**
     * setSignedIn method sets new condition for user
     * @param signedIn
     */
    public void setSignedIn(boolean signedIn) {
        this.signedIn = signedIn;
    }

    /**
     * setBiography method sets new biography for user
     * @param biography
     */
    public void setBiography(String biography){
        //sets new biography
        this.biography = biography;
    }

    /**
     * setName method sets new name for user
     * @param name
     */
    public void setName(String name){
        //sets new name
        this.name = name;
    }

    /**
     * setLastname method sets new lastname for user
     * @param lastname
     */
    public void setLastname(String lastname) {
        //sets new lastname
        this.lastname = lastname;
    }

    /**
     * setDateOfBirth method sets new date of birth for user
     * @param dateOfBirth
     */
    public void setDateOfBirth(Date dateOfBirth) {

        //sets new date of birth
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * getFollowers returns followers
     * @return followers
     */
    public ArrayList<String> getFollowers() {
        return followers;
    }

    /**
     * getFollowings returns followings
     * @return followings
     */
    public ArrayList<String> getFollowings() {
        return followings;
    }

    /**
     * addFollower method adds new follower
     * @param username
     */
    public void addFollower(String username){
        //adds follower
        followers.add(username);
    }

    /**
     * addFollowing method adds new following
     * @param username
     */
    public void addFollowing(String username){
        followings.add(username);
    }

    /**
     * removeFollower removes follower
     * @param username
     */
    public void removeFollower(String username){
        //removes follower
        followers.remove(username);
    }

    /**
     * removeFollowing removes following
     * @param username
     */
    public void removeFollowing(String username){
        //removes following
        followings.remove(username);
    }
}
