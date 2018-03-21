class Solution(object):
    def longestCommonPrefix(self, strs):
        """
        :type strs: List[str]
        :rtype: str
        """
        if len(strs)==0:
            return ''

        n = 0

        l = 100000
        s = ''
        for x in strs:
            if len(x)<l:
                l = len(x)
                s = x

        while n<l:
            found = False
            p = strs[0][n]
            for x in strs:
                if p!=x[n]:
                    found = True

            if found==False:
                n=n+1
            else:
                rs = strs[0][0:n]
                return rs

        return s

# a = Solution()
# print(a.longestCommonPrefix(['a']))
# print(a.longestCommonPrefix([]))
# print(a.longestCommonPrefix(['']))
# print(a.longestCommonPrefix(['abs','abc','ab']))
