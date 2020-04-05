package com.kashipro.luter.luter.dashboard_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.kashipro.luter.luter.R;
import com.kashipro.luter.luter.local.LocalVariables;
import com.kashipro.luter.luter.util.UpdateDB;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.concurrent.TimeUnit;

public class MoneyFragment extends Fragment {

    private TextView wallet_field;
    private CircularProgressBar circularProgressBar;
    private TextView money_percentage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.money_fragment, container, false);

        wallet_field = view.findViewById(R.id.wallet_field);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        money_percentage = view.findViewById(R.id.money_percentage);

        UpdateDB.initializeWallet(wallet_field);

        return view;
    }


}
