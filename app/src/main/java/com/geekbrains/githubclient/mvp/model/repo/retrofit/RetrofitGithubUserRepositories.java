package com.geekbrains.githubclient.mvp.model.repo.retrofit;

import com.geekbrains.githubclient.mvp.model.api.IDataSource;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserReposItem;
import com.geekbrains.githubclient.mvp.model.repo.IGithubUserRepositories;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RetrofitGithubUserRepositories implements IGithubUserRepositories {

    IDataSource api;

    public RetrofitGithubUserRepositories(IDataSource api) {
        this.api = api;
    }

    @Override
    public Single<List<GithubUserReposItem>> getRepos(String url) {
        return api.getRepos(url).subscribeOn(Schedulers.io());
    }
}
