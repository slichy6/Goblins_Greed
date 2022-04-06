import org.json.simple.parser.ParseException;

import java.io.*;
import java.sql.SQLOutput;

import com.GameLogic.Game;

public class Main {
    static Game game;

//    public static void saveGame(){
//        try{
//            FileOutputStream fos = new FileOutputStream("game.sav");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(game);
//            oos.flush();
//            oos.close();
//            System.out.println("Game saved");
//        }catch (Exception e){
//            System.out.println("Serialization Error! Can't save data.\n"
//            + e.getClass() + ": " + e.getMessage() + "\n");
//        }
//    }
//
//    public static void loadGame(){
//        try{
//            FileInputStream fis = new FileInputStream("gave.sav");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            game = (Game) ois.readObject();
//            ois.close();
//            System.out.println("Game loaded");
//        }catch (Exception e){
//            System.out.println("Serialization Error! Can't load data.\n");
//            System.out.println(e.getClass() + ": " + e.getMessage() + "\n");
//        }
//    }

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        Game newGame = new Game();

        boolean startGame = newGame.beginGame();
        while (true) {

            if (startGame) {
                newGame.playGame(newGame.getPlayer());
            } else {
                break;
            }

        }

//        BufferedReader in;
//        String input;
//        String output = "";
//        Game newGame = new Game();
//        in = new BufferedReader(new InputStreamReader(System.in));
        // Maybe break the game intro out and put on this line
//        do{
//
//            boolean startGame = newGame.beginGame();
//            if(startGame){
//                newGame.playGame(newGame.getPlayer());
//            }else {
//                break;
//            }
//            input = in.readLine();
//            switch (input){
//                case "save":
//                    saveGame();
//                    break;
//                default:
//                    newGame.playGame(newGame.getPlayer());
//                    break;
//            }
//            System.out.println(output);
//        }while(!"q".equalsIgnoreCase(input)  );
    }








    }

