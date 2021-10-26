package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/jarvisdev/String-to-Integer-atoi-b911d30d980d4422b0c846be68f70c65
 */

public class StringToInteger {

  /**
   *
   * Big-O: O(n)
   * Justification: n is length of string, parser iterates through each character of string
   *
   */

  public int stringToInt(String string) {
    return Integer.parseInt(string);
  }
}
