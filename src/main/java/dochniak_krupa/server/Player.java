package dochniak_krupa.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Thread {
    Socket socket;
    BufferedReader input;
    PrintWriter output;
    int number;

    Player(Socket socket, int number){
        this.socket = socket;
        this.number = number;
    }

    public void run(){
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

            //Sending initial message to the client
            output.println("Welcome to the Chinese Checkers server!");
            output.println("You are the player number: " + number);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            while(true){
                String command = input.readLine();
                /*
                -TODO responding for specific server commands in switch statement
                switch (command){

                }*/
            }
        }catch (IOException e){
            System.out.println("Unable to read line!");
        }finally {
            try{
                input.close();
            }catch (IOException e){
                System.out.println("Unable to close stream!");
            }
        }

    }
}
