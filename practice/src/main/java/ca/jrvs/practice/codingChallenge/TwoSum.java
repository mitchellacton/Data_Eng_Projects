package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * ticket: https://www.notion.so/jarvisdev/Two-Sum-83ca8102fe5f4f50a77bfa6b73f5eaa2
 */

class TwoSum {

  /**
   *
   * Big-O: O(n)
   * Justification: hashmap has constant lookup time, only one for loop
   *
   */

  public int[] twoSum(int[] nums, int target) {
    // Initialize hash map to store key value pairs
    Map<Integer, Integer> hashMap = new HashMap<>();

    // Iterate through nums array searching the hashmap for the
    // complement of nums[i] that will add to the target value
    for (int i = 0; i < nums.length; i++) {
      if (hashMap.containsKey(target - nums[i])) {
        // Return the matching indices if found
        return new int[] {hashMap.get(target - nums[i]), i};
      }
      // Store the value and index in the hashmap
      hashMap.put(nums[i], i);
    }
    // Throw exception if no matching pair is found
    throw new IllegalArgumentException("No matching pair found!");
  }
}
