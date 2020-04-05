package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kashipro.luter.luter.util.UpdateDB;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.concurrent.TimeUnit;

public class MoneyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dashboard_drawer_layout;
    private NavigationView navigationView;
    private ImageView drawer_controller;
    private NavigationView nav_view;
    private TextView wallet_field;
    private CircularProgressBar circularProgressBar;
    private TextView money_percentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        setNavigationViewListener();

        nav_view = findViewById(R.id.nav_view);
        dashboard_drawer_layout = findViewById(R.id.dashboard_drawer_layout);
        drawer_controller = findViewById(R.id.drawer_controller);
        navigationView = findViewById(R.id.nav_view);

        drawer_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard_drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        // Selected first item by default
        nav_view.getMenu().getItem(1).setChecked(true);

        // Business Logic
        wallet_field = findViewById(R.id.wallet_field);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        money_percentage = findViewById(R.id.money_percentage);

        initializeWallet(wallet_field);

        // Business logic of the activity
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
                Intent i = new Intent(MoneyActivity.this, AdsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            }

            case R.id.game_item: {
                Intent i = new Intent(MoneyActivity.this, GameActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
            }

            case R.id.log_out_item: {
                logOutAndClearStack();
                break;
            }

        }
        //close navigation drawer
        dashboard_drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOutAndClearStack() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void showProgressSlowly(final long value) {
        new Thread() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long current = 0;
                while (current <= value) {
                    circularProgressBar.setProgress(current);
                    current++;
                    try {
                        TimeUnit.MILLISECONDS.sleep(12);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static void initializeWallet(final TextView textView) {
        final String HERE = "WALLET";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("money").document(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        if (snapshot.contains(HERE)) {
                            long clicked = (long) snapshot.get(HERE);
                            textView.setText("" + clicked + " \u20B9");
                        } else {
                            textView.setText("0 \u20B9");
                        }
                    }
                }
            }
        });
    }
}
