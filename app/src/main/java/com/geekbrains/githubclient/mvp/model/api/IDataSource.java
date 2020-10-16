package com.geekbrains.githubclient.mvp.model.api;

import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepository;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IDataSource {
    @GET("/users")
    Single<List<GithubUser>> getUsers();

    @GET
    Single<List<GithubUserRepository>> getRepos(@Url String url);
}
