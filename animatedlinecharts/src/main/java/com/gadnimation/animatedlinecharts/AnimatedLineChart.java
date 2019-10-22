package com.gadnimation.animatedlinecharts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.gadnimation.animatedlinecharts.data.DataSet;
import com.gadnimation.animatedlinecharts.models.LineChart;
import com.gadnimation.animatedlinecharts.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The Animated chart view
 *
 * Copyright 2018 hassan kh
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */

public class AnimatedLineChart extends View implements LineChartManager.AnimationListener{

    private LineChartManager lineChartManager;
    private LineChart lineChart;
    private boolean invertVerticalAxis;
    private List<DataSet> datasets;
    private Paint backgroundPaint;
    private Rect textBoundaries;
    private TypedArray chartTheme;
    private int defaultPadding = 0;
    private int setpsSize;



    public AnimatedLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        chartTheme = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.AnimatedLineChart, 0, 0);

        defaultPadding = (int) DataUtils.convertDpToPixel(10,context);
        setPadding(defaultPadding,defaultPadding,defaultPadding,defaultPadding);
        setpsSize = chartTheme.getInt(R.styleable.AnimatedLineChart_steps,11);

        lineChartManager = new LineChartManager(context, this,chartTheme.getFloat(R.styleable.AnimatedLineChart_textSize,20f),setpsSize);
        lineChart = lineChartManager.lineChart();
        lineChart.setLeftPadding(getPaddingLeft());

        lineChart.setRightPadding(getPaddingRight());
        lineChart.setTopPadding(getPaddingTop());
        lineChart.setBottomPadding(getPaddingBottom());

        invertVerticalAxis = true;

        backgroundPaint = new Paint();
        backgroundPaint.setTextSize(chartTheme.getFloat(R.styleable.AnimatedLineChart_textSize,20f));

        textBoundaries = new Rect();

        chartTheme.recycle();

    }

    /**
     * set the chart data
     * @param dataSetList list of data set
     */
    public void setData(List<DataSet> dataSetList){
        datasets = new ArrayList<>();
        datasets.addAll(dataSetList);

        DataUtils.setAdjustmentDataRange(datasets,lineChart,invertVerticalAxis);
        lineChartManager.setVerticalLabels(DataUtils.generateVerticalData(backgroundPaint,textBoundaries,invertVerticalAxis,lineChart,setpsSize));


        lineChart.setDataSetList(dataSetList);

    }




    @Override
    protected void onDraw(Canvas canvas) {
        lineChartManager.draw(canvas);
    }



    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {

        lineChart.setWidth(width - (lineChart.getLeftPadding() + lineChart.getMaxLabelWidth() * 1.5f) - lineChart.getRightPadding());
        lineChart.setHeight(height - lineChart.getTopPadding() - lineChart.getBottomPadding() - backgroundPaint.getTextSize() +  0.5f);
        post(new Runnable() {
            @Override
            public void run() {
                lineChart.setDrawingCoordinateList(DataUtils.getDrawingData(lineChart));
                lineChartManager.animate();
            }
        });



        super.onSizeChanged(width, height, oldwidth, oldheight);
    }

    @Override
    public void onAnimationUpdated() {
        invalidate();
    }

    /**
     * set the text size for label
     * @param size float value of size
     */
    public void setTextSize(float size){
        backgroundPaint.setTextSize(size);
        lineChartManager.setLabelTextSize(size);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * This method stop the animation
     */
    public void stopAnimate(){
        lineChartManager.stopAnimate();
        lineChartManager.animate();
        invalidate();
    }

    /**
     * This method set the background color for the chart
     *
     * @param alpha Alpha Integer value
     * @param red   Red Integer value
     * @param green Green Integer value
     * @param blue  Blue Integer value
     */
    public void setBackgroundColor(int alpha, int red, int green, int blue){
        lineChartManager.setBackgroundColor(alpha,red,green,blue);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * set false to remove the legends
     *
     * @param drawLegends boolean value (true will draw the legends, false will remove it)
     */
    public void setDrawLegends(boolean drawLegends){
        lineChartManager.setDrawLegends(drawLegends);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * set the width of the line
     *
     * @param strokeWidth Float value of the width
     */
    public void setLineStrokeWidth(float strokeWidth){
        lineChartManager.setLineStrokeWidth(strokeWidth);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * set chart frame color
     *
     * @param color The frame color
     */
    public void setLegendsFrameColor(int color){
        lineChartManager.setLegendsFrameColor(color);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * set the shape of legends
     *
     * @param pathEffect shape effect
     */
    public void setLegendsPathEffect(PathEffect pathEffect){
        lineChartManager.setLegendsPathEffect(pathEffect);
        lineChartManager.animate();
        invalidate();
    }


    /**
     * set image border width
     *
     * @param strokeWidth float value of width
     */
    public void setImageBorderStrokeWidth(float strokeWidth){
        lineChartManager.setImageBorderStrokeWidth(strokeWidth);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * set image border color
     *
     * @param color border color
     */
    public void setImageBorderColor(int color){
        lineChartManager.setImageBorderColor(color);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * add border to the image
     *
     * @param addImageBorder boolean value (true will add border)
     */
    public void addImageBorder(boolean addImageBorder){
        lineChartManager.addImageBorder(addImageBorder);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * This method set animation duration
     *
     * @param animationDuration Integer value of duration
     */
    public void setAnimationDuration(int animationDuration){
        lineChartManager.setAnimationDuration(animationDuration);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * this method set the color for the line inside the chart
     *
     * @param color the color of the line
     */
    public void setLineColor(int color){
        lineChartManager.setLineColor(color);
        lineChartManager.animate();
        invalidate();
    }

    /**
     * This method set if the animation should repeat
     * @param repeat if true will keep repeat the animation
     */
    public void setRepeat (boolean repeat){
        lineChartManager.setRepeat(repeat);
        lineChartManager.animate();
        invalidate();
    }




}
