# Definition for singly-linked list.
# class ListNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.next = None

class Solution(object):
    def mergeTwoLists(self, l1, l2):
        """
        :type l1: ListNode
        :type l2: ListNode
        :rtype: ListNode
        """
        rs = ListNode(0)
        i = l1
        j = l2
        x = rs

        while i or j:
            while i and (j==None or i.val<=j.val):
                x.next=i
                x = x.next
                i = i.next

            while j and (i==None or j.val<=i.val):
                x.next=j
                x = x.next
                j = j.next

        x.next = None
        return rs.next


# a = Solution()
# l1 = ListNode(1)
# l2 = ListNode(1)
# print(a.mergeTwoLists(l1,l2))
# print(a.mergeTwoLists([],[0]))
# print(a.mergeTwoLists([1,2,3,4],[1,2,3,4,5,6]))
