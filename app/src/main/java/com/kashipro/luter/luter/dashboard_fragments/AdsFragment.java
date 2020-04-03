package com.kashipro.luter.luter.dashboard_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kashipro.luter.luter.BannerAdActivity;
import com.kashipro.luter.luter.IntersAdActivity;
import com.kashipro.luter.luter.R;
import com.kashipro.luter.luter.VideoAdActivity;

public class AdsFragment extends Fragment {

    private LinearLayout image_ad_button, video_ad_button, banner_ad_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ads_fragment, container, false);

        image_ad_button = view.findViewById(R.id.image_ad_button);
        video_ad_button = view.findViewById(R.id.video_ad_button);
        banner_ad_button = view.findViewById(R.id.banner_ad_button);

        video_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VideoAdActivity.class));
            }
        });

        banner_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), BannerAdActivity.class));
            }
        });

        image_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), IntersAdActivity.class));
            }
        });

        return view;
    }
}
