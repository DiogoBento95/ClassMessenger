package classMessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Dispatcher implements Runnable{

    private ClassMessenger classMessenger;
    private Socket clientSocket;

    //Client Input.
    private BufferedReader in;

    //Communication is done with Strings.
    private String messageIn;

    //Server output via Dispatcher.
    private PrintWriter out;
    private String messageOut;


    public Dispatcher(Socket clientSocket, ClassMessenger classMessenger) throws IOException {
        this.classMessenger = classMessenger;
        this.clientSocket = clientSocket;
        System.out.println("New client connected.");
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }


    @Override
    public void run() {

        while(true){

            try {

                //Will read the clients input/messages.
                messageIn = in.readLine();
                classMessenger.broadcast(messageIn);
                System.out.println(messageIn);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void receiveBroadcast(String message){

        out.print(message + "\n");
        out.flush();

    }

}
