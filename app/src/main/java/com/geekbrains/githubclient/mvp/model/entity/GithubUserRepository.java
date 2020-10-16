package com.geekbrains.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class GithubUserRepository implements Parcelable {

    @Expose String id;
    @Expose String name;
    @Expose GithubRepoOwner owner;
    @Expose String fullName;
    @Expose String description;
    @Expose String forks;

    public GithubUserRepository(String id, String name, GithubRepoOwner owner, String fullName, String description, String forks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.fullName = fullName;
        this.description = description;
        this.forks = forks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GithubRepoOwner getOwner() {
        return owner;
    }

    public void setOwner(GithubRepoOwner owner) {
        this.owner = owner;
    }

    public String getFullName(){
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public String getForks() {
        return forks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    protected GithubUserRepository(Parcel in) {
    }

    public static final Creator<GithubUserRepository> CREATOR = new Creator<GithubUserRepository>() {
        @Override
        public GithubUserRepository createFromParcel(Parcel in) {
            return new GithubUserRepository(in);
        }

        @Override
        public GithubUserRepository[] newArray(int size) {
            return new GithubUserRepository[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}

