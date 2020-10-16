package com.geekbrains.githubclient.mvp.model.repo.retrofit;

import com.geekbrains.githubclient.mvp.model.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepository;
import com.geekbrains.githubclient.mvp.model.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.room.RoomGithubUser;
import com.geekbrains.githubclient.mvp.model.entity.room.RoomGithubUserRepo;
import com.geekbrains.githubclient.mvp.model.entity.room.cache.IRoomGithubRepositoriesCache;
import com.geekbrains.githubclient.mvp.model.entity.room.cache.RoomGithubRepositoriesCache;
import com.geekbrains.githubclient.mvp.model.network.INetworkStatus;
import com.geekbrains.githubclient.mvp.model.repo.IGithubUserRepositories;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUserRepositories implements IGithubUserRepositories {

    final IDataSource API;
    final INetworkStatus NETWORK_STATUS;
    final Database DB;
    final IRoomGithubRepositoriesCache REPO_CACHE;


    public RetrofitGithubUserRepositories(IDataSource api, INetworkStatus networkStatus, Database db) {
        API = api;
        NETWORK_STATUS = networkStatus;
        DB = db;
        REPO_CACHE = new RoomGithubRepositoriesCache(api, db);
    }

    @Override
    public Single<List<GithubUserRepository>> getRepos(String url, String userID) {

        return NETWORK_STATUS.isOnlineSingle().flatMap((isOnline)-> {
            if (isOnline) {
                return REPO_CACHE.putToCache(url);

            } else {
                return REPO_CACHE.loadFromCache(userID);
            }
        }).subscribeOn(Schedulers.io());
    }
}
