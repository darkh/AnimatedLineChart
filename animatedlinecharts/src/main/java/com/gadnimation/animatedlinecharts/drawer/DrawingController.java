package com.gadnimation.animatedlinecharts.drawer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.gadnimation.animatedlinecharts.animation.AnimationController;
import com.gadnimation.animatedlinecharts.data.AnimationData;
import com.gadnimation.animatedlinecharts.data.DrawingCoordinate;
import com.gadnimation.animatedlinecharts.models.LineChart;
import com.gadnimation.animatedlinecharts.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.gadnimation.animatedlinecharts.util.DrawingUtils.drawLegendBottomAxis;
import static com.gadnimation.animatedlinecharts.util.DrawingUtils.drawLegendSideAxis;
import static com.gadnimation.animatedlinecharts.util.DrawingUtils.getLegendFrame;

/**
 * This class control the drawing process
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */

public class DrawingController {

    private Context context;
    private LineChart lineChart;
    private AnimationData animationData;


    private Paint frameBackgroundPaint;
    private Paint linePaint;
    private Paint imageBorderPaint;
    private Path backgroundPath;
    private Rect textBoundaries;
    private List<String> verticalLabels = new ArrayList<>();
    private float textSize;

    private int alpha = 0;
    private int red = 0;
    private int blue = 0;
    private int green = 0;
    private boolean drawLegends = true;
    private boolean addImageBorder = true;
    private int setpsSize;

    /**
     * @param context   Context to get resources
     * @param lineChart chart model
     * @param textSize  label text size
     * @param setpsSize number of vertical labels
     */
    public DrawingController(@Nullable Context context, @Nullable LineChart lineChart, float textSize, int setpsSize) {
        this.context = context;
        this.lineChart = lineChart;
        this.textSize = textSize;
        this.setpsSize = setpsSize;
        init();
    }

    /**
     * start the chart drawing
     *
     * @param canvas canvas of draw
     */
    public void draw(@Nullable Canvas canvas) {
        drawBackground(canvas);
        if (drawLegends) {
            drawLegend(canvas);
        }
        initLineData(canvas);
    }


    /**
     * draw chart background
     *
     * @param canvas canvas of background
     */
    public void drawBackground(@Nullable Canvas canvas) {
        canvas.drawARGB(alpha, red, green, blue);
    }

    /**
     * draw chart side and bottom legends
     *
     * @param canvas canvas of legends
     */
    public void drawLegend(@Nullable Canvas canvas) {
        canvas.drawPath(getLegendFrame(lineChart.getDataSetList(), backgroundPath, (lineChart.getLeftPadding() + lineChart.getMaxLabelWidth() * 1.5f), lineChart.getTopPadding(), lineChart.getWidth(), lineChart.getHeight(), setpsSize), frameBackgroundPaint);
        drawLegendBottomAxis(lineChart, frameBackgroundPaint, canvas, textBoundaries);
        drawLegendSideAxis(lineChart, setpsSize, frameBackgroundPaint, canvas, verticalLabels);
    }

    /**
     * initialize the points on the chart
     *
     * @param canvas canvas of line
     */
    public void initLineData(Canvas canvas) {

        int runningAnimationPosition = animationData != null ? animationData.getRunningAnimationPosition() : AnimationController.VALUE_NONE;

        for (int i = 0; i < runningAnimationPosition; i++) {
            setData(canvas, i, false);

        }

        if (runningAnimationPosition > AnimationController.VALUE_NONE) {
            setData(canvas, runningAnimationPosition, true);

        }
    }

    /**
     * set the points on the chart
     *
     * @param canvas      canvas object
     * @param position    animation running position
     * @param isAnimation if animation enabled
     */
    public void setData(@NonNull Canvas canvas, int position, boolean isAnimation) {
        List<DrawingCoordinate> drawingCoordinateList = DataUtils.getDrawingData(lineChart);
        if (drawingCoordinateList == null || position > drawingCoordinateList.size() - 1) {
            return;
        }

        DrawingCoordinate drawingCoordinates = drawingCoordinateList.get(position);
        float startX = drawingCoordinates.getStartX();
        float startY = drawingCoordinates.getStartY();

        float stopX;
        float stopY;
        float alpha;

        if (isAnimation) {
            stopX = animationData.getX();
            stopY = animationData.getY();
            alpha = animationData.getAlpha();

        } else {
            stopX = drawingCoordinates.getStopX();
            stopY = drawingCoordinates.getStopY();
            alpha = AnimationController.ALPHA_END;
        }

        drawLines(canvas, startX, startY, stopX, stopY, alpha, position);
    }

