package com.websarva.wings.android.newsslideshow;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.TimerTask;

public class SlideShowTimer extends TimerTask {

    Context mContext;
    WebView mWebView;

    public SlideShowTimer(Context context,WebView webView) {
        mContext = context;
        mWebView = webView;
    }


    private final Handler handler = new Handler();


    private int count = 0;
    private String[] pickUpNewsNumber;
    private String pickUpNewsUrl = "";

    @Override
    public void run() {
        // 本処理を繰り返す

        if (count == 0) {
            //初回のみ、トップページからpickupニュース番号を配列にして返す
            pickUpNewsNumber = getPickUpNewsNumber();
        }
        //pickupニュースの詳細Urlを取得する処理
        pickUpNewsUrl = getPickUpNewsUrl(pickUpNewsNumber[count]);


        handler.post(new Runnable() {
            @Override
            public void run() {
                // UIスレッド
                //詳細Urlが取得できなかった場合は、表示せず、次の表示を待つ
                if(!pickUpNewsUrl.equals("")) {
                    mWebView.loadUrl(pickUpNewsUrl);
                }
                count++;

                if (count >= pickUpNewsNumber.length) {
                    // 取得したURL全て表示したら、初回に戻る
                    count = 0;
                }

            }
        });

    }

    private String[] getPickUpNewsNumber() {
        //YahooニューストップページからpickupニュースURL一覧を配列で返す
        String[] urlString = null;
        try {
            Document document = Jsoup.connect(mContext.getString(R.string.top_news_url)).get();
            Elements mainTopicList = document.getElementsByClass("topicsListItem").select("[href]");

            Log.v("getPickUpNewsId", mainTopicList.toString());

            urlString = new String[mainTopicList.size()];
            String[] mainString = mainTopicList.toString().split("<a href=\"https://news.yahoo.co.jp/pickup/", 0);
            for (int i = 1; i <= mainTopicList.size(); i++) {
                urlString[i - 1] = mainString[i].split("\"")[0];
            }
        } catch (IOException ex) {
            Log.d("getPickUpNewsNumber", "IOException");
        }
        return urlString;

    }

    private String getPickUpNewsUrl(String pickupNewsNumber) {
        //pickupニュース番号からpickUpNewsのURLを返す処理
        String pickupNewsUrl = "";
        try {

            Document document = Jsoup.connect(mContext.getString(R.string.pick_up_news_url) + pickupNewsNumber).get();
            pickupNewsUrl = document.getElementsByClass("pickupMain_inner").toString().split("pickupMain_detailLink\"><a href=\"")[1].split("\" data-ylk=")[0];
            //            Elements pickUpNewsUrlElements = document.getElementsByClass("pickupMain_detailLink").select("[href]");

        } catch (IOException ex) {
            Log.d("getPickUpNewsUrl", "IOException");
        }

        return pickupNewsUrl;

    }

}
