package cli.frontend;

import org.json.JSONObject;

import java.util.HashMap;

public class CommandParser {
    public JSONObject writeJson(String method, String description, HashMap<String, Object> parameterValues) {

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
        ConsoleView consoleView = new ConsoleView();
        consoleView.welcome();

    }
}
