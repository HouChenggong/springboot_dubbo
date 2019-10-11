package cn.net.health.tools.time;

import java.util.HashMap;

public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        if (nums.length < 2) {
            return new int[]{};
        }
        HashMap<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            int cha =target-nums[i];
            if (map.containsKey( cha)) {
                return new int[]{i, map.get( cha) };
            } else {
                map.put( nums[i],i);
            }
        }
        return null;

    }
}
