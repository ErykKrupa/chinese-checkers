package dochniak_krupa.server;

import dochniak_krupa.client.Client;

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
            //Sending client number
            output.println(number);

        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            while(true){
                String command = input.readLine();
                switch (command){
                    case "CREATE MULTIPLAYER 2": {

                        if(Game.getInstance()==null){
                            Game.setInstance(2);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE");
                        }else{
                            output.println("Nie można stworzyć gry. Lobby już istnieje!");
                        }
                    } break;
                    case "CREATE MULTIPLAYER 3": {
                        if(Game.getInstance()==null){
                            Game.setInstance(3);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE");
                        }else{
                            output.println("Nie można stworzyć gry. Lobby już istnieje!");
                        }
                    } break;
                    case "CREATE MULTIPLAYER 4": {
                        if(Game.getInstance()==null){
                            Game.setInstance(4);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE");
                        }else{
                            output.println("Nie można stworzyć gry. Lobby już istnieje!");
                        }
                    } break;
                    case "CREATE MULTIPLAYER 6": {
                        if(Game.getInstance()==null){
                            Game.setInstance(6);
                            Game.getInstance().currNumOfPlayers++;
                            output.println("CREATE GAME PRIVILEGE");
                        }else{
                            output.println("Nie można stworzyć gry. Lobby już istnieje!");
                        }
                    } break;
                    case "JOIN GAME":{
                        if(Game.getInstance()!=null && Game.getInstance().currNumOfPlayers<Game.getInstance().declaredNumberOfPlayersInGame){
                            Game.getInstance().currNumOfPlayers++;
                            output.println("JOIN GAME PRIVILEGE");
                        }else{
                            output.println("Cannot join game!");
                        }
                    }break;
                }
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
