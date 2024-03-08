package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa04.controller.BattleSalvoController;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

class DriverTest {

  @Test
  public void fakeTest() {

    Appendable output = new StringBuilder();
    Readable input = new StringReader("6 6\n1\n1\n1\n1\n0\n1\n0\n2\n0\n3\n0\n4"
        + "\n0\n5\n1\n0\n1\n1\n1\n2\n1\n3\n1\n3\n1\n5\n2"
        + "\n1\n3\n1\n4\n1\n5\n1\n5\n2\n5\n3\n5\n4\n5\n5");

    BattleSalvoController controller =
        new BattleSalvoController(input, output, new Random(4));

  }

}