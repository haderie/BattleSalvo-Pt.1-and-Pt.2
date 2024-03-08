package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test class for controller
 */
class BattleSalvoControllerTest {

  BattleSalvoController controller;
  Readable readable;
  Appendable output;

  /**
   * sets up controller
   */
  @BeforeEach
  void setup() {
    readable = new StringReader("6\n6\n1\n1\n1\n1\n0\n1\n0\n2\n0\n3\n0\n4"
        + "\n0\n5\n2\n0\n2\n1\n2\n2\n2\n3\n2\n4\n1\n0\n1"
        + "\n1\n1\n2\n1\n5\n2\n5\n3\n5\n4\n5\n5\n5\n5\n5\n5\n0\n1");

    output = new StringBuilder();
    controller = new BattleSalvoController(readable, output, new Random(6));

  }

  /**
   * runs controller
   */
  @Test
  void run() {

    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * runs invalid controller
   */
  @Test
  void invalidCoordInp() {
    readable = new StringReader("3\n3\n19\n19\n6\n6\n1\n1\n1\n2\n10\n6\n5\n5\n4\n15");
    controller = new BattleSalvoController(readable, output, new Random(1));


    assertThrows(RuntimeException.class,
        () -> controller.run());

    String userInput =
        "Hello! Welcome to the OOD BattleSalvo Game!Please enter a valid height and width "
            + "below:Uh Oh! You've entered invalid dimensions. Please remember that the height "
            + "and width\nof the game must be in the range (6, 10), inclusive. Try again!Uh Oh! " +
            "You've entered invalid dimensions. Please remember that the height" +
            " and width\nof the game must be in the range (6, 10), inclusive. Try again!Please"
            + " enter your fleet in the order [Submarine, Destroyer, Battleship, Carrier].\n"
            + "Remember, your fleet may not exceed size 6.";

    assertEquals(output.toString(), userInput);
  }


  @Test
  void invalidFleetInp() {
    readable = new StringReader("6\n6\n9\n1\n1\n1\n0\n0\n0\n0\n1\n1\n1\n1\n4\n18\n4\n5");
    controller = new BattleSalvoController(readable, output, new Random(1));


    assertThrows(RuntimeException.class,
        () -> controller.run());

    String userInput = "Hello! Welcome to the OOD BattleSalvo Game!Please enter a valid height and"
        + " width below:Please enter your fleet in the order [Submarine,"
        + " Destroyer, Battleship, Carrier].\n"
        + "Remember, your fleet may not exceed size 6.";

    assertEquals(output.toString(), userInput);
  }

  /**
   * runs invalid controller
   */
  @Test
  void aiRun() {
    readable = new StringReader("6\n6\n1\n1\n1\n1\n0\n1\n0\n2\n0\n3\n0\n4"
        + "\n0\n5\n2\n0\n2\n1\n2\n2\n2\n3\n2\n4\n1\n0\n1"
        + "\n1\n1\n2\n1\n5\n2\n5\n3\n5\n4\n5\n5\n5\n5\n5\n5\n0\n1"
        + "\n5\n3\n5\n4\n5\n5\n5\n5\n5\n5\n0\n1\n5\n5\n5\n5");
    controller = new BattleSalvoController(readable, output, new Random(1));


    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String userInput = "Hello! Welcome to the OOD BattleSalvo Game!Please enter a "
        + "valid height and width below:Please enter your fleet in "
        + "the order [Submarine, Destroyer, Battleship, Carrier].\n"
        + "Remember, your fleet may not exceed size 6.";

    assertEquals(output.toString(), userInput);
  }
}