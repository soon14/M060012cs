
package com.cg.mrice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.cg.mrice.R;
import com.cg.mrice.data.ColorData;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.model.LotteryGamePeriodXml;
import com.cg.mrice.utils.XMLHelper;
import com.cg.mrice.view.ChartActivity;
import com.cg.mrice.view.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvertedLineChartActivity extends ChartActivity implements
        OnChartValueSelectedListener {

    private LineChart mChart;
    private List<LotteryGamePeriodXml> data;
    private String name = "时时彩";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        ButterKnife.bind(this);
        toolbar.setTitle("走势对比");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name = getIntent().getStringExtra("name");
        if ("时时彩".equals(name)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
        } else if ("七乐彩".equals(name)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QLC, "period");
        } else if ("七星彩".equals(name)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_QXC, "period");
        } else if ("排列三".equals(name)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL3, "period");
        } else if ("排列五".equals(name)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_PL5, "period");
        } else if ("快三".equals(name)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_K3, "period");
        } else if ("11选5".equals(name)) {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_D11, "period");
        } else {
            data = XMLHelper.getInstance().getList(LotteryGamePeriodXml.class, CommonData.DATA_SSC, "period");
        }

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
        // no description text
        Description description = new Description();
        description.setText(name);
        mChart.setDescription(description);
        mChart.getDescription().setEnabled(true);
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setPinchZoom(true);
        mChart.zoom(3f, 1, 0, 0);
        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        // add data
        setData();

        // // restrain the maximum scale-out factor
        // mChart.setScaleMinima(3f, 3f);
        //
        // // center the view to a specific position inside the chart
        // mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(LegendForm.LINE);
        l.setWordWrapEnabled(true);

        // dont forget to refresh the drawing
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }

    private void setData() {
        List<ILineDataSet> sets = new ArrayList<>();
        ArrayList<Entry> entries = null;
        LineDataSet set = null;
        for (int b = 0; b < 10; b++) {
            entries = new ArrayList<Entry>();

            LotteryGamePeriodXml xml = data.get(b);
            String[] codes = xml.getAwardNo().replace(":", " ").split(" ");
            int count = codes.length;

            for (int i = 0; i < count; i++) {
                entries.add(new Entry(i, Integer.parseInt(codes[i])));
            }
            set = new LineDataSet(entries, "第" + data.get(b).getPeriodName() + "期");
            set.setCircleColor(ColorData.Colors[b]);
            set.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    DecimalFormat df = new DecimalFormat("#");
                    return "" + df.format(value);
                }
            });
            set.setValueTextSize(12);
            set.setValueTextColor(Color.BLACK);
            set.setLineWidth(2.2f);
            set.setCircleRadius(3f);
            set.setColors(ColorData.Colors[b]);
            set.setDrawCircleHole(false);
            sets.add(set);
        }
        LineData data1 = new LineData(sets);
        // set data
        mChart.setData(data1);
    }
}
