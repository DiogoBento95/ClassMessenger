package classMessenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Needs to be runnable so it can be submitted to the thread pool.
public class Dispatcher implements Runnable{

    //Dispatcher properties.
    //It needs to know the server so it can use its broadcast method.
    private ClassMessenger classMessenger;
    private Socket clientSocket;

    //Client Input and Output.
    private BufferedReader in;
    private PrintWriter out;

    //Communication is done with Strings.
    private String messageIn;

    private int clientIndex = 0;

    //Creates a "wrapper" for the client.
    public Dispatcher(Socket clientSocket, ClassMessenger classMessenger) throws IOException {
        this.classMessenger = classMessenger;
        this.clientSocket = clientSocket;

        //Initiates the reader and writer for each client.
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());

    }


    //This method is invoked automatically.
    @Override
    public void run() {

        classMessenger.broadcast("Client has entered the chat.");

        //We can use true or clientSocket.isBound();
        while(clientSocket.isBound()){

            try {

                //Will read the client input/message.
                messageIn = in.readLine();
                //The broadcast method will receive the client "messageIn" as an argument
                //and will send it to all other clients.
                classMessenger.broadcast(messageIn);

                if(messageIn.equals("/quit")){
                    classMessenger.broadcast("Client has left the chat.");
                    clientSocket.close();

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    //Method that receives a string and prints it to the console.
    public void receiveBroadcast(String message){

        out.print(message + "\n");
        out.flush();

    }

}
