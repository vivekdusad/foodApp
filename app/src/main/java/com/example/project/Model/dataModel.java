package com.example.project.Model;

import com.google.gson.annotations.SerializedName;

public class dataModel {
    @SerializedName("id")
    int idModel;
    @SerializedName("name")
    String nameModel;
    String imageUrl;

    public dataModel(int id, String name, String imageUrl) {
        this.idModel = id;
        this.nameModel = name;
        this.imageUrl = imageUrl;
    }

    public int getIdModel() {
        return idModel;
    }

    public String getNameModel() {
        return nameModel;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
