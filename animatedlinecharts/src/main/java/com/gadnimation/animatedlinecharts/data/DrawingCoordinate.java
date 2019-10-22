package com.gadnimation.animatedlinecharts.data;


/**
 * Object Model for Drawing Coordinate (start x, start y, stop x, stop y)
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */
public class DrawingCoordinate {
    private float startX;
    private float startY;

    private float stopX;
    private float stopY;

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getStopX() {
        return stopX;
    }

    public void setStopX(float stopX) {
        this.stopX = stopX;
    }

    public float getStopY() {
        return stopY;
    }

    public void setStopY(float stopY) {
        this.stopY = stopY;
    }
}
