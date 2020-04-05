package com.kashipro.luter.luter.dashboard_fragments;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kashipro.luter.luter.InfoActivity;
import com.kashipro.luter.luter.R;
import com.kashipro.luter.luter.util.UpdateDB;

public class GameFragment extends Fragment {

    private TextView game_header;
    private LinearLayout option_a, option_b, option_c;
//    private ImageView info_about_game;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);

        game_header = view.findViewById(R.id.game_header);
        option_a = view.findViewById(R.id.option_a);
        option_b = view.findViewById(R.id.option_b);
        option_c = view.findViewById(R.id.option_c);
        final ImageView info_about_game = view.findViewById(R.id.info_about_game);

        info_about_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra("work","playgame");
                startActivity(intent);
            }
        });

        System.out.println("()()()() ");

        try {
            AnimationDrawable animation = (AnimationDrawable) info_about_game.getDrawable();
            animation.stop();
            animation.selectDrawable(0);
            animation.start();
        } catch (Exception e) {
            System.out.println("()()()() " + e.toString());
        }

        game_header.setText(Html.fromHtml("<h3>Choose you lucky option!</h3>"));

        option_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                UpdateDB.initializeOptions("A");
            }
        });

        option_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDB.initializeOptions("B");
            }
        });

        option_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateDB.initializeOptions("C");
            }
        });

        return view;
    }
}
