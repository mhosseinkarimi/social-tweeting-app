package cli.frontend;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * This class handles creating requests and uses ConsoleView and Connection to handle the request and responses.
 *
 * @author Mohammad Hossein Karimi
 */
public class CommandParser {
    /**
     * Creates requests
     *
     * @param method          wanted method
     * @param description     description of the tasks
     * @param parameterValues Values of the parameters
     * @return A JSON File for request
     */
    public JSONObject writeJson(String method, String description, HashMap<String, Object> parameterValues) throws JSONException {

        return new JSONObject() {
            {
                put("method", method);
                put("description", description);
                put("parameterValues", new JSONObject() {
                    {
                        for (String key : parameterValues.keySet()) {
                            put(key, parameterValues.get(key));
                        }
                    }
                });
            }
        };
    }


    public static void main(String[] args) {
    }
}
