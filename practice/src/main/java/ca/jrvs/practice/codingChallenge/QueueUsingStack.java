package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * ticket: https://www.notion.so/jarvisdev/Implement-Queue-using-Stacks-61ec466b28094f3fb199188a9e26a616
 */

public class QueueUsingStack {

  /**
   *
   * push Big-O: O(n)
   * Justification: must empty the main stack into the buffer stack, then return all elements to the main stack
   * pop/peek/empty Big-O: O(1)
   * Justification: Only accesses top element of stack
   *
   */

  Stack<Integer> bufferStack = new Stack<>();
  Stack<Integer> mainStack = new Stack<>();

  public void push(int x) {
    while(!mainStack.empty()) {
      bufferStack.push(mainStack.pop());
    }
    mainStack.push(x);
    while(!bufferStack.empty()) {
      mainStack.push(bufferStack.pop());
    }
  }

  public int pop() {
    return mainStack.pop();
  }

  public int peek() {
    return mainStack.peek();
  }

  public boolean empty() {
    return mainStack.empty();
  }
}
