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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kashipro.luter.luter.util.UpdateDB;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class VideoAdActivity extends AppCompatActivity {

    private RewardedAd rewardedAd;
    private Button watch_ad_button;
    private FirebaseFirestore db;
    private TextView info_view;
    private TextView ads_viewed_video;
    private TextView ads_clicked_video;
    private ImageView back_video;
    private ProgressBar loading_video;

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

        UpdateDB.initializeClicksFromDB("VIDEO_CLICK", ads_clicked_video, loading_video);
        UpdateDB.initializeClicksFromDB("VIDEO_VIEW", ads_viewed_video, loading_video);

        info_view.setText("\u24d8 If button is disabled, try after some time!");

        rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        // ca-app-pub-7862696162151523/7956688247

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                System.out.println("()()()() AD LOADED");
                watch_ad_button.setEnabled(true);
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                System.out.println("()()()() AD LOADED FAILED");
                Toast.makeText(getApplicationContext(), "Failed to load Ad!", Toast.LENGTH_LONG).show();
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);

        watch_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_ad_button.setEnabled(false);
                if (rewardedAd.isLoaded()) {
                    Activity activityContext = VideoAdActivity.this;
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                            System.out.println("()()()() REWARD AD LOADED");
                            UpdateDB.updateDB("VIDEO_VIEW", ads_viewed_video);
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
                            UpdateDB.updateDB("VIDEO_CLICK", ads_clicked_video);
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

    private void updateDB() {
        String HERE = "VIDEO_CLICKED";
        db.collection("userdata").document(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        if (snapshot.contains("VIDEO_CLICKED")) {
                            long clicked = (long)snapshot.get("VIDEO_CLICKED");
                            Map<String, Object> map = new HashMap<>();
                            map.put("VIDEO_CLICKED", clicked+1);
                            db.collection("userdata")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update(map);
                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put("VIDEO_CLICKED", 1);
                            db.collection("userdata").
                                    document(FirebaseAuth.getInstance().getUid()).set(map, SetOptions.merge());
                        }
                    }
                }
            }
        });
    }
}
