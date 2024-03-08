package cs3500.pa04.model.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test class for GameResult enumeration
 */
class GameResultTest {
  GameResult win;
  GameResult lose;
  GameResult draw;

  /**
   * sets up the enumerations
   */
  @BeforeEach
  void setup() {
    win = GameResult.WIN;
    lose = GameResult.LOSE;
    draw = GameResult.DRAW;
  }

  /**
   * tests the method that returns the string of the enumeration
   */
  @Test
  void getResult() {
    assertEquals("win", win.getResult());
    assertEquals("lose", lose.getResult());
    assertEquals("draw", draw.getResult());

  }
}