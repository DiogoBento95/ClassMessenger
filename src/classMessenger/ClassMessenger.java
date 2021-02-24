package classMessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClassMessenger {

    //Server properties.
    public static final int PORT_NUMBER = 6789;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private Dispatcher dispatcher;

    //Server has to have the clients all in one container.
    private LinkedList clientList;

    //Server output.
    private PrintWriter out;
    private String messageOut;

    //Server will initiate when the constructor generates an instance.
    public  ClassMessenger() throws IOException {

        //Instantiating a server socket listening on port: PORT_NUMBER.
        serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("Server is listening on port <" + PORT_NUMBER + ">");

        ExecutorService threadPool = null;
        threadPool = Executors.newFixedThreadPool(10);
        threadPool.submit(dispatcher);

        while(true){

            //Server will accept if a client tries to connect.
            clientSocket = serverSocket.accept();
            clientList.add(clientSocket);
            System.out.println("New client connected.");





        }



    }

    public static void main(String[] args){

        try {
            ClassMessenger classMessenger = new ClassMessenger();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
