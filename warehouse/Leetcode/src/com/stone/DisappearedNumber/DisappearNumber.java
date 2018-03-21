package com.stone.DisappearedNumber;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length;
        List<Integer> arr = new ArrayList<>();
        for (int i=0;i<n;i++){
            arr.add(-1);
        }
        for (int i=0;i<nums.length;i++){
            arr.set(nums[i]-1,1);
        }
        List<Integer> rs = new ArrayList<>();
        for (int i=0;i<arr.size();i++){
            if (arr.get(i)<0){
                rs.add(i);
            }
        }
        return rs;
    }
}


public class DisappearNumber {
    public static void main(String[] args){
        int[] nums = {4,3,2,7,8,2,3,1};
        List<Integer> integerList = new Solution().findDisappearedNumbers(nums);
        for (Integer num:integerList){
            System.out.println(num);
        }
    }
}
