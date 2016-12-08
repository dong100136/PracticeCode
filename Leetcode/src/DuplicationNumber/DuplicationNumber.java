package DuplicationNumber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stone on 04/12/2016.
 */
public class DuplicationNumber {
    public static void main(String[] args) {
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> rs = new Solution().findDuplicates(nums);
        for (Integer num : rs) {
            System.out.println(num);
        }
    }
}

class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        int n = nums.length;
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            arr.add(0);
        }
        for (int i = 0; i < n; i++) {
            arr.set(nums[i] - 1, arr.get(nums[i] - 1)+1);
        }
        List<Integer> rs = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == 2) {
                rs.add(i + 1);
            }
        }

        return rs;
    }
}
