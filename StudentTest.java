/**
 * To test with JUnit, add JUnit to your project. To do this, go to
 * Project->Properties. Select "Java Build Path". Select the "Libraries"
 * tab and "Add Library". Select JUnit, then JUnit 4.
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

  @Test
  public void test() {
    empties();
    //System.out.println("Passed");
    singletons();
    //System.out.println("Passed");
    defaultJudgeTest();
    //System.out.println("Passed");
    customJudgeTest();
    //System.out.println("Passed");
    doubletons();
    //System.out.println("Passed");
    //simpleAlignment();
    //pathMarks();
    //runBenchmarks();
  }

  public void runBenchmarks() {
    SequenceAligner sa;
    Result result;
    sa = new SequenceAligner("CTAGT", "CTGC");
    result = sa.getResult(0, 0);
    assertNotNull(result);
    assertEquals(0, result.getScore());
    assertEquals(Direction.NONE, result.getParent());
    result = sa.getResult(0, 1);
    assertNotNull(result);
    assertEquals(-1, result.getScore());
    assertEquals(Direction.LEFT, result.getParent());
    result = sa.getResult(1, 0);
    assertNotNull(result);
    assertEquals(-1, result.getScore());
    assertEquals(Direction.UP, result.getParent());
    result = sa.getResult(1, 1);
    assertNotNull(result);
    assertEquals(2, result.getScore());
    assertEquals(Direction.DIAGONAL, result.getParent());
    result = sa.getResult(2, 2);
    assertNotNull(result);
    assertEquals(4, result.getScore());
    assertEquals(Direction.DIAGONAL, result.getParent());
    result = sa.getResult(5, 4);
    System.out.println(sa.getX());
    System.out.println(sa.getY());
    System.out.println(result);
    assertNotNull(result);
    assertEquals(-1, result.getScore());


  }
  @Test
  public void defaultJudgeTest() {
    Judge judge = new Judge();
    assertEquals(2, judge.score('A',  'A'));
    assertEquals(2, judge.score("A",  "A"));
  }
  @Test
  public void customJudgeTest() {
    Judge judge = new Judge(3, -3, -2);
    assertEquals(3, judge.score('A',  'A'));
    assertEquals(3, judge.score("A",  "A"));
  }

  /**********************************************
   * Testing SequenceAligner.fillCache()
   **********************************************/
  @Test
  public void empties() {
    SequenceAligner sa;
    Result result;
    sa = new SequenceAligner("", "");
    result = sa.getResult(0, 0);
    assertNotNull(result);
    assertEquals(0, result.getScore());
    assertEquals(Direction.NONE, result.getParent());
  }
  @Test
  public void singletons() {
    SequenceAligner sa;
    Result result;
    sa = new SequenceAligner("A", "A");
    result = sa.getResult(0, 0);
    assertNotNull(result);
    assertEquals(0, result.getScore());
    assertEquals(Direction.NONE, result.getParent());
    System.out.println(result);
    result = sa.getResult(0, 1);
    System.out.println(result);
    assertNotNull(result);
    assertEquals(-1, result.getScore());
    assertEquals(Direction.LEFT, result.getParent());
    result = sa.getResult(1, 0);
    assertNotNull(result);
    assertEquals(-1, result.getScore());
    assertEquals(Direction.UP, result.getParent());
    result = sa.getResult(1, 1);
    assertNotNull(result);
    assertEquals(2, result.getScore());
    assertEquals(Direction.DIAGONAL, result.getParent());
  }
  @Test
  public void doubletons() {
    SequenceAligner sa;
    Result result;
    sa = new SequenceAligner("AG", "AG");
    result = sa.getResult(0, 0);
    assertNotNull(result);
    assertEquals(0, result.getScore());
    assertEquals(Direction.NONE, result.getParent());
    result = sa.getResult(0, 1);
    assertNotNull(result);
    assertEquals(-1, result.getScore());
    assertEquals(Direction.LEFT, result.getParent());
    result = sa.getResult(1, 0);
    assertNotNull(result);
    assertEquals(-1, result.getScore());
    assertEquals(Direction.UP, result.getParent());
    result = sa.getResult(1, 1);
    assertNotNull(result);
    assertEquals(2, result.getScore());
    assertEquals(Direction.DIAGONAL, result.getParent());
    result = sa.getResult(2, 2);
    assertNotNull(result);
    assertEquals(4, result.getScore());
    assertEquals(Direction.DIAGONAL, result.getParent());
  }

  /**********************************************
   * Testing SequenceAligner.traceback()
   **********************************************/

  @Test
  public void simpleAlignment() {
    SequenceAligner sa;
    sa = new SequenceAligner("ACGT", "ACGT");
    assertTrue(sa.isAligned());
    assertEquals("ACGT", sa.getAlignedX());
    assertEquals("ACGT", sa.getAlignedY());
  }

  @Test
  public void pathMarks() {
    SequenceAligner sa;
    sa = new SequenceAligner("AGACG", "CCGCT");
    assertEquals("_AGACG", sa.getAlignedX());
    assertEquals("CCG_CT", sa.getAlignedY());
    // check that start and end are on the path
    assertTrue(sa.getResult(0, 0).onPath());
    assertTrue(sa.getResult(5, 5).onPath());
    int[][] expectedScores = {
            {  0, -1, -2, -3, -4, -5 },
            { -1, -2, -3, -4, -5, -6 },
            { -2, -3, -4, -1, -2, -3 },
            { -3, -4, -5, -2, -3, -4 },
            { -4, -1, -2, -3,  0, -1 },
            { -5, -2, -3,  0, -1, -2 },
    };
    for (int i = 0; i < 6; i++)
      for (int j = 0; j < 6; j++)
        assertEquals(expectedScores[i][j],
                sa.getResult(i, j).getScore());
    // expected coords on optimal path
    int[] is = { 0, 0, 1, 2, 3, 4, 5 };
    int[] js = { 0, 1, 2, 3, 3, 4, 5 };
    int k = 0;
    for (int i = 0; i < 6; i++)
      for (int j = 0; j < 6; j++)
        if (i == is[k] && j == js[k]) {
          assertTrue(sa.getResult(i, j).onPath());
          k++;
        }
        else
          assertFalse(sa.getResult(i, j).onPath());
  }

}