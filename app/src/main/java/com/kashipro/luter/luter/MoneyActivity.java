package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kashipro.luter.luter.util.UpdateDB;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

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

        circularProgressBar.setProgressMax(100);
        circularProgressBar.setIndeterminateMode(true);

        circularProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "We need to reach 100$ before we can withdraw! So, keep up with us!", 3000).show();
            }
        });

        initializeWallet(wallet_field);
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
                overridePendingTransition(R.anim.goup, R.anim.godown);
                break;
            }
            case R.id.work_item: {
                Intent i = new Intent(MoneyActivity.this, HowWeWorkActivity.class);
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
        Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void initializeWallet(final TextView textView) {
        final String HERE = "WALLET";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("money").document("money_")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        if (snapshot.contains(HERE)) {
                            long money = (long) snapshot.get(HERE);
                            long percentage = (long) snapshot.get("PERCENTAGE");
                            textView.setText("" + money + " \u20B9");
                            circularProgressBar.setIndeterminateMode(false);
                            circularProgressBar.setProgressWithAnimation(percentage, 1000L);
                            money_percentage.setText("" + percentage + "%");
                        } else {
                            textView.setText("0 \u20B9");
                            circularProgressBar.setIndeterminateMode(false);
                            circularProgressBar.setProgressWithAnimation(0, 1000L);
                        }
                    }
                }
            }
        });
    }
}
