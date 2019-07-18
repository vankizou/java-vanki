package leetcode.middle;

/**
 * https://leetcode-cn.com/problems/add-two-numbers/
 *
 * Definition for singly-linked list.
 *
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
public class Solution2 {
    public static void main(String[] args) {
        System.out.println(10 % 10);

//        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
//        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));

        ListNode l1 = new ListNode(5);
        ListNode l2 = new ListNode(5);

        ListNode listNode = new Solution2().addTwoNumbers(l1, l2);
        do {
            System.out.print(listNode.val);
        } while ((listNode = listNode.next) != null);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
             return null;
        }

        int sumVal = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);

        ListNode nextL1 = l1 == null ? null : l1.next;
        ListNode nextL2 = l2 == null ? null : l2.next;

        int calcSumVal = sumVal % 10;

        /**
         * 两数和超过10，移位给下面的节点
         */
        if (calcSumVal != sumVal) {
            if (nextL1 != null) {
                nextL1.val++;
            } else if (nextL2 != null) {
                nextL2.val++;
            } else {
                nextL1 = new ListNode(1);
            }
        }

        ListNode listNode = new ListNode(calcSumVal);
        listNode.next = addTwoNumbers(nextL1, nextL2);

        return listNode;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            this(x, null);
        }

        ListNode(int x, ListNode next) {
            this.val = x;
            this.next = next;
        }
    }

}