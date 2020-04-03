package com.kashipro.luter.luter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout dashboard_drawer_layout;
    private Button open_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard_drawer_layout = findViewById(R.id.dashboard_drawer_layout);
        open_button = findViewById(R.id.open_button);

        open_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard_drawer_layout.openDrawer(GravityCompat.START);
            }
        });
    }
}
