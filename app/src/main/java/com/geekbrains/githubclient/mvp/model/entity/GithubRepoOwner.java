package com.geekbrains.githubclient.mvp.model.entity;

import com.google.gson.annotations.Expose;

public class GithubRepoOwner {
    @Expose
    String id;

    public GithubRepoOwner(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
