package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Valid-Parentheses-46f483432e2244a48eacde6930a3c2c4
 */

public class ValidParentheses {
  /**
   *
   * Big-O: O(n)
   * Justification: iterates once through string characters
   *
   */
  public boolean isValid (String s) {
    Stack<Character> stack = new Stack<>();
    for (char c:s.toCharArray()){
      if (c == '(' || c == '[' || c == '{') {
        stack.push(c);
      } else if (c == ')' && !stack.isEmpty() && stack.peek() == '(') {
        stack.pop();
      } else if (c == ']' && !stack.isEmpty() && stack.peek() == '[') {
        stack.pop();
      } else if (c == '}' && !stack.isEmpty() && stack.peek() == '{') {
        stack.pop();
      } else {
        return false;
      }
    }
    return stack.isEmpty();
  }

}
