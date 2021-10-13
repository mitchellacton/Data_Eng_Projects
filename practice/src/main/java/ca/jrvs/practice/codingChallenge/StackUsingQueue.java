package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueue {
  Queue<Integer> q1 = new LinkedList<Integer>();
  Queue<Integer> q2 = new LinkedList<Integer>();

  public void push(int x) {
    q1.add(x);
    while(q1.size() != 1) {
      q2.add(q1.peek());
      q1.remove();
    }
    while (q2.size() != 0) {
      q1.add(q2.peek());
      q2.remove();
    }
  }

  public int pop() {
    if (q1.size() > 0){
      return q1.remove();
    } else { return 0;}
  }

  public int top() {
    return q1.peek();
  }

  public boolean empty() {
    boolean empty = false;
    if (q1.peek() == null) {empty = true;}
    return empty;
  }
}
