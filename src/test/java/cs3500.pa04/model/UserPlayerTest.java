package cs3500.pa04.model;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.model.enums.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test class for user player
 */
class UserPlayerTest {

  BoardSpecs boardSpecs;
  UserPlayer userPlayer;
  List<Ship> shipList;
  Ship ship1;
  Ship ship2;
  Ship ship3;
  Ship ship4;
  Coord coords1;
  Coord coords2;
  Coord coords3;
  Coord coords4;
  ArrayList<Coord> l1;

  ShipType submarine;
  ShipType carrier;
  ShipType battleship;
  ShipType destroyer;
  Map<ShipType, Integer> occur;

  /**
   * sets up the classes for the tests
   */
  @BeforeEach
  void setupClass() {
    coords1 = new Coord(1, 3);
    coords2 = new Coord(0, 1);
    coords3 = new Coord(0, 2);
    coords4 = new Coord(4, 0);
    l1 = new ArrayList<>(asList(coords1, coords2));

    submarine = ShipType.SUBMARINE;
    carrier = ShipType.CARRIER;
    battleship = ShipType.BATTLESHIP;
    destroyer = ShipType.DESTROYER;
    occur = new HashMap<>();
    occur.put(submarine, 1);
    occur.put(carrier, 1);
    occur.put(battleship, 1);
    occur.put(destroyer, 1);

    ship1 = new Ship(submarine, l1);
    ship2 = new Ship(destroyer, l1);
    ship3 = new Ship(carrier, l1);
    ship4 = new Ship(battleship, l1);

    this.shipList = new ArrayList<>();
    shipList.add(ship1);
    shipList.add(ship2);
    shipList.add(ship3);
    shipList.add(ship4);


    boardSpecs = new BoardSpecs(new Random(1));
    userPlayer = new UserPlayer(boardSpecs);
    boardSpecs.initBoard(6, 6);
    //sets up the board and list of ships

    assertEquals(shipList.size(), userPlayer.setup(6, 6, occur).size());

  }

  /**
   * returns the name of the first player
   */
  @Test
  void name() {
    assertEquals("Hareg", userPlayer.name());
  }

  @Test
  void takeShots() {
    assertEquals(new ArrayList<>(), userPlayer.takeShots());
  }

  @Test
  void reportDamage() {
    assertEquals(coords1.getCoordX(), userPlayer.reportDamage(l1).get(0).getCoordX());

  }

  @Test
  void successfulHits() {
    userPlayer.successfulHits(l1);
  }

  @Test
  void endGame() {
  }
}