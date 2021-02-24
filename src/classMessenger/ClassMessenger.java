package classMessenger;

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
    //private Socket clientSocket;----

    //Server has to have the clients all in one container.
    private LinkedList<Dispatcher> clientList = new LinkedList<>();

    // Entity that'll manage the threads.
    private ExecutorService threadPool;

    //Server will initiate when the constructor generates an instance.
    public  ClassMessenger() throws IOException {

        //Instantiating a server socket listening on port: PORT_NUMBER.
        serverSocket = new ServerSocket(PORT_NUMBER);
        System.out.println("Server is listening on port <" + PORT_NUMBER + ">");


        threadPool = Executors.newFixedThreadPool(10);

    }


    public void listen() throws IOException {

        // or serverSocket.isBound();
        while(true){
            //clientSocket = serverSocket.accept();----

            //Server will accept if a client tries to connect.
            Dispatcher dispatcher = new Dispatcher(serverSocket.accept(), this);

            threadPool.submit(dispatcher);

            clientList.add(dispatcher);


        }

    }




    public void broadcast(String message){

        for (Dispatcher client:clientList) {
            client.receiveBroadcast(message);
        }

    }


    public static void main(String[] args){

        try {
            ClassMessenger classMessenger = new ClassMessenger();
            classMessenger.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
