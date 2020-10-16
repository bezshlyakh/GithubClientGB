package com.geekbrains.githubclient.mvp.model.entity.room.cache;

import com.geekbrains.githubclient.mvp.model.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepository;
import com.geekbrains.githubclient.mvp.model.entity.GithubRepoOwner;
import com.geekbrains.githubclient.mvp.model.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.entity.room.RoomGithubUserRepo;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.rxjava3.core.Single;

public class RoomGithubRepositoriesCache implements IRoomGithubRepositoriesCache {

    final IDataSource API;
    final Database DB;

    public RoomGithubRepositoriesCache(IDataSource api, Database db){
        API = api;
        DB = db;
    }

    @Override
    public Single<List<GithubUserRepository>> putToCache(String url) {
        return API.getRepos(url).flatMap((repositories) -> Single.fromCallable(()-> {
            List<RoomGithubUserRepo> roomGithubUserRepos = new ArrayList<>();

            for (GithubUserRepository r: repositories) {
                RoomGithubUserRepo roomRepo = new RoomGithubUserRepo(r.getId(),
                        r.getName(),
                        r.getOwner().getId(),
                        r.getFullName(),
                        r.getDescription(),
                        r.getForks());

                roomGithubUserRepos.add(roomRepo);
            }

            DB.repositoryDao().insert(roomGithubUserRepos);
            return repositories;
        }));
    }

    @Override
    public Single<List<GithubUserRepository>> loadFromCache(String userID) {
        return Single.fromCallable(()-> {
            List<RoomGithubUserRepo> roomRepo = DB.repositoryDao().findByUser(userID);

            List<GithubUserRepository> repos = new ArrayList<>();

            for (RoomGithubUserRepo r : roomRepo) {
                GithubUserRepository gitUserRepo = new GithubUserRepository(r.getId(),
                        r.getName(),
                        new GithubRepoOwner(r.getUserId()),
                        r.getFullName(),
                        r.getDescription(),
                        r.getForks());

                repos.add(gitUserRepo);
            }

            return repos;
        });
    }
}
