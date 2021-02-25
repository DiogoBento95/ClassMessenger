package classMessenger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClassMessenger {

    //Server properties.
    public static final int PORT_NUMBER = 6789;
    private ServerSocket serverSocket;

    //Server needs to have the clients all in one container.
    //Has to be specified that it is a Dispatcher container.
    //This way the server knows the Dispatcher class.
    private LinkedList<Dispatcher> clientList = new LinkedList<>();

    //Interface that'll manage the threads so that we don't need to worry about them.
    private ExecutorService threadPool;

    //Server will initiate when the constructor generates an instance of ClassMessenger.
    public  ClassMessenger() throws IOException {

        //Instantiating a server socket listening on port: PORT_NUMBER.
        serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("Server is listening on port <" + PORT_NUMBER + ">");

        //Creating a Fixed thread pool by using one of the static factory methods in Executors.
        threadPool = Executors.newFixedThreadPool(10);

    }

    //Method that will listen for a new (client) connection.
    public void listen() throws IOException {

        while(true){

            //clientSocket = serverSocket.accept(); would be one way to accept connections,
            //But in this case we are using the Dispatcher constructor to do it.

            //Server will accept if a client tries to connect.
            Dispatcher dispatcher = new Dispatcher(serverSocket.accept(), this);

            //Submitting the new dispatcher to one of the threads in threadPool.
            threadPool.submit(dispatcher);

            //Adding the new dispatcher to the LinkedList.
            clientList.add(dispatcher);

        }

    }



    //Method that iterates through all objects/clients in the server container,
    //With the objective of sending the message sent from one client to all other clients,
    //Using each Dispatchers receiveBroadcast method.
    public void broadcast(String message){

        for (Dispatcher client : clientList) {
            client.receiveBroadcast(message);
        }

    }


    //Where we will instantiate the classMessenger/Server and invoke the listen method.
    public static void main(String[] args){

        try {
            ClassMessenger classMessenger = new ClassMessenger();
            classMessenger.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
