package com.GameLogic;
import com.Players.Player;
import com.Imports.ImportJSON;
import com.Rooms.Room;
import com.Utility.Printer;
import com.Story.Story;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


// Definition of what is a game
public class Game implements Serializable{
    private static Player player = new Player();
    final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
    final Runnable runnable = new Runnable() {
        int countdownStarter = 5;
        @Override
        public void run() {
            System.out.println(countdownStarter);
            countdownStarter--;

            if(countdownStarter < 1){
//                System.out.println("Restarting game now");
                timer.shutdown();
            }
        }
    };
    static Game game;
    Scanner in = new Scanner(System.in);
    ArrayList<Room> map = (ArrayList<Room>) ImportJSON.getMap();
    //Object for timer on game restart -Meri



    // Not sure why this is relevant, will look into it -Meri
    static {
        try {
            game = new Game();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    // Constructor for an instance of the game
    public Game() throws IOException, ParseException {
    }

    // Method for creating a game
    public boolean beginGame() throws IOException, InterruptedException {
        printSplashScreen();
        playerSetup();
        if (checkIfPlayerReady()){
            return true;
        }
        else {
            return false;
        }

    }

    public void playGame() throws IOException, ParseException, InterruptedException {
        playerIntro();
        gameEngine();
    }

    public void gameEngine() throws IOException, InterruptedException, ParseException {
        Scanner in = new Scanner(System.in);
        Printer.print(Story.promptPlayerMessage());
        String[] location = in.nextLine().split(" ");
        try {
            if ("quit".equalsIgnoreCase(location[0])) {
                Printer.print(Story.quitMessage());
                System.exit(130);
            } else if ("restart".equalsIgnoreCase(location[0]) || "r".equalsIgnoreCase(location[0])){
                restartGame();
            }else if("save".equalsIgnoreCase(location[0])) {
                saveGame();
            }else if("load".equalsIgnoreCase(location[0])){
                loadGame();
            }else if ("help".equalsIgnoreCase(location[0]) || "h".equalsIgnoreCase(location[0])) {
                Printer.print(Story.tutorial());

            } else if ("stats".equalsIgnoreCase(location[0])) {
                PlayerMechanics.stats(getPlayer());
            }
            else if (location.length != 2) {
                Printer.print(Story.invalidEntryMessage1());
            } else if ("go".equalsIgnoreCase(location[0])) {
                PlayerMechanics.moveRoom(location[1], this);
            }  else if ("attack".equalsIgnoreCase(location[0])) {
                BattleMechanics.fight(location[1],getPlayer());
            }else if ("look".equalsIgnoreCase(location[0]) && "around".equalsIgnoreCase(location[1]) || "room".equalsIgnoreCase(location[1])) {
                PlayerMechanics.lookAround(this);
            }
            else if("look".equalsIgnoreCase(location[0]) && "map".equalsIgnoreCase(location[1])){
                PlayerMechanics.lookAtMap(this);
            }else if ("look".equalsIgnoreCase(location[0])) {
                PlayerMechanics.lookItem(location[1], player.getCurrentRoom().getItems(),player.getItems());
            } else if ("get".equalsIgnoreCase(location[0])) {
                PlayerMechanics.getItem(location[1], player.getCurrentRoom().getItems(), player.getItems());
            } else if ("drop".equalsIgnoreCase(location[0])) {
                PlayerMechanics.dropItem(location[1], player.getCurrentRoom().getItems(), player.getItems());
            }
            else if ("equip".equalsIgnoreCase(location[0])) {
                if(PlayerMechanics.checkInstance(getPlayer(),location[1])) {
                    PlayerMechanics.equipWeapon(getPlayer(),location[1]);
                } else {
                    PlayerMechanics.equipArmor(getPlayer(),location[1]);
                }
            }else if ("check".equalsIgnoreCase(location[0]) && "inventory".equalsIgnoreCase(location[1])) {
                PlayerMechanics.checkInventory(getPlayer());
            } else {
                Printer.print(Story.invalidEntryMessage2());
                playGame();
            }
        } catch (IndexOutOfBoundsException | ParseException e) {
            playGame();
        }
    }



    public void printSplashScreen() throws IOException, InterruptedException {
        Printer.print(Story.beginGameText());
    }

    public void playerIntro(){
        Player player = getPlayer();
        player.setItems(player.getItems());
        showPlayerDetails();
        showLocationDescription();
    }

    public void playerSetup(){
        String name = in.nextLine();
        Player user = new Player(name,110, 20);
        setPlayer(user);
        Player player = getPlayer();
        player.setCurrentRoom(map.get(0));
        System.out.println("Ok, " + user.getName() + " this isn't going to be an easy adventure are you ready? (Type yes or y)");
    }


    public void restartGame() throws InterruptedException, IOException {
        System.out.println("Are you sure you want to restart");
        String response = in.nextLine();
        if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")){
            timer.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
            Thread.sleep(5010);
            System.out.println("Restarting game now");
            Thread.sleep(2000);
            beginGame();
        }else {
            System.out.println("Glad you chose to keep playing!");
            Thread.sleep(2000);
            System.out.println("Lets keep playing");
            Thread.sleep(2000);
        }
    }

    public static void saveGame(){
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savedGame"));
            oos.writeObject(player);
            oos.flush();
            oos.close();
            System.out.println("Game saved");
            game.checkIfPlayerContinue();

        }catch (Exception e){
            System.out.println("Serialization Error! Can't save data.\n"
                    + e.getClass() + ": " + e.getMessage() + "\n");
        }
    }

    public static void loadGame(){
        try{
            FileInputStream fis = new FileInputStream("savedGame");
            ObjectInputStream ois = new ObjectInputStream(fis);
            player = (Player) ois.readObject();
            ois.close();
            System.out.println("Game loaded");
            System.out.println("Here is where you left off");
            System.out.println(player.getCurrentRoom() + " " + player.getItems());
        }catch (Exception e){
            System.out.println("Serialization Error! Can't load data.\n");
            System.out.println(e.getClass() + ": " + e.getMessage() + "\n");
        }
    }

    public void showPlayerDetails(){
        System.out.println("\n" + player.getName() + " is at the " + player.getCurrentRoomName());
    }

    public void showLocationDescription(){
        System.out.println(player.showCurrentRoomDesc());
    }

    public void printPlayerDetails(){
        System.out.println(getPlayer());
    }

    public boolean checkIfPlayerReady(){
        String response = in.nextLine();
        if(response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("y")){
            printPlayerDetails();
            return true;
        }
        return false;
    }

    public boolean checkIfPlayerContinue() throws InterruptedException {
        System.out.println("Do you want to continue or exit\n" +
                "Press 1 to continue or 2 to exit");
        String response = in.nextLine();
        if(response.equalsIgnoreCase("1")){
            System.out.println("Lets continue");
            Thread.sleep(2000);
            return true;

        }else if(response.equalsIgnoreCase("2")){
            System.out.println("See you soon");
            timer.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
            Thread.sleep(5010);
            System.out.println("Game ended");
            System.exit(130);
        }
        return false;
    }



    //Accessors and Mutators
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }



}
