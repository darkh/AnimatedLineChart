package com.gadnimation.animatedlinecharts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PathEffect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gadnimation.animatedlinecharts.animation.AnimationController;
import com.gadnimation.animatedlinecharts.data.AnimationData;
import com.gadnimation.animatedlinecharts.drawer.DrawingController;
import com.gadnimation.animatedlinecharts.models.LineChart;

import java.util.List;

/**
 * This class the manager that control the animation and the drawing classes
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */
public class LineChartManager implements AnimationController.AnimationListener {


    private DrawingController drawingController;
    private AnimationController animationController;
    private AnimationListener listener;
    private LineChart lineChart;

    /**
     * @param context   the context of view
     * @param listener  Animation listener
     * @param textSize  labels text size
     * @param setpsSize number of vertical labels
     */
    public LineChartManager(@NonNull Context context, @Nullable AnimationListener listener, float textSize, int setpsSize) {
        lineChart = new LineChart();
        drawingController = new DrawingController(context, lineChart, textSize, setpsSize);
        this.animationController = new AnimationController(lineChart, this);
        this.listener = listener;
    }

    /**
     * get line chart model
     *
     * @return line chart model
     */
    public LineChart lineChart() {
        return lineChart;
    }

    /**
     * start drawing
     *
     * @param canvas the canvas
     */
    public void draw(@NonNull Canvas canvas) {
        drawingController.draw(canvas);
    }

    /**
     * start the animation
     */
    public void animate() {
        if (!lineChart.getDrawingCoordinateList().isEmpty()) {
            animationController.animate();
        }
    }

    /**
     * stop the animation
     */
    public void stopAnimate() {
        if (!lineChart.getDrawingCoordinateList().isEmpty()) {
            animationController.stop();
        }
    }

    /**
     * This method set vertical labels
     *
     * @param verticalLabels list of vertical labels
     */
    public void setVerticalLabels(List<String> verticalLabels) {
        drawingController.setVerticalLabels(verticalLabels);
    }

    /**
     * this method set the color for the line inside the chart
     *
     * @param color the color of the line
     */
    public void setLineColor(int color) {
        drawingController.setLineColor(color);
    }

    /**
     * This method set the background color for the chart
     *
     * @param alpha Alpha Integer value
     * @param red   Red Integer value
     * @param green Green Integer value
     * @param blue  Blue Integer value
     */
    public void setBackgroundColor(int alpha, int red, int green, int blue) {
        drawingController.setBackgroundColor(alpha, red, green, blue);
    }

    /**
     * set false to remove the legends
     *
     * @param drawLegends boolean value (true will draw the legends, false will remove it)
     */
    public void setDrawLegends(boolean drawLegends) {
        drawingController.setDrawLegends(drawLegends);
    }

    /**
     * set the width of the line
     *
     * @param strokeWidth Float value of the width
     */
    public void setLineStrokeWidth(float strokeWidth) {
        drawingController.setLineStrokeWidth(strokeWidth);
    }

    /**
     * set chart frame color
     *
     * @param color The frame color
     */
    public void setLegendsFrameColor(int color) {
        drawingController.setLegendsFrameColor(color);
    }

    /**
     * set the shape of legends
     *
     * @param pathEffect shape effect
     */
    public void setLegendsPathEffect(PathEffect pathEffect) {
        drawingController.setLegendsPathEffect(pathEffect);
    }

    /**
     * set labels text size
     *
     * @param size float value of text size
     */
    public void setLabelTextSize(float size) {
        drawingController.setLabelTextSize(size);
    }

    /**
     * set image border width
     *
     * @param strokeWidth float value of width
     */
    public void setImageBorderStrokeWidth(float strokeWidth) {
        drawingController.setImageBorderStrokeWidth(strokeWidth);
    }

    /**
     * set image border color
     *
     * @param color border color
     */
    public void setImageBorderColor(int color) {
        drawingController.setImageBorderColor(color);
    }

    /**
     * add border to the image
     *
     * @param addImageBorder boolean value (true will add border)
     */
    public void addImageBorder(boolean addImageBorder) {
        drawingController.addImageBorder(addImageBorder);
    }

    /**
     * This method set animation duration
     *
     * @param animationDuration Integer value of duration
     */
    public void setAnimationDuration(int animationDuration) {
        animationController.setAnimationDuration(animationDuration);
    }

    /**
     * This method listen for animation updates
     *
     * @param animationData the value animator object
     */
    @Override
    public void onAnimationUpdated(@NonNull AnimationData animationData) {
        drawingController.updateValue(animationData);
        if (listener != null) {
            listener.onAnimationUpdated();
        }
    }

    /**
     * This method set if the animation should repeat
     * @param repeat if true will keep repeat the animation
     */
    public void setRepeat (boolean repeat){
        animationController.setRepeat(repeat);
    }

    public interface AnimationListener {

        void onAnimationUpdated();
    }

}
