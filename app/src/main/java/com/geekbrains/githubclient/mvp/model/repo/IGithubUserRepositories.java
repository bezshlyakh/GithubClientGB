package com.geekbrains.githubclient.mvp.model.repo;

import com.geekbrains.githubclient.mvp.model.entity.GithubUserReposItem;
import java.util.List;
import io.reactivex.rxjava3.core.Single;

public interface IGithubUserRepositories {
    Single<List<GithubUserReposItem>> getRepos(String url);
}
