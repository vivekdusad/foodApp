package com.example.project;

import android.app.ActivityOptions;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity2 extends BasicActivity implements View.OnClickListener,View.OnKeyListener {
    public static final int REQUEST_CODE = 3001;
    private View decorView;

    TextView signupActivity;
    Button loginButton;
    TextInputEditText emailLogin;
    TextInputEditText passwordLogin;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    ImageView googleImageView;
    ImageView phoneImageView;
    CardView cardView;
    LottieAnimationView lottieAnimationView;
    TextView forgetPassword,qutemessage;
    FirebaseFirestore mStore;
    DocumentReference docRef;
    String userID;
    LinearLayout loginLayout;

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            String emailaddress = emailLogin.getText().toString();
            String password = passwordLogin.getText().toString();
            Toast.makeText(this, emailaddress+" "+password, Toast.LENGTH_SHORT).show();
            if(!emailaddress.equals("")&& !password.equals("")){
                loginUser(emailaddress,password);
            }

        }
        return false;
    }
    public void initalizeUI(){
        cardView = findViewById(R.id.cardView);
        lottieAnimationView = findViewById(R.id.lottieAnimations);
        signupActivity = findViewById(R.id.goToSignupActivity);
        signupActivity.setOnClickListener(this);
        loginButton= findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        emailLogin = findViewById(R.id.emailEditText);
        googleImageView = findViewById(R.id.googleImageView);
        loginLayout = findViewById(R.id.LoginBackground);
        loginLayout.setOnClickListener(this);
        googleImageView.setOnClickListener(this);
        passwordLogin = findViewById(R.id.passwordEditText1);
        passwordLogin.setOnClickListener(this);
        forgetPassword = findViewById(R.id.forgetTextView);
        forgetPassword.setOnClickListener(this);
        phoneImageView = findViewById(R.id.phoneImageView);
        phoneImageView.setOnClickListener(this);
        qutemessage = findViewById(R.id.textView2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            final DocumentReference documentReference = mStore.collection("users").document(mAuth.getCurrentUser().getUid());
            try {
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            startActivity(new Intent(MainActivity2.this, MainActivity.class));
                            finish();
                        } else {
                            startActivity(new Intent(MainActivity2.this, DetailsActivity.class));
                            finish();
                        }
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        initalizeUI();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("926564093897-oulojsooesojad94j6pk0r4et73r98oq.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        decorView = getWindow().getDecorView();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
               
    }


    private void updateUI(FirebaseUser user) {

        Intent toMainActivity = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(toMainActivity);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBar());
        }
    }
    private int hideSystemBar(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                View.SYSTEM_UI_FLAG_FULLSCREEN|
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.goToSignupActivity){
            Intent signupActivity = new Intent(MainActivity2.this,SignupActivity.class);
            Pair[] pairs = new Pair[3];
            pairs[0] = new Pair<View,String>(cardView,"cardTransition");
            pairs[1] = new Pair<View,String>(lottieAnimationView,"imageTransition");
            pairs[2] = new Pair<View,String >(qutemessage,"quote");
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,pairs);
            startActivity(signupActivity,options.toBundle());
        }
        else if(v.getId() == R.id.loginButton){
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            String emailaddress = emailLogin.getText().toString();
            String password = passwordLogin.getText().toString();
            Toast.makeText(this, emailaddress+" "+password, Toast.LENGTH_SHORT).show();
            if(!emailaddress.equals("")&& !password.equals("")){
                loginUser(emailaddress,password);
            }

        }
        else if(v.getId() == R.id.googleImageView){
            signInwithGoogle();
        }
        else if(v.getId() == R.id.phoneImageView){
            Intent otpIntent = new Intent(MainActivity2.this,OtpActivity.class);
            startActivity(otpIntent);
            finish();
        }
        else if(v.getId() == R.id.forgetTextView){
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            String email = emailLogin.getText().toString();
            if(!email.isEmpty()){
                forgetPassword(email);
            }

        }
        else if(v.getId() == R.id.LoginBackground ){
            keyBoarDown();//this will get keyBoard down
        }
    }

    private void forgetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity2.this, "Sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
//login with google part

    private void signInwithGoogle() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, REQUEST_CODE);
    }
//login with google part
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            if(task.isSuccessful()){
                Toast.makeText(MainActivity2.this, "Created", Toast.LENGTH_SHORT).show();

                Intent toMainActivity = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(toMainActivity);
                finish();
            }
            else {
                Toast.makeText(MainActivity2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            handleSignInResult(task);
        }
    }
//login with google part
    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try{
            GoogleSignInAccount account = task.getResult(ApiException.class);
            final AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
            mAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                    }
                    else{

                    }

                }
            });


        }catch(Exception e){
            e.printStackTrace();


        }

    }
//Login with email part
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    Toast.makeText(MainActivity2.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}