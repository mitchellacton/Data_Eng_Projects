package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Fibonacci-Number-Climbing-Stairs-ef8e7744a9234111bf7833b54624c2e1
 */

public class FibonacciNumber {
  /**
   *
   * Big-O: O(n)
   * Justification: iterates once from 0 to n, no recursion
   *
   */
  public int getFib(int n) {
    if (n == 1 || n == 2) {
      return 1;
    }
    int[] fibs = new int[n];
    fibs[0] = 1;
    fibs[1] = 1;
    for(int i = 2; i<n; i++) {
      fibs[i] = fibs[i-1] + fibs[i-2];
    }
    return fibs[n-1];
  }
}
