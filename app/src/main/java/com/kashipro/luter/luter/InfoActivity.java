package com.kashipro.luter.luter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private TextView info_header, rules_view;
    private ImageView back_info, ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        back_info = findViewById(R.id.back_info);
        info_header = findViewById(R.id.info_header);
        rules_view = findViewById(R.id.rules_view);
        ball = findViewById(R.id.ball_icon);

        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("()()()() ()()()()");
                Drawable drawable = getDrawable(R.drawable.ball);
                AnimationDrawable animation = (AnimationDrawable) drawable;
//                animation.stop();
                animation.selectDrawable(0);
                animation.start();
            }
        });

        back_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        info_header.setText(Html.fromHtml("<h3>How to?</h3>"));

        Intent intent = getIntent();
        String work = intent.getStringExtra("work");
        if (work.equals("playgame")) {
            rules_view.setText(Html.fromHtml("" +
                    "<h4>How to play game?</h4><br/>" +
                    "<h6>- The game is simple you just have to choose one card out of the three given A,B,C.<br/>" +
                    "- Once the card is selected you can not change your selection.</h6><br/>" +
                    "<h4>How is the winner decided?<h4><br/>" +
                    "<h6>- To be honest with you! You can be winner by your luck.<br/>" +
                    "- To be winner you just have to guess which card would be lest selected by the other people<br/>" +
                    "- The card having least selections will be declared as winning card.<br/>" +
                    "- So, it means you have 33.34% chance of winning.</h6>"));
        }

    }
}
