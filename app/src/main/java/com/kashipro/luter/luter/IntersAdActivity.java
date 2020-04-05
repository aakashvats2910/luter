package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kashipro.luter.luter.util.UpdateDB;

import java.util.HashMap;
import java.util.Map;

public class IntersAdActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    private Button watch_image_ad_button;
    private TextView info_view;
    private TextView ads_clicked_inters;
    private TextView ads_viewed_inters;
    private ImageView back_inters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inters_ad);

        watch_image_ad_button = findViewById(R.id.watch_image_ad_button);
        ads_clicked_inters = findViewById(R.id.ads_clicked_inters);
        ads_viewed_inters = findViewById(R.id.ads_viewed_inters);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        info_view = findViewById(R.id.info_view_inters);
        back_inters = findViewById(R.id.back_inters);

        back_inters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        info_view.setText("\u24d8 If button is disabled, try after some time!");

        watch_image_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_image_ad_button.setEnabled(false);
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        UpdateDB.initializeClicksFromDB("INTERS_CLICK", ads_clicked_inters);
        UpdateDB.initializeClicksFromDB("INTERS_VIEW", ads_viewed_inters);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                watch_image_ad_button.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                UpdateDB.updateDB("INTERS_VIEW", ads_viewed_inters);
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                UpdateDB.updateDB("INTERS_CLICK", ads_clicked_inters);
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });
    }

}
