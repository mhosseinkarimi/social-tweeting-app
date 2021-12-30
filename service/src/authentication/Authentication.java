package authentication;
import util.Account;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.util.Scanner;

/**
 * Authentication Class Includes
 * @author mohammad mahdi ghadaksaz
 * @version 1.1
 */
public class Authentication{

    //File Writer
    FileWriter fileWriter;
    //Represents usernames
    private ArrayList<String> usernames = new ArrayList<>();
    //Represents path of the file
    private String path = "C:\\Users\\mohammad\\Desktop\\project_ap\\users";

    public Authentication(){

        try {

            fileWriter = new FileWriter(path + "\\usernames.txt", true);

        } catch(IOException e){
            e.printStackTrace();
        }

    }


    /**
     * signUp class creates new users
     * @param name name
     * @param lastname lastname
     * @param username username
     * @param dateOfBirth date of birth
     * @param password password
     * @param biography biography
     */
    public void signUp(String name, String lastname, String username, Date dateOfBirth, String password, String biography) {

        //checks username
        if(!isExists(username)){

            try {
                password = toHexString(getSHA(password));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }


            //new account
            Account account = new Account(name, lastname, username, dateOfBirth, password, biography);
            //adds user
            addUser(account);
            //sets signIn
            account.setSignedIn(true);
            //adds new account to file
            objectFileWriter(path + "\\" + username + ".bin", account);
            //output message
            System.out.println("SignUp Successfully! Welcome to Twitter!");

        }

        else
            System.out.println("Choose Another username!");
    }

    /**
     * logIn class is Login part for this program
     * @param username username
     * @param password password
     */
    public void logIn(String username, String password){

        //Represent Login
        boolean Login = false;
        //Receive accounts from file
        Object Temp = null;
        //Represent accounts
        Account account = null;
        //Convert password into sha format
        try {
            password = toHexString(getSHA(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //Search on users
        for(String Username: getUsers()){
            //Check whether usernames are equal
            if(Username.equals(username)){
              //Receive object from file
              Temp = objectFileReader(path + "\\" + username +".bin");
              //Cast temp
              account = (Account) Temp;
                //Check whether passwords are equal
              if(account.getPassword().equals(password)){
                  //Update filed
                  account.setSignedIn(true);
                  //Update account in the file
                  objectFileWriter(path + "\\" + username + ".bin", account);

                  System.out.println("Login Complete!");

                  Login = true;

              }

            }
        }

        if(!Login)
            System.out.println("ADD MESSAGE! UnSuccessFull Login");

    }

    public void Logout(String username){


        //Represent Login
        boolean Login = false;
        //Receive accounts from file
        Object Temp = null;
        //Represent accounts
        Account account = null;
        //Search on users
        for(String Username: getUsers()){
            //Check whether usernames are equal
            if(Username.equals(username)){
                //Receive object from file
                Temp = objectFileReader(path + "\\" + username +".bin");
                //Cast temp
                account = (Account) Temp;
                //Check whether passwords are equal
                if(account.isSignedIn()){
                    //Update filed
                    account.setSignedIn(false);
                    //Update account in the file
                    objectFileWriter(path + "\\" + username + ".bin", account);

                    System.out.println("Logout Complete!");

                    Login = true;

                }

            }
        }

        if(!Login)
            System.out.println("ADD MESSAGE! UnSuccessFull Logout");

    }

    /**
     * getSHA class converts passwords into SHA format
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * toHexString class returns password
     * @param hash
     * @return
     */
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
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

        return Exists;

    }

    public void addUser(Account account){   /// inja khat avval bad try

        try {

            //Buffer
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //Writing
            bufferedWriter.write(account.getUsername() + "\n");
            //Flushing
            bufferedWriter.flush();
            //Closing
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
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

}
