package com.syp.algorithm;

import java.util.Scanner;

/**
 * @Author: SYP
 * @Date: 2020/8/22
 * @Description:
 * 统计回文数
 * 例如：
 * 输入aaa  输出6  a a a aaa aa aa
 * 输入aaba  输出6  a a b a aa aba
 */
public class Huiwenshu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String str = sc.nextLine(); //aa bb abba
            int count = 0;
            for(int i=0; i<str.length(); i++){
                count++;
                for(int j=i+1; j<str.length(); j++){
                    if(isHuiWenShu(str.substring(i,j+1))){
                        count++;
                    }
                }
            }
            System.out.println(count);
        }
    }
    private static boolean  isHuiWenShu(String str){
        for(int i=0; i<str.length()/2; i++){
            if(str.charAt(i) == str.charAt(str.length()-1-i)){
                continue;
            }else {
                return false;
            }
        }
        System.out.println(str);
        return true;
    }
}
