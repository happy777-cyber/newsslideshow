package com.websarva.wings.android.newsslideshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.Timer;

public class SlideShowActivity extends AppCompatActivity {

    private WebView mMyWebView;
    private SlideShowTimer mSlideShow;
    private Timer mTimer;
    private Boolean mSlideShowFlag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        mMyWebView = findViewById(R.id.my_web_view);
        mMyWebView.getSettings().setJavaScriptEnabled(true);

        mMyWebView.setWebViewClient(new WebViewClient(){
            //外部ブラウザの起動を防ぐ
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
                view.loadUrl(String.valueOf(request.getUrl()));
                return true;
            }
        });


        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        startSlideShow();



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restart:
                Toast.makeText(getApplicationContext() ,getApplicationContext().getResources().getString(R.string.restart_event) , Toast.LENGTH_SHORT).show();

                stopSlideShow();
                startSlideShow();

                return true;

            case R.id.action_stop:
                Toast.makeText(getApplicationContext() ,getApplicationContext().getResources().getString(R.string.stop_event) , Toast.LENGTH_SHORT).show();

                stopSlideShow();
                return true;

            case R.id.action_go_setting:
                goSettingActivity();
                return true;



            default:
                return super.onOptionsItemSelected(item);


        }
    }

    private void startSlideShow() {
        mSlideShowFlag = true;
        mTimer = new Timer();
        mSlideShow = new SlideShowTimer(getApplicationContext(),mMyWebView);
        mTimer.scheduleAtFixedRate(mSlideShow, 0, SettingActivity.TIME);
    }

    private void stopSlideShow() {
        mSlideShowFlag = false;
        mTimer.cancel();
    }

    private void goSettingActivity() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        finish();
    }



}
