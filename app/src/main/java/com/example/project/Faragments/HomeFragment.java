package com.example.project.Faragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Adapter.adapter_rv_menu;
import com.example.project.Adapter.listAdapter;
import com.example.project.Interface.apiInterface;
import com.example.project.MainActivity;
import com.example.project.Model.Model_rv_menu;
import com.example.project.Model.dataModel;
import com.example.project.Model.model_rv_list;
import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment implements View.OnClickListener {
    adapter_rv_menu adapter;
    listAdapter adapter1;
    RecyclerView
            recyclerView1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://run.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface apiInterface = retrofit.create(com.example.project.Interface.apiInterface.class);
        Call<List<dataModel>> call  = apiInterface.getData();
        call.enqueue(new Callback<List<dataModel>>() {
            @Override
            public void onResponse(Call<List<dataModel>> call, Response<List<dataModel>> response) {
                if(response.code() != 200){
                    return;
                }
                List<dataModel> data = response.body();
                String responseTest = "";
                List<model_rv_list> model_rv_lists = new ArrayList<>();
                for(dataModel d:data){



                    model_rv_lists.add(new model_rv_list(d.getNameModel(),"This is a startUp by Vivek khandelwal and Ayush Sunariya","https://img.etimg.com/thumb/msid-75176755,width-640,resizemode-4,imgsize-612672/effect-of-coronavirus-on-food.jpg"));


                }
                adapter1 = new listAdapter(model_rv_lists,getContext());
                recyclerView1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<dataModel>> call, Throwable t) {

            }
        });


    }
    private void findViews(View v) {
                recyclerView1
                = v.findViewById(
                R.id.recyclerview2);
        LinearLayoutManager
                layoutManager
                = new LinearLayoutManager(
                getContext());
        RecyclerView
                recyclerView
                = v.findViewById(
                R.id.recyclarView);
        LinearLayoutManager
                layoutManager1
                = new LinearLayoutManager(
                getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);
        List<Model_rv_menu> model_rv_menus = new ArrayList<>();
        model_rv_menus.add(new Model_rv_menu("Chinese",R.drawable.chinese));
        model_rv_menus.add(new Model_rv_menu("Pizza",R.drawable.pizza));
        model_rv_menus.add(new Model_rv_menu("Burger",R.drawable.burger));
        model_rv_menus.add(new Model_rv_menu("Vegetarian",R.drawable.vegetarian));
        model_rv_menus.add(new Model_rv_menu("Non-Vegetarian",R.drawable.chicken));

        adapter = new adapter_rv_menu(model_rv_menus);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        EditText editText = v.findViewById(R.id.editTextTextPersonName);






    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager =getFragmentManager();
        // we must handle the callback fragment process
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        Searchfragment searchfragment = new Searchfragment();
        fragmentTransaction.replace(R.id.framelayout,searchfragment);
        fragmentTransaction.commit();
    }
}