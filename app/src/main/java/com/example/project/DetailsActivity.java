package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
//this is expiremental barnch
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    FirebaseFirestore mstore;
    TextInputEditText firstName1;
    TextInputEditText lastnameEditTect;
    TextInputEditText phoneNumber;
    Button finishButton;
    String firstname;
    String lastname;
    DocumentReference docRef;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mAuth = FirebaseAuth.getInstance();
        mstore = FirebaseFirestore.getInstance();
        updateUI();
        finishButton.setOnClickListener(this);
         userID = mAuth.getCurrentUser().getUid();
        docRef = mstore.collection("users").document(userID);

    }
    public  void updateUI(){
        firstName1 = findViewById(R.id.firstName);
        lastnameEditTect = findViewById(R.id.lastname);
        phoneNumber = findViewById(R.id.phone);
        finishButton = findViewById(R.id.finishButton);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.finishButton){
            firstname = firstName1.getText().toString();
            lastname = lastnameEditTect.getText().toString();
            if(!firstname.isEmpty()&& !lastname.isEmpty()){
                Map<String,Object> user = new HashMap<>();
                user.put("firstName",firstname);
                user.put("lastName",lastname);
                docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(DetailsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(DetailsActivity.this, "Failed Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
}