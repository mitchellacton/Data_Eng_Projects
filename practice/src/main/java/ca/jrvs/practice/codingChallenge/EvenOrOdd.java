package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-6c5e45697bbc447faa7b74a3a8962f00
 */

public class EvenOrOdd {

  /**
   *
   * Big-O: O(1)
   * Justification: arithmetic operation
   *
   */

  public String evenOrOdd(int num) {
    String result = "odd";
    if (num % 2 == 0) {
      result = "even";
    }
    return result;
  }
}
