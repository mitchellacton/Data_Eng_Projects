package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Palindrome-12630367fc8c41678973c028069c21d8
 */

public class ValidPalindrome {
  /**
   *
   * Big-O: O(n)
   * Justification: iterates once through string characters
   *
   */
  public static boolean isValid(String s) {
    s = s.toLowerCase();
    s = s.replaceAll("[^A-Za-z0-9]", "");
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i<s.length(); i++) {
      if (i == (s.length()/2.0) -0.5) {
        continue;
      } else if (i < s.length()/2){
        stack.push(s.charAt(i));
      } else if (!stack.isEmpty() && s.charAt(i) != stack.peek()){
        return false;
      } else if (!stack.isEmpty() && s.charAt(i) == stack.peek()){
        stack.pop();
      }
    }
    return stack.isEmpty();
  }

}
