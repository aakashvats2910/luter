package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kashipro.luter.luter.local.LocalVariables;

import java.util.HashMap;
import java.util.Map;

public class GetInforActivity extends AppCompatActivity {

    private ImageButton get_infor_next_button;
    private EditText name_field;

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
                    Toast.makeText(getApplicationContext(), "Nice Work! Logging you in!",Toast.LENGTH_LONG).show();
                    setNameInDB(name);
                    getPhoneNumber();
                    startActivity(new Intent(GetInforActivity.this, InstructionsActivity.class));
                }
            }
        });
    }

    // Writes the name in database and after the name is written successfully then it proceeds to
    // the next activity of instructions.
    private void setNameInDB(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("NAME",name);
        if (FirebaseAuth.getInstance().getUid() != null)
            FirebaseFirestore.getInstance().collection("userdata")
                    .document(FirebaseAuth.getInstance().getUid())
                    .set(map, SetOptions.merge())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    startActivity(new Intent(GetInforActivity.this, InstructionsActivity.class));
                }
            });
    }

    // Take phone number and store it in Shared preferences as well as use the helper method
    // uploadPhoneNumberToDB to upload it to the database.
    private void getPhoneNumber() {
        String number = LocalVariables.getResponse().getPhoneNumber();
        LocalVariables.setDefaults("number", number, getApplicationContext());
        uploadPhoneNumberToDB(number);
    }

    // Upload the data to firebase.
    private void uploadPhoneNumberToDB(String number) {
        Map<String, Object> map = new HashMap<>();
        map.put("NUMBER",number);
        FirebaseFirestore.getInstance().collection("userdata")
                .document(FirebaseAuth.getInstance().getUid())
                .set(map, SetOptions.merge());
    }

}
