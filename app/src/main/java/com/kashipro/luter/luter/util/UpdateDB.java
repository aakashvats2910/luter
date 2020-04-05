package com.kashipro.luter.luter.util;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class UpdateDB {

    public static void updateDB(String here, final TextView textView) {
        final String HERE = here;
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("userdata").document(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        if (snapshot.contains(HERE)) {
                            long clicked = (long)snapshot.get(HERE);
                            Map<String, Object> map = new HashMap<>();
                            map.put(HERE, clicked+1);
                            db.collection("userdata")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .update(map);
                            textView.setText("" + (clicked+1));
                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put(HERE, 1);
                            db.collection("userdata").
                                    document(FirebaseAuth.getInstance().getUid()).set(map, SetOptions.merge());
                            textView.setText("1");
                        }
                    }
                }
            }
        });
    }

    public static void initializeClicksFromDB(String here, final TextView textView) {
        final String HERE = here;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("userdata").document(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot.exists()) {
                        if (snapshot.contains(HERE)) {
                            long clicked = (long) snapshot.get(HERE);
                            textView.setText("" + clicked);
                        } else {
                            textView.setText("0");
                        }
                    }
                }
            }
        });
    }





}
