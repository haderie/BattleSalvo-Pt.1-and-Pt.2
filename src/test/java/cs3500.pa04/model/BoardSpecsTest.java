package cs3500.pa04.model;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa04.model.enums.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardSpecsTest {
  BoardSpecs specs;
  Coord coords1;
  Coord coords2;
  Coord coords3;
  Coord coords4;

  Ship ship1;

  ShipType submarine;
  ShipType carrier;
  ShipType battleship;


  ArrayList<Coord> l1;
  List<Ship> shipList;
  BoardSpecs boardSpecs;
  BoardSpecs boardSpecs2;

  ArrayList<ArrayList<String>> board;
  ArrayList<ArrayList<String>> board2;

  ArrayList<String> row1;
  ArrayList<String> row2;
  ArrayList<String> row3;
  ArrayList<String> row4;
  ArrayList<String> row5;
  ArrayList<String> row6;


  @BeforeEach
  void setup() {
    boardSpecs = new BoardSpecs(new Random(1));

    //specs = new BoardSpecs(6, 6);
    coords1 = new Coord(1, 3);
    coords2 = new Coord(0, 1);
    coords3 = new Coord(0, 2);
    coords4 = new Coord(4, 0);


    l1 = new ArrayList<>(asList(coords1, coords2));
    shipList = new ArrayList<>();

    submarine = ShipType.SUBMARINE;
    carrier = ShipType.CARRIER;
    battleship = ShipType.BATTLESHIP;
    ship1 = new Ship(submarine, l1);

    row1 = new ArrayList<>(asList("_", "_", "_", "_", "_", "_"));
    row2 = new ArrayList<>(asList("_", "_", "_", "_", "_", "_"));
    row3 = new ArrayList<>(asList("_", "_", "_", "_", "_", "_"));
    row4 = new ArrayList<>(asList("_", "_", "_", "_", "_", "_"));
    row5 = new ArrayList<>(asList("_", "_", "_", "_", "_", "_"));
    row6 = new ArrayList<>(asList("_", "_", "_", "_", "_", "_"));

    board = new ArrayList<>(asList(row1, row2, row3, row4, row5, row6));
    boardSpecs.initBoard(6, 6);
    boardSpecs.initEmptyBoard(6, 6);

    shipList.add(ship1);
  }

  @Test
  void checkDupes() {
    assertTrue(boardSpecs.checkDupes(l1, new Coord(0, 1)));
    assertTrue(boardSpecs.checkDupes(l1, new Coord(1, 3)));

    assertFalse(boardSpecs.checkDupes(l1, new Coord(0, 6)));
    assertTrue(l1.get(1).getCoordX() == new Coord(0, 1).getCoordX()
        && l1.get(1).getCoordY() == new Coord(0, 1).getCoordY());
  }

  @Test
  void initBoard() {
    assertEquals(board, boardSpecs.getGameBoard());
  }

  @Test
  void placeHorizontalAndVerticalCarrier() {
    //place it properly
    boardSpecs.generateIndex(carrier);
    boardSpecs.generateIndex(submarine);
    boardSpecs.generateIndex(battleship);

  }

  /**
   * tests the getAllShips method
   */
  @Test
  void getAllShips() {
    boardSpecs.generateIndex(carrier);

    assertEquals(1, boardSpecs.getAllShips().size());
    assertEquals(coords4.getCoordX(),
        boardSpecs.getAllShips().get(0).getListOfCoords().get(0).getCoordX());
    assertEquals(coords4.getCoordY(),
        boardSpecs.getAllShips().get(0).getListOfCoords().get(0).getCoordY());

  }

  /**
   * tests that the board mutates hits properly
   */
  @Test
  void setBoardHit() {
    boardSpecs.generateIndex(carrier);
    boardSpecs.setBoardHit(coords4);

    assertEquals("H",
        boardSpecs.getGameBoard().get(coords4.getCoordY()).get(coords4.getCoordX()));
  }

  /**
   * tests that the board mutates misses properly
   */
  @Test
  void setBoardMiss() {
    boardSpecs.generateIndex(carrier);
    boardSpecs.setBoardMiss(coords2);

    assertEquals("M",
        boardSpecs.getGameBoard().get(coords2.getCoordY()).get(coords2.getCoordX()));
  }

  @Test
  void setEmptyBoardHit() {
    boardSpecs.generateIndex(carrier);
    boardSpecs.setBoardHit(coords4);

    assertEquals("_",
        boardSpecs.getEmptyBoard().get(coords4.getCoordY()).get(coords4.getCoordX()));
  }

  /**
   * tests that the board mutates misses properly
   */
  @Test
  void setEmptyBoardMiss() {
    boardSpecs.generateIndex(carrier);
    boardSpecs.setBoardMiss(coords2);

    assertEquals("_",
        boardSpecs.getEmptyBoard().get(coords2.getCoordY()).get(coords2.getCoordX()));
  }
}