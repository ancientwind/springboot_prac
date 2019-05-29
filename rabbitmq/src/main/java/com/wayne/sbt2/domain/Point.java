package com.wayne.sbt2.domain;

/**
 * Created by 305020173 on 2017/10/12.
 */

public class Point {
    private int x;
    private int y; //landmark height
    private int z; //landmark position

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
