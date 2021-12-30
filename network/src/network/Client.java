package network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * Client class handles the actions and abilities of client (user)
 *
 * @author Mohammad Hossein Karimi
 * @version 1.0
 */
class Client {
    // Command Line Parser
    static commandParser cp = null;
    // Main method for running client socket
    public static void main(String[] args) {
        // Create a connection to the server with the provided port number
        try (Socket socket = new Socket("localhost", 1234)) {

            // Sending requests to server
            ObjectOutputStream outToServer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            // Receiving the responses from server
            ObjectInputStream inFromServer = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            cp = new commandPraser();

            // Dummy input for client later would be changed for CLI
            Scanner sc = new Scanner(System.in);
            String request;

            // Dummy Parser from Command Line reads until the "exit" is received
            while (!"exit".equalsIgnoreCase(request) && cp != null) {

                // reading from user
                request = sc.nextLine();

                JSONObject requestJSON = cp.parseRequest(request);

                // sending the user input to server
                outToServer.writeObject(requestJSON);
                outToServer.flush();

                JSONObject response = (JSONObject) inFromServer.readObject(); // To be handled later
            }

            // closing the scanner object
            sc.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
