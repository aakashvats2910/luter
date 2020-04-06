package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class HowWeWorkActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dashboard_drawer_layout;
    private ImageView drawer_controller;
    private NavigationView nav_view;
    private TextView text_1;
    private ImageView twitter_button, instagram_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_we_work);
        setNavigationViewListener();

        nav_view = findViewById(R.id.nav_view);
        dashboard_drawer_layout = findViewById(R.id.dashboard_drawer_layout);
        drawer_controller = findViewById(R.id.drawer_controller);
        text_1 = findViewById(R.id.text_1);

        twitter_button = findViewById(R.id.twitter_button);
        instagram_button = findViewById(R.id.instagram_button);

        // As per now instagram is hidden!
        instagram_button.setVisibility(View.GONE);

        drawer_controller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboard_drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        // Selected first item by default
        nav_view.getMenu().getItem(2).setChecked(true);

        // Business Logic.
        text_1.setText(Html.fromHtml("Yeah! Its a simple idea developed to help <b>Poor</b> & <b>Needy</b> around us."));


        // TODO linking to twitter and instagram left.
        twitter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "Luter03362017")));
                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + "Luter03362017")));
                }
            }
        });

        instagram_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/aakashvats2910");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/aakashvats2910")));
                }
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
                Intent i = new Intent(HowWeWorkActivity.this, AdsActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.goup, R.anim.godown);
                break;
            }

            case R.id.money_earned_item: {
                Intent i = new Intent(HowWeWorkActivity.this, MoneyActivity.class);
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
        Intent intent = new Intent(HowWeWorkActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}