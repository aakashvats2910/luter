package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kashipro.luter.luter.local.LocalVariables;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout main_activity_main;
    private LinearLayout start_button;
    private TextView starter_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        starter_view = findViewById(R.id.starter_view);
        main_activity_main = findViewById(R.id.main_activity_main);
        start_button = findViewById(R.id.start_button);

        starter_view.setText(Html.fromHtml("<b><h1>Luter:</b></h1><h6>An easy way to earn money</h6>"));

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        if (FirebaseAuth.getInstance().getUid() != null) {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
        }

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GetInforActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
