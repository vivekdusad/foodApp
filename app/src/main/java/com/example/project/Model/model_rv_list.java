package com.example.project.Model;

public class model_rv_list {
    private String title;
    private String body;
    private int resource;

    public model_rv_list(String title, String body, int resource) {
        this.title = title;
        this.body = body;
        this.resource = resource;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public int getResource() {
        return resource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }
}
