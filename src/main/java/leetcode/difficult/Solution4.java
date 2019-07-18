package leetcode.difficult;

/**
 * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/
 *
 * @author vanki
 * @date 2019-07-18 16:22
 */
public class Solution4 {

    public static void main(String[] args) {
//        int[] nums1 = new int[]{1, 3, 5};
//        int[] nums2 = new int[]{2, 4, 6, 7, 8};

        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{};

        System.out.println(new Solution4().findMedianSortedArrays(nums1, nums2));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int nums1Len = nums1.length;
        int nums2Len = nums2.length;
        int mergeLen = nums1Len + nums2Len;
        int[] mergeNums = new int[mergeLen];

        int mergeCursor = 0;
        int nums1Cursor = 0;
        int nums2Cursor = 0;
        for (; ; ) {
            if (nums1Len <= nums1Cursor && nums2Len <= nums2Cursor) {
                break;
            }

            if (nums1Len > nums1Cursor && nums2Len <= nums2Cursor) {
                mergeNums[mergeCursor] = nums1[nums1Cursor++];
            } else if (nums1Len <= nums1Cursor && nums2Len > nums2Cursor) {
                mergeNums[mergeCursor] = nums2[nums2Cursor++];
            } else {
                int n1 = nums1[nums1Cursor];
                int n2 = nums2[nums2Cursor];

                if (n1 <= n2) {
                    mergeNums[mergeCursor] = n1;
                    nums1Cursor++;
                } else {
                    mergeNums[mergeCursor] = n2;
                    nums2Cursor++;
                }
            }
            mergeCursor++;
        }

        int half = (mergeLen / 2);
        if ((mergeLen % 2) == 0) {
            return (mergeNums[half - 1] + mergeNums[half]) / 2.0;
        } else {
            return mergeNums[half];
        }
    }
}
