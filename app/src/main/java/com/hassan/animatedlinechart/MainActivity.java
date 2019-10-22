package com.hassan.animatedlinechart;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gadnimation.animatedlinecharts.AnimatedLineChart;
import com.gadnimation.animatedlinecharts.data.DataSet;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        final AnimatedLineChart chart = findViewById(R.id.charView);
        ArrayList<DataSet> points = new ArrayList<>();
        points.add(new DataSet(40,"2016",Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.cool), 50, 50, true)));
        points.add(new DataSet(50,"2017",Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.loveempji), 50, 50, true)));
        points.add(new DataSet(20,"2018",Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.dead2), 50, 50, true)));
        points.add(new DataSet(60,"2019",Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.cilb), 50, 50, true)));
        points.add(new DataSet(70,"2020",Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.loveempji), 50, 50, true)));

        chart.setData(points);
        chart.addImageBorder(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chart.setLineColor(Color.BLUE);
                chart.setLegendsFrameColor(Color.BLUE);
            }
        });


    }
}
