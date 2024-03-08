package cs3500.pa04;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.BoardSpecs;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.ProxyDealer;
import cs3500.pa04.model.enums.GameType;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param host the host
   * @param port the port the server connects to
   * @param type the type of game to play, single or multi
   */
  private static void runClient(String host, int port, String type)
      throws IOException, IllegalStateException {
    // TODO: implement
    try (Socket server = new Socket(host, port)) {
      Random r = new Random();
      BoardSpecs boardSpecs2 = new BoardSpecs(r);
      Player aiPlayer = new AiPlayer(boardSpecs2, r);

      GameType t = getType(type);

      ProxyDealer proxyDealer = new ProxyDealer(server, aiPlayer, t);
      proxyDealer.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Project entry point
   *
   * @param args - 2 or 0 command lines required
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      try {
        Driver.runClient(host, port, "SINGLE");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else if (args.length == 0) {
      Readable input = new InputStreamReader(System.in);
      Appendable output = new OutputStreamWriter(System.out);
      BattleSalvoController salvoControl = new BattleSalvoController(input, output, new Random());
      try {
        salvoControl.run();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {
      throw new IllegalArgumentException("You did not input 0 or 2 command-line arguments," +
          " you input " + args.length + ".");
    }

  }

  /**
   * Project entry point
   *
   * @param s the game type given in main
   * @return the game type in enum form
   */
  public static GameType getType(String s) {
    if (s.equals("SINGLE")) {
      return GameType.SINGLE;
    } else if (s.equals("MULTI")) {
      return GameType.MULTI;
    } else {
      throw new RuntimeException("not a valid type!");
    }
  }
}

