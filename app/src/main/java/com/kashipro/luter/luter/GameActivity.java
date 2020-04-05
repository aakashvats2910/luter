package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kashipro.luter.luter.util.UpdateDB;

import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dashboard_drawer_layout;
    private NavigationView navigationView;
    private ImageView drawer_controller;
    private NavigationView nav_view;
    private TextView game_header;
    private LinearLayout option_a, option_b, option_c, option_d;
    private ImageView imagea, imageb, imagec, imaged;
    private ImageView info_about_game;
    private ProgressBar loading_game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setNavigationViewListener();

        nav_view = findViewById(R.id.nav_view);
        dashboard_drawer_layout = findViewById(R.id.dashboard_drawer_layout);
        drawer_controller = findViewById(R.id.drawer_controller);
        navigationView = findViewById(R.id.nav_view);
        game_header = findViewById(R.id.game_header);
        option_a = findViewById(R.id.option_a);
        option_b = findViewById(R.id.option_b);
        option_c = findViewById(R.id.option_c);
        option_d = findViewById(R.id.option_d);
        info_about_game = findViewById(R.id.info_about_game);
        imagea = findViewById(R.id.imagea);
        imageb = findViewById(R.id.imageb);
        imageb = findViewById(R.id.imagec);
        imageb = findViewById(R.id.imaged);
        loading_game = findViewById(R.id.loading_game);

        loading_game.setVisibility(View.GONE);

        drawer_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard_drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        // Selected first item by default
        nav_view.getMenu().getItem(2).setChecked(true);

        // Business Logic of the Activity.
        info_about_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, InfoActivity.class);
                intent.putExtra("work","playgame");
                startActivity(intent);
            }
        });

        game_header.setText(Html.fromHtml("<h5>Choose you lucky option!</h5>" +
                "<h6>The card selected by least users will be winning card.!</h6>"));

        option_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeOptions("A",v);
                startLoading();
            }
        });

        option_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeOptions("B", v);
                startLoading();
            }
        });

        option_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeOptions("C", v);
                startLoading();
            }
        });

        option_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeOptions("D", v);
                startLoading();
            }
        });

    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {

            case R.id.ads_item: {
                Intent i = new Intent(GameActivity.this, AdsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.goup, R.anim.godown);
                break;
            }

            case R.id.money_earned_item: {
                Intent i = new Intent(GameActivity.this, MoneyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.goup, R.anim.godown);
                break;
            }

            case R.id.log_out_item: {
                logOutAndClearStack();
                overridePendingTransition(R.anim.goup, R.anim.godown);
                break;
            }

        }
        //close navigation drawer
        dashboard_drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOutAndClearStack() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void initializeOptions(String option, final View view) {
        Map<String, Object> map = new HashMap<>();
        map.put("SELECTED_OPTION", option);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("options").document(FirebaseAuth.getInstance().getUid())
                .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar.make(view, "Option selected successfully!", 2000).show();
                stopLoading();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(view, "Selected options can not be changed!", 2000).show();
            }
        });
    }

    private void startLoading() {
        loading_game.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        loading_game.setVisibility(View.GONE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        AnimationDrawable animation = (AnimationDrawable) info_about_game.getDrawable();
        animation.stop();
        animation.selectDrawable(0);
        animation.start();
    }
}