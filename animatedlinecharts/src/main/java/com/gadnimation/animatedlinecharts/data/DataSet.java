package com.gadnimation.animatedlinecharts.data;

import android.graphics.Bitmap;

/**
 * Object Model for the chart
 * hold point value, point name and point image
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */

public class DataSet {

    private float point;
    private String pointName;
    private Bitmap pointImage;

    public DataSet(float point, String pointName, Bitmap pointImage) {
        this.point = point;
        this.pointName = pointName;
        this.pointImage = pointImage;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Bitmap getPointImage() {
        return pointImage;
    }

    public void setPointImage(Bitmap pointImage) {
        this.pointImage = pointImage;
    }
}
