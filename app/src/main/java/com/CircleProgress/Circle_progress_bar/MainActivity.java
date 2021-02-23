package com.CircleProgress.Circle_progress_bar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private CircleProgressBar circleBar;
    private Button add;
    private Button reduce;
    private int Progress = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleBar = (CircleProgressBar) findViewById(R.id.CircleBar);
        add = (Button) findViewById(R.id.add);
        reduce = (Button) findViewById(R.id.reduce);
        int[] ints = {Color.parseColor("#27B197"), Color.parseColor("#00A6D5")};
        //设置圆环的进度颜色(渐变)
        circleBar.setColorArray(ints);
        //设置圆环周围的阴影颜色
        circleBar.setShadowColor(Color.parseColor("#E2E0DE"));
        //设置默认进度
        circleBar.setProgress(Progress);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Progress += 10;
                if (Progress>=100){
                    Progress=100;
                }
                circleBar.setProgress(Progress, true);
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Progress -= 10;
                if (Progress<=0){
                    Progress=0;
                }
                circleBar.setProgress(Progress, true);

            }
        });
    }
}
