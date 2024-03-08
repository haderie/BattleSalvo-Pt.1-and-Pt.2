package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.enums.Direction;
import cs3500.pa04.model.enums.ShipType;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test for ship class
 */
class ShipTest {
  Ship ship1;
  Coord coord1;
  Coord coord2;
  Coord coord3;

  ArrayList<Coord> listOfCoords;

  /**
   * sets up the fields for Ship class
   */
  @BeforeEach
  void setup() {
    this.listOfCoords = new ArrayList<>();
    coord1 = new Coord(20, 9);
    coord2 = new Coord(20, 10);
    coord3 = new Coord(0, 5);
    listOfCoords.add(coord1);
    listOfCoords.add(coord2);
    listOfCoords.add(coord3);
    ship1 = new Ship(ShipType.DESTROYER, listOfCoords);
  }

  /**
   * tests that the getter method to get the list of Coords works
   */
  @Test
  void getListOfCoords() {
    assertEquals(listOfCoords, ship1.getListOfCoords());
    assertEquals(3, ship1.getListOfCoords().size());
  }

  /**
   * test that the setter method to set a ship as sunk works
   */
  @Test
  void setShipSunk() {
    assertFalse(ship1.getShipSunkStat());
    ship1.setShipSunk();
    assertTrue(ship1.getShipSunkStat());
  }

  /**
   * test that the method gets the start location of the ship
   */
  @Test
  void setGetLocationOfFirst() {
    assertEquals(new Coord(20, 9).getCoordX(), ship1.getLocationOfFirst().getCoordX());
    assertEquals(new Coord(20, 9).getCoordY(), ship1.getLocationOfFirst().getCoordY());
  }

  /**
   * test that the method determines the direction the ship is placed
   */
  @Test
  void determineDirection() {
    assertEquals(Direction.VERTICAL, ship1.determineDirection());
  }

}