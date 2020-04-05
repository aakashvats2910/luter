package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kashipro.luter.luter.dashboard_fragments.AdsFragment;
import com.kashipro.luter.luter.dashboard_fragments.GameFragment;
import com.kashipro.luter.luter.dashboard_fragments.MoneyFragment;
import com.kashipro.luter.luter.local.LocalVariables;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.concurrent.TimeUnit;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout dashboard_drawer_layout;
    private NavigationView navigationView;
    private ImageView drawer_controller;
    private NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setNavigationViewListener();

        nav_view = findViewById(R.id.nav_view);
        dashboard_drawer_layout = findViewById(R.id.dashboard_drawer_layout);
        drawer_controller = findViewById(R.id.drawer_controller);
        navigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frames_frag, new AdsFragment()).commit();

        drawer_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard_drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        // Selected first item by default
        nav_view.getMenu().getItem(0).setChecked(true);

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
                getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frames_frag, new AdsFragment()).commit();
                break;
            }

            case R.id.money_earned_item: {
                getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frames_frag, new MoneyFragment()).commit();
                break;
            }

            case R.id.game_item: {
                getSupportFragmentManager().beginTransaction().replace(R.id.dashboard_frames_frag, new GameFragment()).commit();
                break;
            }

            case R.id.log_out_item: {
                logOutAndClearStack();
            }

        }
        //close navigation drawer
        dashboard_drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOutAndClearStack() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
