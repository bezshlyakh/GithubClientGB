package com.geekbrains.githubclient.mvp.model.entity.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {@ForeignKey(
                entity = RoomGithubUser.class,
                parentColumns = {"id"},
                childColumns = {"userId"},
                onDelete = ForeignKey.CASCADE
        )}
)
public class RoomGithubUserRepo {
    @PrimaryKey
    String id;
    String name;
    String userId;
    String fullName;
    String description;
    String forks;

    public RoomGithubUserRepo(String id, String name, String userId, String fullName, String description, String forks) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.fullName = fullName;
        this.description = description;
        this.forks = forks;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
