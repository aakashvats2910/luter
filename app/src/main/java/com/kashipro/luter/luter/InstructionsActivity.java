package com.kashipro.luter.luter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.kashipro.luter.luter.fragments.FirstInstruction;
import com.kashipro.luter.luter.fragments.SecondInstruction;
import com.kashipro.luter.luter.fragments.ThirdInstruction;
import com.kashipro.luter.luter.local.LocalVariables;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InstructionsActivity extends AppCompatActivity {

    private int INSTRUCTION = 0;

    private FrameLayout instructions_frame;
    private Fragment selectedFragment = null;
    private TextView skip_instruction_button;
    private TextView next_instruction_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        instructions_frame = findViewById(R.id.instructions_frame);
        skip_instruction_button = findViewById(R.id.skip_instruction_button);
        next_instruction_button = findViewById(R.id.next_instruction_button);

        setNameInDB();
        setNumberInDB();

        getSupportFragmentManager().beginTransaction().replace(R.id.instructions_frame, new FirstInstruction()).commit();

        next_instruction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTRUCTION++;
                switch (INSTRUCTION) {
                    case 0:
                        selectedFragment = new FirstInstruction();
                        break;
                    case 1:
                        selectedFragment = new SecondInstruction();
                        break;
                    case 2:
                        selectedFragment = new ThirdInstruction();
                        break;
                    default:
                        ;// TODO pass onto next activity.;
                        System.out.println("DONE!");
                        break;
                }

                if (INSTRUCTION > 2) {
                    INSTRUCTION = 2;
                    transferToDashboard();
                    // TODO start next activity!
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.instructions_frame, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        skip_instruction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTRUCTION--;
                if (INSTRUCTION < 0) INSTRUCTION = 0;
                switch (INSTRUCTION) {
                    case 0:
                        selectedFragment = new FirstInstruction();
                        break;
                    case 1:
                        selectedFragment = new SecondInstruction();
                        break;
                    case 2:
                        selectedFragment = new ThirdInstruction();
                        break;
                    default:
                        ;// TODO pass onto next activity.;
                        finishAffinity();
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.instructions_frame, selectedFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    // Writes the name in database and after the name is written successfully then it proceeds to
    // the next activity of instructions.
    private void setNameInDB() {
        String name = LocalVariables.getDefaults("name", getApplicationContext());
        if (name.isEmpty()) return;
        Map<String, Object> map = new HashMap<>();
        map.put("NAME",name);
        if (FirebaseAuth.getInstance().getUid() != null)
            FirebaseFirestore.getInstance().collection("userdata")
                    .document(FirebaseAuth.getInstance().getUid())
                    .set(map, SetOptions.merge());
    }

    // Take phone number and store it in Shared preferences as well as use the helper method
    // uploadPhoneNumberToDB to upload it to the database.
    private void setNumberInDB() {
        String number = LocalVariables.getDefaults("number",getApplicationContext());
        if (number.isEmpty()) {
            System.out.println("()()()() EMPTY");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("NUMBER",number);
        FirebaseFirestore.getInstance().collection("userdata")
                .document(FirebaseAuth.getInstance().getUid())
                .set(map, SetOptions.merge());
    }

    private void transferToDashboard() {
        Intent intent = new Intent(InstructionsActivity.this, AdsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
