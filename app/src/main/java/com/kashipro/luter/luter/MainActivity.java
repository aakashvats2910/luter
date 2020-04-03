package com.kashipro.luter.luter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kashipro.luter.luter.local.LocalVariables;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 1;
    private RelativeLayout main_activity_main;

    // Choose authentication providers
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.PhoneBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_activity_main = findViewById(R.id.main_activity_main);

        if (FirebaseAuth.getInstance().getUid() != null) {
            startActivity(new Intent(MainActivity.this, GetInforActivity.class));
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                LocalVariables.setResponse(response);
                startActivity(new Intent(MainActivity.this, GetInforActivity.class));
            } else {
                // Unsuccessful sign in
                Snackbar.make(main_activity_main, "Unable to sign in!", 2000).show();
            }
        }
    }
}
