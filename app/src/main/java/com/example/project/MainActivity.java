package com.example.project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapter.adapter_rv_menu;
import com.example.project.Adapter.listAdapter;
import com.example.project.Faragments.profileFragments;
import com.example.project.Model.Model_rv_menu;
import com.example.project.Model.model_rv_list;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,BottomNavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle ;
    private View decorView;
    ImageView imageView;
    FirebaseAuth mAuth;
    FirebaseFirestore mStore;
    adapter_rv_menu adapter;
    listAdapter adapter1;

    DocumentReference documentReference;
    public void initalizeUI(){
        imageView = findViewById(R.id.settingImageView);
        imageView.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();
        RecyclerView
                recyclerView
                = findViewById(
                R.id.recyclarView);
        RecyclerView
                recyclerView1
                = findViewById(
                R.id.recyclerview2);

        LinearLayoutManager
                layoutManager
                = new LinearLayoutManager(
                MainActivity.this);
        LinearLayoutManager
                layoutManager1
                = new LinearLayoutManager(
                MainActivity.this);

        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);
        List<Model_rv_menu> model_rv_menus = new ArrayList<>();
        model_rv_menus.add(new Model_rv_menu("Chinese",R.drawable.chinese));
        model_rv_menus.add(new Model_rv_menu("Pizza",R.drawable.pizza));
        model_rv_menus.add(new Model_rv_menu("Burger",R.drawable.burger));
        model_rv_menus.add(new Model_rv_menu("Vegetarian",R.drawable.vegetarian));
        model_rv_menus.add(new Model_rv_menu("Non-Vegetarian",R.drawable.chicken));
        List<model_rv_list> model_rv_lists = new ArrayList<>();
        model_rv_lists.add(new model_rv_list("Vivek Khandelwal","This is a startUp by Vivek khandelwal and Ayush Sunariya",R.drawable.eatery));
        model_rv_lists.add(new model_rv_list("Vivek Khandelwal","This is a startUp by Vivek khandelwal and Ayush Sunariya",R.drawable.eatery));
        model_rv_lists.add(new model_rv_list("Vivek Khandelwal","This is a startUp by Vivek khandelwal and Ayush Sunariya",R.drawable.eatery));
        model_rv_lists.add(new model_rv_list("Vivek Khandelwal","This is a startUp by Vivek khandelwal and Ayush Sunariya",R.drawable.eatery));
        adapter = new adapter_rv_menu(model_rv_menus);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter1 = new listAdapter(model_rv_lists);
        recyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();




    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBar());
                }
            }
        });
        drawerLayout =(DrawerLayout) findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        initalizeUI();
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        documentReference = mStore.collection("users").document(mAuth.getCurrentUser().getUid());

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
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
        if(v.getId() == R.id.settingImageView){
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
        }
    }
    public void logout(View v){
        FirebaseAuth.getInstance().signOut();
        GoogleSignIn.getClient(this,new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()).signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent backtoLogin = new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(backtoLogin);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        if(item.getItemId() == R.id.page_3){
            fragment = new profileFragments();



        }
        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}