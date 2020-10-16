package com.geekbrains.githubclient.mvp.model.entity.room.cache;

import com.geekbrains.githubclient.mvp.model.entity.GithubUser;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IRoomGithubUsersCache {

    Single<List<GithubUser>> putToCache();

    Single<List<GithubUser>> loadFromCache();

}
