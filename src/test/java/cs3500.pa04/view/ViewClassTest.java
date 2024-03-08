package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for View class
 */
class ViewClassTest {

  Appendable output;
  ViewClass viewClass;

  /**
   * sets up the view class and the appendable
   */
  @BeforeEach
  void setUp() {
    output = new StringBuilder();
    viewClass = new ViewClass(output);
  }

  /**
   * tests that the welcome user prints out a welcome message
   */
  @Test
  void welcomeUser() {
    try {
      viewClass.welcomeUser();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String welcome = "Hello! Welcome to the OOD BattleSalvo Game!";
    assertEquals(output.toString(), welcome);
  }

  /**
   * tests that a message prompting board size shows up
   */
  @Test
  void promptBoardSize() {
    try {
      viewClass.promptBoardSize();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String prompt = "Please enter a valid height and width below:";
    assertEquals(output.toString(), prompt);
  }

  /**
   * tests that a message re-prompting board size shows up given an invalid input
   */
  @Test
  void rePromptBoardSize() {
    try {
      viewClass.rePromptBoardSize();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String rePrompt = "Uh Oh! You've entered invalid dimensions. "
        + "Please remember that the height and width\n"
        + "of the game must be in the range (6, 10), inclusive. Try again!";
    assertEquals(output.toString(), rePrompt);
  }

  /**
   * tests that a message prompting the number of ships to place shows up
   */
  @Test
  void promptNumShips() {
    try {
      viewClass.promptNumShips(5);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String promptNumShips = "Please enter your fleet in the order "
        + "[Submarine, Destroyer, Battleship, Carrier].\n"
        + "Remember, your fleet may not exceed size 5.";
    assertEquals(output.toString(), promptNumShips);
  }
}