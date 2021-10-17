package com.xperblueray.zk.zkclient;

class Solution {
    public static boolean validPalindrome(String s) {
        int head = 0;
        int tail = s.length() - 1;
        while (head < tail) {
            if (s.charAt(head) != s.charAt(tail)) {
                return isPalindrome(s.substring(head, tail)) || isPalindrome(s.substring(head + 1, tail + 1));
            }
            head++;
            tail--;
        }
        return true;
    }

    public static boolean isPalindrome(String s) {
        int head = 0;
        int tail = s.length() - 1;
        while(head <= tail) {
            if (s.charAt(head) != s.charAt(tail)) {
                return false;
            }
            head++;
            tail--;
        }
        return true;
    }

    public static void main(String[] args) {
        boolean abc = validPalindrome("abc");
        System.out.println(abc);
    }
}