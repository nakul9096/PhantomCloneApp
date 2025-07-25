package com.example.myphantom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FeedFragment extends Fragment {

    private LineChart chart;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ImageView notification_bell = view.findViewById(R.id.notification_bell);
        ViewUtils.addPressEffect(notification_bell);
        notification_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), Notification.class);
                requireActivity().startActivity(intent);
            }
        });
        chart = view.findViewById(R.id.coin_price_chart);
        setupChart();
        loadChartData();
        return view;
    }

    private void setupChart() {
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.parseColor("#AAAAAA"));
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            @Override
            public String getFormattedValue(float value) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 9 + (int) value);
                calendar.set(Calendar.MINUTE, 0);
                return format.format(calendar.getTime());
            }
        });
        xAxis.setLabelCount(5, true);

        YAxis leftYAxis = chart.getAxisLeft();
        leftYAxis.setDrawGridLines(true);
        leftYAxis.setGridColor(Color.parseColor("#282828"));
        leftYAxis.setDrawAxisLine(false);
        leftYAxis.setTextColor(Color.parseColor("#AAAAAA"));
        leftYAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftYAxis.setLabelCount(4, true);

        chart.getAxisRight().setEnabled(false);

        chart.getLegend().setEnabled(false);

        chart.animateX(1000);
    }

    private void loadChartData() {
        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0f, 0.28f));
        entries.add(new Entry(1f, 0.29f));
        entries.add(new Entry(2f, 0.31f));
        entries.add(new Entry(3f, 0.30f));
        entries.add(new Entry(4f, 0.32f));
        entries.add(new Entry(5f, 0.295f));
        entries.add(new Entry(6f, 0.298f));
        entries.add(new Entry(7f, 0.305f));
        entries.add(new Entry(8f, 0.29f));

        LineDataSet dataSet = new LineDataSet(entries, "Coin Price");
        dataSet.setColor(Color.parseColor("#7ED957"));
        dataSet.setCircleColor(Color.parseColor("#7ED957"));
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setLineWidth(2f);

        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#7ED957"));
        dataSet.setFillAlpha(60);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}