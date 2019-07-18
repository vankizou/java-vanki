package leetcode.middle;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 */
class Solution3 {
    public static void main(String[] args) {
        System.out.println(new Solution3().lengthOfLongestSubstring("abcabcbb"));
        System.out.println(new Solution3().lengthOfLongestSubstring("bbbbb"));
        System.out.println(new Solution3().lengthOfLongestSubstring("pwwkew"));
        System.out.println(new Solution3().lengthOfLongestSubstring(" "));
        System.out.println(new Solution3().lengthOfLongestSubstring("123"));
        System.out.println(new Solution3().lengthOfLongestSubstring("dvdf"));
        System.out.println(new Solution3().lengthOfLongestSubstring("aabaab!bb"));
    }

    public int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        if (len == 1) {
            return 1;
        }

        int noRepeatMaxLen = 0;
        List<Character> currNoRepeatChars = new ArrayList<>(len);
        int indexOfTmp;
        List<Character> currNoRepeatCharsTmp;
        for (char c : chars) {
            if ((indexOfTmp = currNoRepeatChars.indexOf(c)) > -1) {
                if (noRepeatMaxLen < currNoRepeatChars.size()) {
                    noRepeatMaxLen = currNoRepeatChars.size();
                }
                currNoRepeatCharsTmp = new ArrayList<>(len - indexOfTmp);
                currNoRepeatCharsTmp.addAll(currNoRepeatChars.subList(indexOfTmp + 1, currNoRepeatChars.size()));
                currNoRepeatChars = currNoRepeatCharsTmp;
            }
            currNoRepeatChars.add(c);
        }
        return noRepeatMaxLen < currNoRepeatChars.size() ? currNoRepeatChars.size() : noRepeatMaxLen;
    }
}