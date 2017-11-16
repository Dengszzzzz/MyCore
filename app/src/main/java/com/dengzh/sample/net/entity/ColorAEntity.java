package com.dengzh.sample.net.entity;

import android.support.annotation.FloatRange;

/**
 * Created by dengzh on 2017/9/25 0025.
 */

public class ColorAEntity {

    private float alpha;

    @FloatRange
    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
