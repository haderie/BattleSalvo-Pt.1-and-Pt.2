package cs3500.pa04;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.EmptyJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.Join;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.ReportDamage;
import cs3500.pa04.json.Setup;
import cs3500.pa04.json.Volley;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.BoardSpecs;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.ProxyDealer;
import cs3500.pa04.model.enums.GameResult;
import cs3500.pa04.model.enums.GameType;
import cs3500.pa04.model.enums.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


 class ProxyDealerTest {

  private ByteArrayOutputStream testLog;
  private ProxyDealer dealer;
  private ProxyDealer dealer2;
  private Map<ShipType, Integer> ships;

  private Random rand;
  private Player aiPlayer;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());

    ships = new HashMap<>();
    ships.put(ShipType.SUBMARINE, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.CARRIER, 1);
    rand = new Random(3);
    aiPlayer = new AiPlayer(new BoardSpecs(rand), rand);

  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

  @Test
  public void testHandleJoin() {
    Join join = new Join("emma-klekotka", GameType.SINGLE);
    JsonNode joinNode = createSampleMessage("join", join);

    Mocket socket = new Mocket(this.testLog, List.of(joinNode.toString()));

    Random r = new Random(3);
    this.dealer = new ProxyDealer(socket, new AiPlayer(new BoardSpecs(r), r), GameType.SINGLE);
    this.dealer.run();

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":"
        + "\"emma-klekotka\",\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  @Test
  public void testHandleSetup() {
    Setup setup = new Setup(10, 10, ships);
    JsonNode joinNode = createSampleMessage("setup", setup);

    Mocket socket = new Mocket(this.testLog, List.of(joinNode.toString()));

    Random r = new Random(3);
    this.dealer = new ProxyDealer(socket, new AiPlayer(new BoardSpecs(r), r), GameType.SINGLE);
    this.dealer.run();

    String expected =
        "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":2,\"y\":0},"
            + "\"length\":6,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":1,\"y\":7},\"length\":5,"
            + "\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":4,\"y\":6},\"length\":4,\"direction"
            + "\":\"HORIZONTAL\"},{\"coord\":{\"x\":7,\"y\":1},\"length\":3,\"direction\":"
            + "\"VERTICAL\"}]}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  @Test
  public void testHandleTakeShots() {

    EmptyJson takeShots = new EmptyJson();
    JsonNode joinNode2 = createSampleMessage("take-shots", takeShots);

    Mocket socket2 = new Mocket(this.testLog, List.of(joinNode2.toString()));

    aiPlayer.setup(10, 10, ships);

    dealer2 = new ProxyDealer(socket2, aiPlayer, GameType.SINGLE);
    dealer2.run();

    String expected =
        "{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y\":0},"
            + "{\"x\":2,\"y\":2},{\"x\":4,\"y\":4},{\"x\":6,\"y\":6}]}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  @Test
  public void testHandleReportDamage() {

    ArrayList<CoordJson> list = new ArrayList<>(asList(new CoordJson(2, 0),
        new CoordJson(1, 7)));

    this.aiPlayer.setup(10, 10, ships);

    ReportDamage damage = new ReportDamage(list);
    JsonNode joinNode2 = createSampleMessage("report-damage", damage);

    Mocket socket2 = new Mocket(this.testLog, List.of(joinNode2.toString()));

    dealer2 = new ProxyDealer(socket2, aiPlayer, GameType.SINGLE);
    this.dealer2.run();

    String expected = "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\""
        + ":[{\"x\":2,\"y\":0},{\"x\":1,\"y\":7}]}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  @Test
  public void testHandleEndGame() {
    EndGameJson end = new EndGameJson(GameResult.WIN, "all ship sunk!");
    JsonNode joinNode = createSampleMessage("end-game", end);

    Mocket socket = new Mocket(this.testLog, List.of(joinNode.toString()));

    Random r = new Random(3);
    this.dealer = new ProxyDealer(socket, new AiPlayer(new BoardSpecs(r), r), GameType.SINGLE);
    this.dealer.run();

    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  @Test
  public void testHandleSuccessfulHits() {
    ArrayList<CoordJson> coord = new ArrayList<>();
    coord.add(new CoordJson(5, 5));
    coord.add(new CoordJson(2, 1));
    Volley v = new Volley(coord);
    JsonNode joinNode = createSampleMessage("successful-hits", v);

    Mocket socket = new Mocket(this.testLog, List.of(joinNode.toString()));

    Random r = new Random(3);
    AiPlayer player = new AiPlayer(new BoardSpecs(r), r);
    this.dealer = new ProxyDealer(socket, player, GameType.SINGLE);
    player.setup(6, 6, ships);
    this.dealer.run();

    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
    responseToClass(MessageJson.class);
  }

  @Test
  public void testErrors() {
    Volley v = new Volley(new ArrayList<>());
    JsonNode joinNode = createSampleMessage("Emma!!!", v);

    Mocket socket = new Mocket(this.testLog, List.of(joinNode.toString()));

    Random r = new Random(3);
    this.dealer = new ProxyDealer(socket, new AiPlayer(new BoardSpecs(r), r), GameType.SINGLE);
    assertThrows(IllegalStateException.class, () -> this.dealer.run());
  }
}
