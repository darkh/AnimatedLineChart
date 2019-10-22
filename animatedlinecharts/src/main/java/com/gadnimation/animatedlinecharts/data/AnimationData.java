package com.gadnimation.animatedlinecharts.data;

/**
 * Animation Data Object Model
 * hold x,y,alpha and running animation position
 *
 * @author hassan kh
 * @version 0.0.1
 * @since 9/5/2018
 */

public class AnimationData {

    private float x;
    private float y;
    private float alpha;
    private int runningAnimationPosition;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public int getRunningAnimationPosition() {
        return runningAnimationPosition;
    }

    public void setRunningAnimationPosition(int runningAnimationPosition) {
        this.runningAnimationPosition = runningAnimationPosition;
    }

}
