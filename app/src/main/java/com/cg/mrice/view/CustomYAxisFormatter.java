package com.cg.mrice.view;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by app on 2018/4/17.
 */
public class CustomYAxisFormatter implements IAxisValueFormatter {
    private String[] hm;

    public CustomYAxisFormatter(String[] codes) {
        this.hm = codes;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int i = (int) (value / 10);
        return hm[i];
    }
}
