package com.geekbrains.githubclient.mvp.model.repo.retrofit;

import com.geekbrains.githubclient.mvp.model.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.room.RoomGithubUser;
import com.geekbrains.githubclient.mvp.model.entity.room.cache.IRoomGithubUsersCache;
import com.geekbrains.githubclient.mvp.model.entity.room.cache.RoomGithubUsersCache;
import com.geekbrains.githubclient.mvp.model.network.INetworkStatus;
import com.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUsersRepo implements IGithubUsersRepo {

    final IDataSource API;
    final INetworkStatus NETWORK_STATUS;
    final Database DB;
    final IRoomGithubUsersCache USERS_CACHE;

    public RetrofitGithubUsersRepo(IDataSource api, INetworkStatus networkStatus, Database db) {
        API = api;
        NETWORK_STATUS = networkStatus;
        DB = db;
        USERS_CACHE = new RoomGithubUsersCache(api, db);
    }

    @Override
    public Single<List<GithubUser>> getUsers() {

        return NETWORK_STATUS.isOnlineSingle().flatMap((isOnline)-> {
            if (isOnline) {
                return USERS_CACHE.putToCache();
            } else {
                return USERS_CACHE.loadFromCache();
            }
        }).subscribeOn(Schedulers.io());
    }
}
