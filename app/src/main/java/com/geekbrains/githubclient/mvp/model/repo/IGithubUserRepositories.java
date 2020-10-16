package com.geekbrains.githubclient.mvp.model.repo;

import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepository;
import java.util.List;
import io.reactivex.rxjava3.core.Single;

public interface IGithubUserRepositories {
    Single<List<GithubUserRepository>> getRepos(String url, String userID);
}
