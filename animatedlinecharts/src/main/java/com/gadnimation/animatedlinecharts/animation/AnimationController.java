package com.gadnimation.animatedlinecharts.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gadnimation.animatedlinecharts.data.AnimationData;
import com.gadnimation.animatedlinecharts.data.DrawingCoordinate;
import com.gadnimation.animatedlinecharts.models.LineChart;

import java.util.ArrayList;
import java.util.List;

/**
 * This class control the animation set (start,stop)
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */

public class AnimationController {

    public static final String PROPERTY_X = "PROPERTY_X";
    public static final String PROPERTY_Y = "PROPERTY_Y";
    public static final String PROPERTY_ALPHA = "PROPERTY_ALPHA";
    public static final int VALUE_NONE = -1;
    public static final int ALPHA_START = 100;
    public static final int ALPHA_END = 255;
    private int animationDuration = 1000;
    private boolean repeat = false;

    private LineChart lineChart;
    private AnimatorSet animatorSet;
    private AnimationListener listener;
    private AnimationData animationData;


    /**
     * @param lineChart line chart model
     * @param listener  Animation listener
     */
    public AnimationController(@NonNull LineChart lineChart, @Nullable AnimationListener listener) {
        this.lineChart = lineChart;
        this.listener = listener;
        this.animatorSet = new AnimatorSet();
    }

    /**
     * This method start the ser animation
     */
    public void animate() {
        if (animatorSet.isRunning()) {
            animatorSet.end();
        }
        this.animatorSet.playSequentially(createAnimatorList());
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (repeat)
                    animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    /**
     * This method stop the ser animation
     */
    public void stop() {
        this.animatorSet.end();
        animatorSet.end();
    }

    /**
     * This method create the animation list for value Animator Set
     *
     * @return list of Animator ready for animate
     */
    private List<Animator> createAnimatorList() {
        List<DrawingCoordinate> drawingCordinateList = lineChart.getDrawingCoordinateList();
        List<Animator> animatorList = new ArrayList<>();
        for (DrawingCoordinate drawData : drawingCordinateList) {
            if (drawData.getStopX() == 0.0 || drawData.getStopY() == 0.0) {
                DrawingCoordinate drawingCordinate = new DrawingCoordinate();
                drawingCordinate.setStartX(drawData.getStartX());
                drawingCordinate.setStartY(drawData.getStartY());
                drawingCordinate.setStopX(drawData.getStartX());
                drawingCordinate.setStopY(drawData.getStartY());
                animatorList.add(createAnimator(drawingCordinate));
            }
            animatorList.add(createAnimator(drawData));
        }

        return animatorList;
    }

    /**
     * This method create the value Animator for Animator Set
     *
     * @param drawData drawing data
     * @return ValueAnimator of drawing data
     */
    private ValueAnimator createAnimator(@NonNull DrawingCoordinate drawData) {
        PropertyValuesHolder propertyX = PropertyValuesHolder.ofFloat(PROPERTY_X, drawData.getStartX(), drawData.getStopX());
        PropertyValuesHolder propertyY = PropertyValuesHolder.ofFloat(PROPERTY_Y, drawData.getStartY(), drawData.getStopY());
        PropertyValuesHolder propertyAlpha = PropertyValuesHolder.ofFloat(PROPERTY_ALPHA, ALPHA_START, ALPHA_END);

        ValueAnimator animator = new ValueAnimator();
        animator.setValues(propertyX, propertyY, propertyAlpha);
        animator.setDuration(animationDuration);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AnimationController.this.onAnimationUpdate(valueAnimator);
            }


        });

        return animator;
    }

    /**
     * This method listen for animation updates
     *
     * @param valueAnimator the value animator object
     */
    private void onAnimationUpdate(@Nullable ValueAnimator valueAnimator) {
        if (valueAnimator == null || listener == null) {
            return;
        }

        float x = (float) valueAnimator.getAnimatedValue(PROPERTY_X);
        float y = (float) valueAnimator.getAnimatedValue(PROPERTY_Y);
        float alpha = (float) valueAnimator.getAnimatedValue(PROPERTY_ALPHA);
        int runningAnimationPosition = getRunningAnimationPosition();


        AnimationData value = new AnimationData();
        value.setX(x);
        value.setY(y);
        value.setAlpha(adjustAlpha(runningAnimationPosition, alpha));
        value.setRunningAnimationPosition(runningAnimationPosition);

        listener.onAnimationUpdated(value);
        animationData = value;
    }

    /**
     * This method get running animation position
     *
     * @return Integer value of running animation position
     */
    private int getRunningAnimationPosition() {
        ArrayList<Animator> childAnimations = animatorSet.getChildAnimations();
        for (int i = 0; i < childAnimations.size(); i++) {
            Animator animator = childAnimations.get(i);
            if (animator.isRunning()) {
                return i;
            }
        }

        return VALUE_NONE;
    }

    /**
     * This method adjust alpha for animator
     *
     * @param runningPos running animation position
     * @param alpha      alpha value
     * @return float value of alpha
     */
    private float adjustAlpha(int runningPos, float alpha) {
        if (animationData == null) {
            return alpha;
        }

        boolean isPositionIncreased = runningPos > animationData.getRunningAnimationPosition();
        boolean isAlphaIncreased = alpha > animationData.getAlpha();

        if (!isPositionIncreased && !isAlphaIncreased) {
            return animationData.getAlpha();
        } else {
            return alpha;
        }
    }

    /**
     * This method set animation duration
     *
     * @param animationDuration Integer value of duration
     */
    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public interface AnimationListener {

        void onAnimationUpdated(@NonNull AnimationData value);
    }

    /**
     * This method set if the animation should repeat
     * @param repeat if true will keep repeat the animation
     */
    public void setRepeat (boolean repeat){
        this.repeat = repeat;
    }


}
