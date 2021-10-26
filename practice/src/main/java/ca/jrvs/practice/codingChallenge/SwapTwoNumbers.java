package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Swap-two-numbers-478f311a76214d98ab2a1ffc6e02314a
 */

public class SwapTwoNumbers {

  /**
   *
   * Big-O: O(n)
   * Justification: looping through entire array, however n will always be 2 so O(1) is also acceptable
   *
   */

  public int[] swapNumbers(int[] nums) {
    // z = x + y
    nums[0] = nums[0] + nums[1];
    // x = z - y
    nums[1] = nums[0] - nums[1];
    //y = z - x
    nums[0] = nums[0] - nums[1];
    return nums;
  }
}
