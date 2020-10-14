package com.geekbrains.githubclient.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class GithubUserReposItem implements Parcelable {

    @Expose String name;
    @Expose String fullName;
    @Expose String description;
    @Expose String forks;

    public String getName() {
        return name;
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

    protected GithubUserReposItem(Parcel in) {
    }

    public static final Creator<GithubUserReposItem> CREATOR = new Creator<GithubUserReposItem>() {
        @Override
        public GithubUserReposItem createFromParcel(Parcel in) {
            return new GithubUserReposItem(in);
        }

        @Override
        public GithubUserReposItem[] newArray(int size) {
            return new GithubUserReposItem[size];
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
