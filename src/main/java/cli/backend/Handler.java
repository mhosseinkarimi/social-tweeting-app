package cli.backend;

import service.authentication.Authentication;
import service.observer.Observer;
import org.json.JSONArray;
import org.json.JSONObject;
import service.util.Tweet;

import java.util.Date;

/**
 * Handler class receives json objects
 * @author mohammad mahdi ghadaksaz/ mohammad hossein karimi
 * @version 1.0
 */
public class Handler {
    //authentication service
    Authentication authentication = new Authentication();
    //observer service
    Observer observer = new Observer();

    /**
     * ReadJson method reads request from output
     * @param input
     * @return json
     */
    public JSONObject ReadJson(JSONObject input){
        //switch on methods
        switch (input.getString("method")){

            case "authentication":{
                //switch on descriptions
                switch (input.getString("description")){

                    case "signUp" :
                    {
                        //calls method
                        return JSONSignUp(input.get("parameterValues").getString("name"),input.get("parameterValues").getString("name"), input.get("parameterValues").getString("lastname"), input.get("parameterValues").getString("username"), input.get("parameterValues").get("date of birth"), input.get("parameterValues").getString("password"), input.get("parameterValues").getString("biography"));

                    }

                    case "login" :
                    {
                    //calls method
                    return JSONSignUp(input.get("parameterValue").get("username"), input.get("parameterValue").get("password"));

                    }
                    case "signOut" :
                    {
                    //calls method
                    return JSONLogOut(input.get("parameterValue").get("username"));

                    }

                }

            }

            case "observer" :
            {
                switch (input.getString("description")){

                    case "follow" :{
                    //calls method
                    return JSONFollow(input.get("parameterValues").get("username1"), input.get("parameterValues").get("username1"));

                    }

                    case "unfollow" :{
                    //calls method
                    return JSONUnfollow(input.get("parameterValues").get("username1"), input.get("parameterValues").get("username1"));

                    }

                    case "deleteFollower" :{


                    }

                }
            }

        }

    }

    /**
     * JSONSignUp method performs SignUp in this program
     * @param name
     * @param lastname
     * @param username
     * @param dateOfBirth
     * @param password
     * @param biography
     * @return
     */
    public JSONObject JSONSignUp(String name, String lastname, String username, Date dateOfBirth, String password, String biography){
        //checking
        if (!observer.isExists(username)){
            //signUps new user
            authentication.signUp(name,lastname,username,dateOfBirth, password,biography);
            //new json object
            JSONObject response = new JSONObject();
            //add new information
            response.put("hasError", 0);
            //add new information
            response.put("errorCode", 0);
            //add new information
            response.put("count", 1);
            //add new information
            response.put("result", "Welcome! You have successfully signed up!");
            return response;

        }

        else{
            //new json object
            JSONObject response = new JSONObject();
            //add new information
            response.put("hasError", 1);
            //add new information
            response.put("errorCode", 1);
            //add new information
            response.put("count", 1);
            //add new information
            response.put("result", "Choose another username");
            return response;
        }

    }

    /**
     * JSONObject
     * @param username
     * @param password
     * @return
     */
    public JSONObject JSONLogin(String username, String password){
        //checks
        if(authentication.logIn(username, password)){
            //new json object
            JSONObject response = new JSONObject();
            //add new information
            response.put("hasError", 0);
            //add new information
            response.put("errorCode", 0);
            //add new information
            response.put("count", 1);
            //add new information
            response.put("result", "Welcome! You have successfully login!");
            return response;


        }
        else{
            //new json object
            JSONObject response = new JSONObject();
            //add new information
            response.put("hasError", 1);
            //add new information
            response.put("errorCode", 2);
            //add new information
            response.put("count", 1);
            //add new information
            response.put("result", "failed to login! password or username is not correct!");
            return response;

        }


    }

    /**
     * JSONLogout provides logOut option for this program
     * @param username
     * @return
     */
    public JSONObject JSONLogOut(String username){
        //logOut user
        authentication.Logout(username);
        // new json object
        JSONObject response = new JSONObject();
        //add new information
        response.put("hasError", 0);
        //add new information
        response.put("errorCode", 0);
        //add new information
        response.put("count", 1);
        //add new information
        response.put("result", "you have successfully log out!");
        return response;


    }

    /**
     * JSONFollow method provides follow option in this program
     * @param username1
     * @param username2
     * @return json
     */
    public JSONObject JSONFollow(String username1, String username2){
        //checks
        if(authentication.isExists(username1) & authentication.isExists(username2)){
        //follows
        observer.Follow(username1, username2);
        //new json object
        JSONObject response = new JSONObject();
        //add new information
        response.put("hasError", 0);
        //add new information
        response.put("errorCode", 0);
        //add new information
        response.put("count", 1);
        //add new information
        response.put("result", "you have successfully followed" + username2 + " !");
        return response;

        }

        else{
            //new json object
            JSONObject response = new JSONObject();
            //add new information
            response.put("hasError", 1);
            //add new information
            response.put("errorCode", 3);
            //add new information
            response.put("count", 1);
            //add new information
            response.put("result", username1 + " or " +  username2 + "is not exists !");
            return response;


        }


    }

    /**
     * JSONUnfollow method provides unfollowing in this program
     * @param username1
     * @param username2
     * @return
     */
    public JSONObject JSONUnfollow(String username1, String username2){
        //unfollow
        observer.unFollow(username1, username2);
        //new json file
        JSONObject response = new JSONObject();
        //add new information
        response.put("hasError", 0);
        //add new information
        response.put("errorCode", 0);
        //add new information
        response.put("count", 1);
        //add new information
        response.put("result", "you have successfully unfollowed" + username2 + " !");
        return response;

    }



}
