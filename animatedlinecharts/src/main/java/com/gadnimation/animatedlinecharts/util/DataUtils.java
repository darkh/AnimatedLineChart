package com.gadnimation.animatedlinecharts.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gadnimation.animatedlinecharts.data.DataSet;
import com.gadnimation.animatedlinecharts.data.DrawingCoordinate;
import com.gadnimation.animatedlinecharts.models.LineChart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class having the methods need it to calculate the data and return list of points
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */
public class DataUtils {

    /**
     * This method get list of Drawing Coordinates
     *
     * @param lineChart line chart model
     * @return list of Drawing Coordinates
     */
    public static List<DrawingCoordinate> getDrawingData(@Nullable LineChart lineChart) {
        if (lineChart == null || lineChart.getDataSetList().isEmpty()) {
            return new ArrayList<>();
        }

        return createDrawingDataList(lineChart, lineChart.getDataSetList());

    }

    /**
     * This method calculate Drawing Coordinates of the data set (startX,startY,stopX,stopY)
     *
     * @param lineChart   line chart model
     * @param dataSetList data set
     * @return Drawing Coordinates of the data set (startX,startY,stopX,stopY)
     */
    public static List<DrawingCoordinate> createDrawingDataList(@Nullable LineChart lineChart, @Nullable List<DataSet> dataSetList) {
        List<DrawingCoordinate> drawingCoordinates = new ArrayList<>();
        assert dataSetList != null;
        for (int i = 0; i < dataSetList.size(); i++) {
            DrawingCoordinate drawingCoordinate = new DrawingCoordinate();
            assert lineChart != null;
            float startX = getCoordinateX(lineChart, i, dataSetList.size());
            float startY = getCoordinateY(lineChart, i, dataSetList);
            drawingCoordinate.setStartX(startX);
            drawingCoordinate.setStartY(startY);
            int nextPosition = i + 1;
            if (nextPosition < dataSetList.size()) {
                float stopX = getCoordinateX(lineChart, i + 1, dataSetList.size());
                float stopY = getCoordinateY(lineChart, i + 1, dataSetList);


                drawingCoordinate.setStopX(stopX);
                drawingCoordinate.setStopY(stopY);

            }

            drawingCoordinates.add(drawingCoordinate);

        }
        return drawingCoordinates;

    }

    /**
     * This method calculate point for coordinate x
     *
     * @param lineChart line chart model
     * @param index     which index of data set
     * @param listSize  the list size of data set
     * @return x position depends on data set
     */
    public static float getCoordinateX(@NonNull LineChart lineChart, int index, int listSize) {
        if (index == 0) {
            return getActualLeftPadding(lineChart);
        }

        return lineChart.getWidth() * (((float) index) / listSize) + getActualLeftPadding(lineChart);

    }

    /**
     * This method calculate point for coordinate y
     *
     * @param lineChart   line chart model
     * @param index       which index of data set
     * @param dataSetList data set
     * @return y position depends on data set
     */
    public static float getCoordinateY(@NonNull LineChart lineChart, int index, List<DataSet> dataSetList) {

        return lineChart.getHeight() * getDataPoint(index, dataSetList, lineChart) + lineChart.getTopPadding();

    }

    /**
     * This method calculate point for dataset
     *
     * @param index       which index of data set
     * @param dataSetList data set
     * @param lineChart   line chart model
     * @return float value of data point
     */
    public static float getDataPoint(int index, List<DataSet> dataSetList, @Nullable LineChart lineChart) {
        assert lineChart != null;
        float data = (dataSetList.get(index).getPoint() - lineChart.getMinData()) / lineChart.getVerticalDelta();

        return lineChart.isInvertVerticalAxis() ? 1.f - data : data;
    }

    /**
     * This method calculate left padding
     *
     * @param lineChart line chart model
     * @return float value of actual left padding by adding the label size + padding
     */
    public static float getActualLeftPadding(@NonNull LineChart lineChart) {
        return lineChart.getLeftPadding() + lineChart.getMaxLabelWidth() * 1.5f;
    }

    /**
     * This method adjust the data range to find vertical delta and store values
     *
     * @param dataSetList        list of data
     * @param lineChart          chart model
     * @param invertVerticalAxis vertical data direction
     */
    public static void setAdjustmentDataRange(List<DataSet> dataSetList, LineChart lineChart, boolean invertVerticalAxis) {
        float minValue = Float.MAX_VALUE;
        float maxValue = Float.MIN_VALUE;

        for (int i = 0; i < dataSetList.size(); i++) {
            if (dataSetList.get(i).getPoint() < minValue) minValue = dataSetList.get(i).getPoint();
            if (dataSetList.get(i).getPoint() > maxValue) maxValue = dataSetList.get(i).getPoint();

        }
        minValue = minValue - (minValue / 2);
        maxValue = maxValue + (5);
        float verticalDelta = maxValue - minValue;
        lineChart.setMinData(minValue);
        lineChart.setMaxData(maxValue);
        lineChart.setInvertVerticalAxis(invertVerticalAxis);
        lineChart.setVerticalDelta(verticalDelta);

    }

    /**
     * This method generate vertical Labels for the chart
     *
     * @param TextBackground     labels background
     * @param textBoundaries     labels Rect
     * @param invertVerticalAxis vertical data direction
     * @param lineChart          chart model
     * @param size               number of labels
     * @return String list of labels
     */
    public static List<String> generateVerticalData(Paint TextBackground, Rect textBoundaries, boolean invertVerticalAxis, LineChart lineChart, int size) {
        float maxLabelWidth = 0.f;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<String> verticalLabels = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            float step;

            if (!invertVerticalAxis) {
                step = ((float) i / 10.f);
            } else {
                step = ((float) (10 - i)) / 10.f;
            }


            float value = step * lineChart.getVerticalDelta() + (lineChart.getMinData());

            verticalLabels.add(decimalFormat.format(value));
            TextBackground.getTextBounds(verticalLabels.get(i), 0, verticalLabels.get(i).length(), textBoundaries);
            lineChart.setTextBound(textBoundaries.width());
            if (textBoundaries.width() > maxLabelWidth) {
                maxLabelWidth = textBoundaries.width();
                lineChart.setMaxLabelWidth(maxLabelWidth);
            }
        }

        return verticalLabels;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
