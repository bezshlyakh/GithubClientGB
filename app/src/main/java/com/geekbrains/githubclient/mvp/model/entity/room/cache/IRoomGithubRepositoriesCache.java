package com.geekbrains.githubclient.mvp.model.entity.room.cache;

import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface IRoomGithubRepositoriesCache {
    Single<List<GithubUserRepository>> putToCache(String url);

    Single<List<GithubUserRepository>> loadFromCache(String userID);

}
