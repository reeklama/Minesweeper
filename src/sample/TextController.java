package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.*;

import java.io.IOException;
import java.util.Scanner;

public class TextController {
    final int ExitFlag = 0;
    final int NewGameFlag = 1;
    public static GameView gameView = new GameView();
    public int doGame(GameView model) throws IOException {
        System.out.println("Commands:");
        System.out.println("help");
        System.out.println("exit");
        System.out.println("open + field");
        System.out.println("flag + field");
        System.out.println("New game");

        int size = model.getLengthField();
        int minesCount = model.getMinesCount();
        Scanner scan = new Scanner(System.in);
        do{
            GameView.showGameField(size);
            System.out.println("Enter your command");
            String turn = scan.nextLine();
            if (turn.equals("exit")) {
                return ExitFlag;
            }
            if (turn.equals("new game")) {
                return NewGameFlag;
            }
            if (turn.equals("help")) {
                System.out.println("Commands:\n");
                System.out.println("help\n");
                System.out.println("exit\n");
                System.out.println("open + side side - example: open 1 1\n");
                System.out.println("flag + side side - example: flag 1 1\n");
                System.out.println("new game\n");
            }

            String[] comArgs;
            comArgs = turn.split(" ");
            int x = Integer.parseInt(comArgs[1].trim());
            int y = Integer.parseInt(comArgs[2].trim());
            Tile thisTile = GameView.grid[x][y];
            if (comArgs[0].equals("open")) {
                thisTile.open(thisTile);
            }
            if (comArgs[0].equals("flag")) {
                thisTile.flag(thisTile);
            }
        }while (!Tile.isEnd);
        if(Tile.isWin)
            return ExitFlag;
        else{
            Tile.setTrueIsFirstClick();
            return NewGameFlag;
        }
    }

    public TextController() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter length of side: ");
        int howmuch = scan.nextInt();
        System.out.println("Enter count of mines: ");
        int minesCount = scan.nextInt();
        GameView model = new GameView();
        int exit_status;
        do {

            model.initialize(howmuch, minesCount);
            exit_status = doGame(model);
            System.out.println("1 ");
            if (exit_status == 1)
                model.ggNt();
        } while (exit_status != 0);
        if ((GameView.countMarkedBombs == minesCount) || (GameView.countOpened == howmuch*howmuch - minesCount)) {
            model.ggWp();
        }
    }
}