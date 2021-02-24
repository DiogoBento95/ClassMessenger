package classMessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.Executor;

public class Dispatcher implements Runnable{

    private Executor executor;

    private Socket clientSocket;

    //Client Input.
    private BufferedReader in;

    //Communication is done with Strings.
    private String messageIn;


    public Dispatcher(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {

        try {
            //Will read the clients input/messages.
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            messageIn = in.readLine();
            System.out.println(messageIn);




        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
