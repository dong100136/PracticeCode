class Solution(object):
    def isPalindrome(self, x):
        """
        :type x: int
        :rtype: bool
        """

        if x<0:
            return False

        if x % 10 == 0 and x!=0:
            return False

        y = x
        rs = 0
        while y>0:
            n = y % 10
            rs = rs*10+n
            y = int(y/10)

        if rs==x:
            return True
        else:
            return False

