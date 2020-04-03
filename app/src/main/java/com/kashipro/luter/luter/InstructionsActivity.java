package com.kashipro.luter.luter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kashipro.luter.luter.fragments.FirstInstruction;
import com.kashipro.luter.luter.fragments.SecondInstruction;
import com.kashipro.luter.luter.fragments.ThirdInstruction;

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
                    // TODO start next activity!
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.instructions_frame, selectedFragment).commit();
            }
        });

        skip_instruction_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INSTRUCTION--;
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

                if (INSTRUCTION < 0) INSTRUCTION = 0;

                getSupportFragmentManager().beginTransaction().replace(R.id.instructions_frame, selectedFragment).commit();
            }
        });

    }
}
