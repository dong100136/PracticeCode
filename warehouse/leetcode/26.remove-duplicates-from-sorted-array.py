# 这题有点坑，他会检查nums前now个是否是需要的结果
class Solution(object):
    def removeDuplicates(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        if not nums:
            return 0

        now = 0
        for i in range(1,len(nums)):
            if nums[now]!=nums[i]:
                now +=1
                nums[now]=nums[i]

        return now+1

# a = Solution()
# print(a.removeDuplicates([]))
# print(a.removeDuplicates([1,2,3]))
# print(a.removeDuplicates([1,2,2,3]))
# print(a.removeDuplicates([1,1,2]))
