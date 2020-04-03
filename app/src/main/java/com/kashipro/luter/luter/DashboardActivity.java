package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.kashipro.luter.luter.dashboard_fragments.AdsFragment;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout dashboard_drawer_layout;
    private NavigationView navigationView;
    private ImageView drawer_controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setNavigationViewListener();

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
                Toast.makeText(getApplicationContext(), "Ads! Clicked!", Toast.LENGTH_LONG).show();
                System.out.println("()()()() CLICKED");
                break;
            }

        }
        //close navigation drawer
        dashboard_drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }
}
