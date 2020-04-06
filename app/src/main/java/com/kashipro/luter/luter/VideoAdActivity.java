package com.kashipro.luter.luter;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

import com.google.android.gms.ads.reward.RewardedVideoAd;

import com.google.android.gms.ads.reward.RewardedVideoAdListener;


import com.google.firebase.firestore.FirebaseFirestore;

import com.kashipro.luter.luter.util.UpdateDB;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class VideoAdActivity extends AppCompatActivity {


    private Button watch_ad_button;
    private FirebaseFirestore db;
    private TextView info_view;
    private TextView ads_viewed_video;
    private TextView ads_clicked_video;
    private ImageView back_video;
    private ProgressBar loading_video;
    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_ad);

        db = FirebaseFirestore.getInstance();
        watch_ad_button = findViewById(R.id.watch_ad_button);
        info_view = findViewById(R.id.info_view);
        ads_clicked_video = findViewById(R.id.ads_clicked_video);
        ads_viewed_video = findViewById(R.id.ads_viewed_video);
        back_video = findViewById(R.id.back_video);
        loading_video = findViewById(R.id.loading_video);

        back_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                watch_ad_button.setEnabled(true);
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

            }

            @Override
            public void onRewarded(com.google.android.gms.ads.reward.RewardItem rewardItem) {
                UpdateDB.updateDB("VIDEO_VIEW", ads_viewed_video);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                UpdateDB.updateDB("VIDEO_CLICK", ads_viewed_video);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });

        loadRewardedVideoAd();

        UpdateDB.initializeClicksFromDB("VIDEO_CLICK", ads_clicked_video, loading_video);
        UpdateDB.initializeClicksFromDB("VIDEO_VIEW", ads_viewed_video, loading_video);

        info_view.setText("\u24d8 If button is disabled, try after some time!");

        // ca-app-pub-7862696162151523/7956688247

        watch_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-7862696162151523/7956688247",
                new AdRequest.Builder().build());
    }
}
