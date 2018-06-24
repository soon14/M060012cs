package com.cg.mrice.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by app on 2018/4/16.
 */
public abstract class ChartActivity extends AppCompatActivity {
    protected String[] mMonths = new String[]{
            "No.180430", "No.180429", "No.180428", "No.180427", "No.180426", "No.180425", "No.180424 ", "No.180423", "No.180422", "No.180421", "No.180420", "No.180419"
    };

    protected String[] mParties = new String[]{
            "号码 0", "号码 1", "号码 2", "号码 3", "号码 4", "号码 5", "号码 6", "号码 7",
            "号码 8", "号码 9", "号码 10", "号码 11", "号码12", "号码 13", "号码 14", "号码 15",
            "号码 16", "号码 17", "号码 18", "号码 19", "号码 20", "号码 21", "号码 22", "号码 23",
            "号码 24", "号码 25"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }
}
