class Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """
        if x==0:
            return 0

        sign = 1
        if x<0:
            sign = -1
            x = -x

        rs = 0
        while x>0:
            n = x % 10
            rs = rs*10+n
            x = int(x / 10)

        if rs>pow(2,31)-1:
            return 0
        else:
            return rs*sign

if __name__=="__main__":
    a = Solution()
    print(a.reverse(1534236469))
    print(a.reverse(1000000003))
    print(a.reverse(123))
    print(a.reverse(-123))
    print(a.reverse(0))
