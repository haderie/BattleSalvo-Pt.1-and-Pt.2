package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests the Coord class
 */
class CoordTest {

  Coord coord1;
  Coord coord2;
  Coord coord3;

  /**
   * sets up the coordinates
   */
  @BeforeEach
  void setUp() {
    coord1 = new Coord(0, 5);
    coord2 = new Coord(10, 7);
    coord3 = new Coord(20, 9);

  }

  /**
   * tests that getCoordX return the "x" coordinate
   */
  @Test
  void getX() {
    assertEquals(0, coord1.getCoordX());
    assertEquals(10, coord2.getCoordX());
    assertEquals(20, coord3.getCoordX());

  }

  /**
   * tests that getCoordY return the "y" coordinate
   */
  @Test
  void getY() {
    assertEquals(5, coord1.getCoordY());
    assertEquals(7, coord2.getCoordY());
    assertEquals(9, coord3.getCoordY());
  }
}