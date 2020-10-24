package com.example.project.Model;

public class Model_rv_menu {
     private String Name;
     private int imageResource;

    public Model_rv_menu(String name, int imageResource) {
        Name = name;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
