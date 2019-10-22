package com.gadnimation.animatedlinecharts.models;

import com.gadnimation.animatedlinecharts.data.DataSet;
import com.gadnimation.animatedlinecharts.data.DrawingCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * line chart model
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */

public class LineChart {

    private float width;
    private float height;
    private float leftPadding;
    private float bottomPadding;
    private float rightPadding;
    private float topPadding;
    private boolean invertVerticalAxis;
    private float verticalDelta;
    private float labelLeftPadding;
    private float minData;
    private float maxData;
    private float maxLabelWidth;
    private int radius;
    private int inerRadius;
    private int textBound;
    private List<DataSet> dataSetList = new ArrayList<>();
    private List<DrawingCoordinate> drawingCoordinateList = new ArrayList<>();

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(float leftPadding) {
        this.leftPadding = leftPadding;
    }

    public float getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(float bottomPadding) {
        this.bottomPadding = bottomPadding;
    }

    public float getRightPadding() {
        return rightPadding;
    }

    public void setRightPadding(float rightPadding) {
        this.rightPadding = rightPadding;
    }

    public float getTopPadding() {
        return topPadding;
    }

    public void setTopPadding(float topPadding) {
        this.topPadding = topPadding;
    }

    public boolean isInvertVerticalAxis() {
        return invertVerticalAxis;
    }

    public void setInvertVerticalAxis(boolean invertVerticalAxis) {
        this.invertVerticalAxis = invertVerticalAxis;
    }

    public float getVerticalDelta() {
        return verticalDelta;
    }

    public void setVerticalDelta(float verticalDelta) {
        this.verticalDelta = verticalDelta;
    }

    public float getLabelLeftPadding() {
        return labelLeftPadding;
    }

    public void setLabelLeftPadding(float labelLeftPadding) {
        this.labelLeftPadding = labelLeftPadding;
    }

    public List<DataSet> getDataSetList() {
        return dataSetList;
    }

    public void setDataSetList(List<DataSet> dataSetList) {
        this.dataSetList = dataSetList;
    }

    public List<DrawingCoordinate> getDrawingCoordinateList() {
        return drawingCoordinateList;
    }

    public void setDrawingCoordinateList(List<DrawingCoordinate> drawingCoordinateList) {
        this.drawingCoordinateList = drawingCoordinateList;
    }

    public float getMaxData() {
        return maxData;
    }

    public void setMaxData(float maxData) {
        this.maxData = maxData;
    }

    public float getMinData() {
        return minData;
    }

    public void setMinData(float minData) {
        this.minData = minData;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getMaxLabelWidth() {
        return maxLabelWidth;
    }

    public void setMaxLabelWidth(float maxLabelWidth) {
        this.maxLabelWidth = maxLabelWidth;
    }

    public int getInerRadius() {
        return inerRadius;
    }

    public void setInerRadius(int inerRadius) {
        this.inerRadius = inerRadius;
    }

    public int getTextBound() {
        return textBound;
    }

    public void setTextBound(int textBound) {
        this.textBound = textBound;
    }
}
