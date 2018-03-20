package com.CircleProgress.Circle_progress_bar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    private CircleProgressBar CircleBar;
    private Button add;
    private Button reduce;
    private int Progress = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleBar = (CircleProgressBar) findViewById(R.id.CircleBar);
        add = (Button) findViewById(R.id.add);
        reduce = (Button) findViewById(R.id.reduce);
        CircleBar.setProgress(Progress);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Progress += 10;
                if (Progress>=100){
                    Progress=100;
                }
                CircleBar.setProgress(Progress, true);
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Progress -= 10;
                if (Progress<=0){
                    Progress=0;
                }
                CircleBar.setProgress(Progress, true);

            }
        });
    }
}
