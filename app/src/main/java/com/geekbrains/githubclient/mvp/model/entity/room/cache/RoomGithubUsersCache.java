package com.geekbrains.githubclient.mvp.model.entity.room.cache;

import com.geekbrains.githubclient.mvp.model.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.room.RoomGithubUser;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class RoomGithubUsersCache implements IRoomGithubUsersCache {

    final IDataSource API;
    final Database DB;

    public RoomGithubUsersCache(IDataSource api, Database db){
        API = api;
        DB = db;
    }

    @Override
    public Single<List<GithubUser>> putToCache() {
        return API.getUsers().flatMap((users) -> Single.fromCallable(()-> {
            List<RoomGithubUser> roomGithubUsers = new ArrayList<>();

            for (GithubUser user: users) {
                RoomGithubUser roomUser = new RoomGithubUser(user.getId(),
                        user.getLogin(),
                        user.getAvatarUrl(),
                        user.getReposUrl());

                roomGithubUsers.add(roomUser);
            }

            DB.userDao().insert(roomGithubUsers);
            return users;
        }));
    }

    @Override
    public Single<List<GithubUser>> loadFromCache() {
        return Single.fromCallable(()-> {
            List<RoomGithubUser> roomGithubUsers = DB.userDao().getAll();

            List<GithubUser> users = new ArrayList<>();

            for (RoomGithubUser roomGithubUser : roomGithubUsers) {
                GithubUser githubUser = new GithubUser(roomGithubUser.getId(),
                        roomGithubUser.getLogin(),
                        roomGithubUser.getAvatarUrl(),
                        roomGithubUser.getReposUrl());

                users.add(githubUser);
            }

            return users;
        });
    }
}
