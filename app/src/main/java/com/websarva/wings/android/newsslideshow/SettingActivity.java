package com.websarva.wings.android.newsslideshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.view.*;

public class SettingActivity extends AppCompatActivity {

    public static int TIME = 10 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    public void ok_click(View view){
        EditText edittimeText = (EditText) findViewById(R.id.time_edit);

        int time = Integer.parseInt(edittimeText.getText().toString());
        setTime(time);
    }

    private void setTime(int time) {
        if(time <= 3) {
            TIME = 3 * 1000;
        }
        TIME = time * 1000;
    }

    public void back_click(View view) {
        Intent intent = new Intent(this, SlideShowActivity.class);
        startActivity(intent);
    }
}
