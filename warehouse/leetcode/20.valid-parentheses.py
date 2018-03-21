class Solution(object):
    def isValid(self, s):
        """
        :type s: str
        :rtype: bool
        """
        seq = []
        lc = ['(','[','{']
        rc = [')',']','}']
        for x in s:
            if x in lc:
                seq.append(x)
            elif x in rc:
                i = rc.index(x)
                l = lc[i]
                if len(seq)>0 and l==seq[len(seq)-1]:
                    seq.pop()
                else:
                    return False

        if len(seq)==0:
            return True
        else:
            return False


a = Solution()
print(a.isValid(']'))
# print(a.isValid('('))
# print(a.isValid(''))
# print(a.isValid("([)"))
# print(a.isValid("([)]"))
