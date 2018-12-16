package dochniak_krupa.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnection {
    BufferedReader in;
    PrintWriter out;

    //Singleton
    private static ServerConnection serverConnection = null;

    //Stores a number of client received from server
    static int clientNumber;

    //Handling server connection and setting input and output buffers
    void connectToServer() throws IOException {
        Socket socket = new Socket("",9090);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(),true);

        //Printing server initial message
        for(int i=0; i<2; i++){
            String initialMessage = in.readLine();
            System.out.println(initialMessage);
        }

        String clientNumberString = in.readLine();
        clientNumber = Integer.parseInt(clientNumberString);
    }

    static void setInstance() {
        serverConnection = new ServerConnection();
    }

    public static ServerConnection getInstance() {
        return serverConnection;
    }
}
