package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kashipro.luter.luter.local.LocalVariables;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetInforActivity extends AppCompatActivity {

    private ImageButton get_infor_next_button;
    private EditText name_field;
    private final int RC_SIGN_IN = 1;

    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.PhoneBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_infor);

        get_infor_next_button = findViewById(R.id.get_infor_next_button);
        name_field = findViewById(R.id.name_field);

        get_infor_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_field.getText().toString().trim();
                if (name.isEmpty()) {
                    name_field.setError("Please enter name!");
                } else if (name.length() > 20) {
                    name_field.setError("Max 20 characters allowed!");
                } else {
                    setLocalName(name);
                    startFireUi();
                }
            }
        });
    }

    private void setLocalName(String name) {
        LocalVariables.setDefaults("name",name, getApplicationContext());
    }

    private void startFireUi() {
        startActivityForResult(
            AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
            RC_SIGN_IN
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                LocalVariables.setDefaults("number",user.getPhoneNumber(), getApplicationContext());
                LocalVariables.setResponse(response);
                startActivity(new Intent(GetInforActivity.this, InstructionsActivity.class));
            } else {
                // Unsuccessful sign in
            }
        }
    }

}