    /**
     * draw the lines between the points
     *
     * @param canvas   canvas object
     * @param startX   starting point X
     * @param startY   starting point Y
     * @param stopX    stopping point X
     * @param stopY    stopping point Y
     * @param alpha    Animation alpha
     * @param position animation running position
     */
    public void drawLines(@NonNull Canvas canvas, float startX, float startY, float stopX, float stopY, float alpha, int position) {
        if (stopX != 0.0 && stopY != 0)
            canvas.drawLine(startX, startY, stopX, stopY, linePaint);


        if (position > 0) {
            if (addImageBorder) {
                imageBorderPaint.setAlpha((int) alpha);
                canvas.drawCircle(startX, startY, (lineChart.getDataSetList().get(position).getPointImage().getWidth() / 2) + 5, imageBorderPaint);
            }
            canvas.drawBitmap(lineChart.getDataSetList().get(position).getPointImage(), startX - (lineChart.getDataSetList().get(position).getPointImage().getWidth() / 2), startY - (lineChart.getDataSetList().get(position).getPointImage().getHeight() / 2), imageBorderPaint);
        }
    }


    /**
     * this method initialize need it object for drawing the chart
     */
    private void init() {


        linePaint = new Paint();
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(8.f);
        linePaint.setStyle(Paint.Style.STROKE);

        frameBackgroundPaint = new Paint();
        frameBackgroundPaint.setColor(Color.RED);
        frameBackgroundPaint.setStyle(Paint.Style.STROKE);
        frameBackgroundPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        frameBackgroundPaint.setTextSize(textSize);

        backgroundPath = new Path();
        textBoundaries = new Rect();


        imageBorderPaint = new Paint();
        imageBorderPaint.setStyle(Paint.Style.STROKE);
        imageBorderPaint.setAntiAlias(true);
        imageBorderPaint.setStrokeWidth(11);
        imageBorderPaint.setColor(Color.RED);


    }

    /**
     * thie method update the animation data
     *
     * @param animationData animation data object
     */
    public void updateValue(@NonNull AnimationData animationData) {
        this.animationData = animationData;
    }

    /**
     * This method set vertical labels
     *
     * @param verticalLabels list of vertical labels
     */
    public void setVerticalLabels(List<String> verticalLabels) {
        this.verticalLabels = verticalLabels;
    }

    /**
     * this method set the color for the line inside the chart
     *
     * @param color the color of the line
     */
    public void setLineColor(int color) {
        linePaint.setColor(color);
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
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * set false to remove the legends
     *
     * @param drawLegends boolean value (true will draw the legends, false will remove it)
     */
    public void setDrawLegends(boolean drawLegends) {
        this.drawLegends = drawLegends;
    }

    /**
     * set the width of the line
     *
     * @param strokeWidth Float value of the width
     */
    public void setLineStrokeWidth(float strokeWidth) {
        linePaint.setStrokeWidth(strokeWidth);
    }

    /**
     * set chart frame color
     *
     * @param color The frame color
     */
    public void setLegendsFrameColor(int color) {
        frameBackgroundPaint.setColor(color);
    }

    /**
     * set the shape of legends
     *
     * @param pathEffect shape effect
     */
    public void setLegendsPathEffect(PathEffect pathEffect) {
        frameBackgroundPaint.setPathEffect(pathEffect);
    }

    /**
     * set labels text size
     *
     * @param size float value of text size
     */
    public void setLabelTextSize(float size) {
        frameBackgroundPaint.setTextSize(size);
    }

    /**
     * set image border width
     *
     * @param strokeWidth float value of width
     */
    public void setImageBorderStrokeWidth(float strokeWidth) {
        imageBorderPaint.setStrokeWidth(strokeWidth);
    }

    /**
     * set image border color
     *
     * @param color border color
     */
    public void setImageBorderColor(int color) {
        imageBorderPaint.setColor(color);
    }

    /**
     * add border to the image
     *
     * @param addImageBorder boolean value (true will add border)
     */
    public void addImageBorder(boolean addImageBorder) {
        this.addImageBorder = addImageBorder;
    }


}
