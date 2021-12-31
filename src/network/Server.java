//package network;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
///**
// * Server class to handle client requests and  responses
// *
// * @author Mohammad Hosein Karimi
// * @version 1.0
// */
//class Server {
//    // fields
//    private static int portNumber;
//
//    public Server(int portNumber) {
//        Server.portNumber = portNumber;
//    }
//
//    public static void main(String[] args) {
//        ServerSocket server = null;
//        connectionService cs = new connectionService(); // To be implemented
//
//        try {
//            // server is listening on port 1234
//            server = new ServerSocket(portNumber);
//            server.setReuseAddress(true);
//
//            // running infinite loop for getting client request
//            while (true) {
//                // socket object to receive incoming client requests
//                Socket client = server.accept();
//
//                // Displaying that new client is connected to server
//                System.out.println("New client connected" + client.getInetAddress().getHostAddress());
//
//                // Create a new thread for the client
//                Session clientSock = new Session(client, cs);
//
//                // Creating a Session for the client
//                new Thread(clientSock).start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (server != null) {
//                try {
//                    server.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     * This class implements the actions in case of connections to server and passes the requests to
//     * Command Parser to process the request and return responses.
//     */
//    private static class Session implements Runnable {
//        private final Socket clientSocket;
//
//        // Constructor
//        public Session(Socket socket, connectionService cs) throws IOException {
//            this.clientSocket = socket;
//        }
//
//        public void run() {
//            ObjectOutputStream outToClient = null;
//            ObjectInputStream inFromClient = null;
//
//            try {
//                // get the output stream of client
//                outToClient = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
//
//                // get the input stream of client
//                inFromClient = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
//
//                try {
//                    // Passing the reuensts JSON Files to commandParser
//                    JSONObject request = (JSONObject) inFromClient.readObject();
//                    JSONObject response = cs.processReuest(request);
//                    outToClient.writeObject(response);
//                } catch (IOException | ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}