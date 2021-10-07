package ca.jrvs.practice.codingChallenge;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ca.jrvs.practice.codingChallenge.EvenOrOdd;



public class ChallengeTest {

  @Test
  void evenOddTest() {
    EvenOrOdd testInstance = new EvenOrOdd();
    int valOdd = 5;
    int valEven = 6;

    assertEquals("odd", testInstance.evenOrOdd(valOdd));
    assertEquals("even", testInstance.evenOrOdd(valEven));
  }

  @Test
  void twoSumTest() {
    TwoSum testInstance = new TwoSum();
    int[] arrA = {0,1,2,3,4,5,6,7};
    int[] solA = {3,5};
    int[] arrB = {0,0,0,0,5,6};
    int[] solB = {4,5};

    assertArrayEquals(solA, testInstance.twoSum(arrA, 8));
    assertArrayEquals(solB, testInstance.twoSum(arrB, 11));
  }

  @Test
  void stringToIntTest() {
    StringToInteger testInstance = new StringToInteger();
    String strA = "1234";
    String strB = "-10";

    assertEquals(1234, testInstance.stringToInt(strA));
    assertEquals(-10, testInstance.stringToInt(strB));
  }
}
