package com.wayne.sbt2.domain;

/**
 * @author 212331901
 * @date 2018/12/21
 */
public class Landmark {

    private Point landmark;

    public Point getLandmark() {
        return landmark;
    }

    public void setLandmark(Point landmark) {
        this.landmark = landmark;
    }

    @Override
    public String toString() {
        return "PredictedLandmark{" +
                "landmark=" + landmark +
                '}';
    }
}
