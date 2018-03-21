class Solution(object):
    def romanToInt(self, s):
        """
        :type s: str
        :rtype: int
        """
        a = { 'I':1,'V':5,'X':10, 'L':50, 'C':100, 'D':500,'M':1000 }
        rs = 0
        for i in range(len(s)-1):
            if a[s[i]]<a[s[i+1]]:
                rs = rs-a[s[i]]
            else:
                rs = rs+ a[s[i]]
        return rs+a[s[len(s)-1]]

if __name__=="__main__":
    a = Solution()
    print(a.romanToInt('DCXXI'))

