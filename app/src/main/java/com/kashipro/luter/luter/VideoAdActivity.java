package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class VideoAdActivity extends AppCompatActivity {

    private RewardedAd rewardedAd;
    private Button button_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_ad);

        button_test = findViewById(R.id.button_test);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });

        rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        // ca-app-pub-7862696162151523/7956688247

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                System.out.println("()()()() AD LOADED");
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                System.out.println("()()()() AD LOADED FAILED");
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        button_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardedAd.isLoaded()) {
                    Activity activityContext = VideoAdActivity.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                            System.out.println("()()()() REWARD AD LOADED");
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            // Ad closed.
                            System.out.println("()()()() REWARD AD CLOSED");
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            // User earned reward.
                            System.out.println("()()()() REWARD EARNED");
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {
                            // Ad failed to display.
                            System.out.println("()()()() REWARD AD FAILED TO SHOW");
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }
        });
    }
}
