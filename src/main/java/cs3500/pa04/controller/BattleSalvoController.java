package cs3500.pa04.controller;

import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.BoardSpecs;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameModel;
import cs3500.pa04.model.Ship;
import cs3500.pa04.model.UserPlayer;
import cs3500.pa04.model.enums.GameResult;
import cs3500.pa04.model.enums.ShipType;
import cs3500.pa04.view.ViewClass;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * represents the controller for BattleSalvo game
 */
public class BattleSalvoController implements Controller {
  Readable input;
  Appendable output;
  Random rand;

  Map<ShipType, Integer> amount = new HashMap<>();

  int sumOfShips;


  /**
   * represetns the constructor
   *
   * @param rand   represents the random used in userplayer and AiPlayer
   * @param input  represents the system.in used to read user input
   * @param output represents the appendable used to store the output
   */
  public BattleSalvoController(Readable input, Appendable output, Random rand) {
    this.input = input;
    this.output = output;
    this.rand = rand;
  }

  /**
   * represents the method to call the model classes
   */
  @Override
  public void run() throws IOException {
    Scanner scanner = new Scanner(input);
    ViewClass view = new ViewClass(output);
    view.welcomeUser(); // welcome user
    view.promptBoardSize(); // prompt boardSize
    int boardHeight = Integer.parseInt(scanner.next());
    int boardWidth = Integer.parseInt(scanner.next());

    //prints error message and re-prompts user
    while (boardHeight < 6 || boardHeight > 15
        || boardWidth < 6 || boardWidth > 15) {
      view.rePromptBoardSize();
      boardHeight = Integer.parseInt(scanner.next());
      boardWidth = Integer.parseInt(scanner.next());
    }
    //sets up boardSpecs
    BoardSpecs userBoard = new BoardSpecs(rand);
    BoardSpecs aiBoard = new BoardSpecs(rand);

    view.promptNumShips(findMin(boardHeight, boardWidth)); //prompt the for number of fleets
    askUserFleetInput(scanner, boardHeight, boardWidth); //collect user's input for number of ships

    GameModel userPlayer = new UserPlayer(userBoard);
    GameModel aiPlayer = new AiPlayer(aiBoard, rand);
    userPlayer.setup(boardHeight, boardWidth, amount); //set up user board
    aiPlayer.setup(boardHeight, boardWidth, amount); //set up ai board
    System.out.println("Player 1 Board: ");
    view.displayBoard(userBoard.getGameBoard());
    System.out.println("Ai Player Board: ");
    view.displayBoard(aiBoard.getEmptyBoard());

    view.promptShots(sumOfShips);

    //get player shots
    List<Coord> listPlayerCoords = new ArrayList<>();
    for (int i = 0; i < sumOfShips; i++) {
      int x = scanner.nextInt();
      if (x > boardWidth) {
        System.err.println("Uh oh! " + x + " is out of bounds please try again!");
        x = scanner.nextInt();
      }
      int y = scanner.nextInt();
      if (y > boardWidth) {
        System.err.println("Uh oh! " + y + " is out of bounds please try again!");
        y = scanner.nextInt();
      }
      Coord coords = new Coord(x, y);
      listPlayerCoords.add(coords);
    }
    //shots the player takes
    //boardSpecs.setPlayerShots(listPlayerCoords);
    userPlayer.takeShots();

    //ai player's successful hits
    userPlayer.reportDamage(aiPlayer.takeShots());
    //user player's successful hits
    aiPlayer.reportDamage(listPlayerCoords);
    System.out.println("Player 1 Board: ");

    view.displayBoard(userBoard.getGameBoard());
    System.out.println("Ai player Board: ");
    view.displayBoard(aiBoard.getEmptyBoard());


    boolean aiDone = false;
    boolean userDone = false;
    int userCount = 0;

    while (!aiDone && !userDone) {
      view.promptShots(sumOfShips - checkSunkShips(userBoard, userCount));
      List<Coord> playerCoords = new ArrayList<>();
      for (int i = 0; i < sumOfShips - checkSunkShips(userBoard, userCount); i++) {
        Coord coords = new Coord(scanner.nextInt(), scanner.nextInt());
        playerCoords.add(coords);
      }

      aiBoard.setPlayerShots(playerCoords);
      userPlayer.takeShots();

      //ai player's successful hits
      aiPlayer.successfulHits(userPlayer.reportDamage(aiPlayer.takeShots()));
      //user player's successful hits
      userPlayer.successfulHits(aiPlayer.reportDamage(playerCoords));
      System.out.println("Player 1 Board: ");
      view.displayBoard(userBoard.getGameBoard());
      System.out.println("Ai Player Board: ");
      view.displayBoard(aiBoard.getEmptyBoard());

      int aiCount = 0; //count for the ai's sunk ships
      userCount = 0; //count for the user's sunk ships

      if (checkSunkShips(aiBoard, aiCount) == aiBoard.getAllShips().size()) {
        aiDone = true;
        userPlayer.endGame(GameResult.WIN, "You shot them all down!");
      }

      if (checkSunkShips(userBoard, userCount) == userBoard.getAllShips().size()) {
        userDone = true;
        aiPlayer.endGame(GameResult.WIN, "Player 2 shot them all down!");

      }
    }
  }

  /**
   * askes the user for the fleet size inputs
   */
  void askUserFleetInput(Scanner scanner, int height, int width) throws IOException {
    ViewClass view = new ViewClass(System.out);
    int sub = Integer.parseInt(scanner.next());
    int dest = Integer.parseInt(scanner.next());
    int battle = Integer.parseInt(scanner.next());
    int carrier = Integer.parseInt(scanner.next());

    //handle invalid input
    while (sub == 0 || dest == 0 || battle == 0 || carrier == 0
        || (sub + dest + battle + carrier > findMin(height, width))) {

      view.promptNumShips(findMin(height, width));
      sub = Integer.parseInt(scanner.next());
      dest = Integer.parseInt(scanner.next());
      battle = Integer.parseInt(scanner.next());
      carrier = Integer.parseInt(scanner.next());
    }

    amount.put(ShipType.SUBMARINE, sub);
    amount.put(ShipType.DESTROYER, dest);
    amount.put(ShipType.BATTLESHIP, battle);
    amount.put(ShipType.CARRIER, carrier);

    sumOfShips = sub + dest + battle + carrier;
  }

  int checkSunkShips(BoardSpecs board1, int aiCount) {
    for (Ship aiShip : board1.getAllShips()) {
      if (aiShip.getShipSunkStat()) {
        aiCount += 1;
      }
    }
    return aiCount;
  }

  /**
   * finds the minimum number between height and width
   *
   * @param height represents the height of the board
   * @param width  represents the width of the board
   * @return the smallest number between the height and width
   */
  private int findMin(int height, int width) {
    return Math.min(height, width);
  }


}
