package com.gadnimation.animatedlinecharts.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.gadnimation.animatedlinecharts.data.DataSet;
import com.gadnimation.animatedlinecharts.models.LineChart;

import java.util.List;

/**
 * This class having the methods need it to draw the chart
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */
public class DrawingUtils {

    /**
     * draw legend frame
     *
     * @param dataSetList    list of data
     * @param backgroundPath path for background
     * @param leftPadding    left padding for the view
     * @param topPadding     top padding for the view
     * @param width          canvas width
     * @param height         canvas height
     * @param size           number of vertical labels
     * @return path of the frame
     */
    public static Path getLegendFrame(List<DataSet> dataSetList, Path backgroundPath, float leftPadding, float topPadding, float width, float height, int size) {

        //  Log.e("width",width+"  "+height);
        backgroundPath.reset();
        for (int i = 0; i <= dataSetList.size(); i++) {
            if (i == 0 || i == dataSetList.size()) {
                float xl = width * (((float) i) / dataSetList.size()) + leftPadding;
                backgroundPath.moveTo(xl, topPadding);
                backgroundPath.lineTo(xl, topPadding + height);
            }
        }

        for (int i = 0; i <= size; i++) {
            float yl = ((float) i / size) * height + topPadding;
            backgroundPath.moveTo(leftPadding, yl);
            backgroundPath.lineTo(leftPadding + width, yl);
        }

        return backgroundPath;

    }

    /**
     * draw the bottom legend of the chart
     *
     * @param lineChart       line chart model
     * @param backgroundPaint Paint of background
     * @param canvas          the canvas
     * @param textBoundaries  Rect of the labels text boundaries
     */
    public static void drawLegendBottomAxis(LineChart lineChart, Paint backgroundPaint, Canvas canvas, Rect textBoundaries) {
        for (int i = 0; i < lineChart.getDataSetList().size(); i++) {
            float xl = lineChart.getWidth() * (((float) i) / lineChart.getDataSetList().size()) + (lineChart.getLeftPadding() + lineChart.getMaxLabelWidth() * 1.5f);
            backgroundPaint.getTextBounds(lineChart.getDataSetList().get(i).getPointName(), 0, lineChart.getDataSetList().get(i).getPointName().length(), textBoundaries);
            //  canvas.save();
            //  canvas.rotate(-20,xl - (lineChart.getTextBound() / 2),lineChart.getHeight() + lineChart.getTopPadding() + backgroundPaint.getTextSize() * 1.5f);
            canvas.drawText(lineChart.getDataSetList().get(i).getPointName(),
                    xl - (lineChart.getTextBound() / 2),
                    lineChart.getHeight() + lineChart.getTopPadding() + backgroundPaint.getTextSize() * 1.5f,
                    backgroundPaint);
            //  canvas.restore();
        }
    }

    /**
     * draw the left side legend
     *
     * @param lineChart       line chart model
     * @param size            number of labels
     * @param backgroundPaint paint of the background
     * @param canvas          the canvas
     * @param verticalLabels  list of vertical labels
     */
    public static void drawLegendSideAxis(LineChart lineChart, int size, Paint backgroundPaint, Canvas canvas, List<String> verticalLabels) {
        //lineChart.setLabelLeftPadding(lineChart.getLeftPadding() + lineChart.getMaxLabelWidth() *0.25f);
        for (int i = 0; i < size; i++) {
            float step = ((float) i / size);
            float yl = step * lineChart.getHeight() + lineChart.getTopPadding()
                    - (backgroundPaint.ascent() + backgroundPaint.descent()) * 0.5f;

            if (!verticalLabels.isEmpty()) {
                canvas.drawText(verticalLabels.get(i),
                        (lineChart.getLeftPadding() + lineChart.getMaxLabelWidth() * 0.25f),
                        yl,
                        backgroundPaint);
            }
        }
    }

}
