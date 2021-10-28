package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class ValidPalindrome {

  public static void main(String[] args) {
    String s = "mom";
    isValid(s);
  }
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
