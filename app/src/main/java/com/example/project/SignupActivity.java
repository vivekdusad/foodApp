package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends BasicActivity implements View.OnClickListener {
    TextView textView,quotemessage;

    TextInputEditText emailEditText;

    TextInputEditText passwordText;
    Button RegisterButton;
    private FirebaseAuth mAuth;
    String emailSignup;
    String passSignup;
    ImageView imageView2;
    CardView cardView;
    ConstraintLayout RegisterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.BacktoLoginText);
        textView.setOnClickListener(this);
//        usernameEditText = findViewById(R.id.UsernameEditText);
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
//        phoneEditText = findViewById(R.id.editTextPhone);
        passwordText = findViewById(R.id.passwordeditText);
        RegisterButton = findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(this);
        quotemessage = findViewById(R.id.textView6);
        imageView2 = findViewById(R.id.imageView2);
        cardView = findViewById(R.id.cardView2);
        RegisterLayout = findViewById(R.id.registerLayout);
        RegisterLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.BacktoLoginText){
            Intent backtologin = new Intent(SignupActivity.this,MainActivity2.class);
            Pair[] pairs = new Pair[3];
            pairs[0] = new Pair<View,String>(cardView,"cardTransition");
            pairs[1] = new Pair<View,String>(imageView2,"imageTransition");
            pairs[2] = new Pair<View,String >(quotemessage,"quote");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,pairs);
            startActivity(backtologin,options.toBundle());
        }
        else if(v.getId() == R.id.RegisterButton){
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            emailSignup = emailEditText.getText().toString();
            passSignup = passwordText.getText().toString();
            if(!passSignup.isEmpty()&& !emailSignup.isEmpty()) {
                createAccount(emailSignup, passSignup);
            }
        }
        else if(v.getId() == R.id.registerLayout){
            keyBoarDown();
        }
    }
    private boolean validateForm() {
        boolean valid = true;

        String emails = emailEditText.getText().toString();
        if (TextUtils.isEmpty(emails)) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        String passwords = passwordText.getText().toString();
        if (TextUtils.isEmpty(passwords)) {
            passwordText.setError("Required.");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
    private void createAccount(String email, String password) {

        if (!validateForm()) {
            return;
        }

        // showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignupActivity.this, "succes", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, DetailsActivity.class));

                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
//    public void Register(View view){
//        if(usernameEditText.getText().equals("")&&emailEditText.getText().equals("")&&phoneEditText.getText().equals("")&&passwordText.getText().equals("")){
//            Toast.makeText(this, "Please Fill Empty Spaces", Toast.LENGTH_SHORT).show();
//
//        }
//        else{
//            Toast.makeText(this, "Registered Succesfully!", Toast.LENGTH_SHORT).show();
//        }
//    }
}