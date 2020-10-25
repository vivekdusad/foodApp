package com.example.project.Interface;

import com.example.project.Model.dataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiInterface {
    ///https://run.mocky.io/                  v3/088b2c32-37fb-43cb-a16e-fc34bd96b87a
    @GET("v3/088b2c32-37fb-43cb-a16e-fc34bd96b87a")
    Call<List<dataModel>> getData();
}
