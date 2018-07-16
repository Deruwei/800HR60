package com.hr.ui.api;

public class StrPermutations {

    /**
     * 递归实现字符串的全排列
     */
    public void StrPermutations(char[] str, int from, int to){
        if(to <= 1){
            return;
        }
        if(from == to){
            System.out.println(str);
        }
        for(int i=from;i<to;i++){ //i指向from到to之间的元素，方便与第一个字符做交换
            swap(str, i, from);
            StrPermutations(str, from+1, to);
            swap(str, i, from);
        }
    }
    /**
     * 字符数组的元素交换
     * @param c
     * @param a
     * @param b
     */
    public void swap(char[] c, int a, int b){
        char cc = c[a];
        c[a] = c[b];
        c[b] = cc;
    }public static void main(String[] args) {
        StrPermutations strPermutations = new StrPermutations();
        char[] c = {'h','e','a'};
        strPermutations.StrPermutations(c, 0, c.length);
    }
}