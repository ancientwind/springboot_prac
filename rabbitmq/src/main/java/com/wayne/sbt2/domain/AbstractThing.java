package com.wayne.sbt2.domain;

/**
 * @author 212331901
 * @date 2019/7/15
 */
public abstract class AbstractThing implements IAnything {
    public void m1() {
        System.out.println("implement m1");
    }
    public void m3() {
        System.out.println("implement m1");
    }

    public static void main(String[] args) {
        boolean flag = true;
        Integer i = 0;
        int j = 1;
        int k = flag ? i : j;
        System.out.println(k);
    }
}
