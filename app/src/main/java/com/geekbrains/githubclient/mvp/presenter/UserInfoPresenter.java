package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepository;
import com.geekbrains.githubclient.mvp.model.repo.IGithubUserRepositories;
import com.geekbrains.githubclient.mvp.presenter.list.IReposListPresenter;
import com.geekbrains.githubclient.mvp.view.RepoItemView;
import com.geekbrains.githubclient.mvp.view.UserInfoView;
import com.geekbrains.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UserInfoPresenter extends MvpPresenter<UserInfoView> {

    private static final String TAG = UserInfoPresenter.class.getSimpleName();
    private static final boolean VERBOSE = true;
    private final Scheduler SCHEDULER;
    private final IGithubUserRepositories USER_REPOSITORIES;
    private final Router ROUTER;
    private GithubUser gitUser;


    public UserInfoPresenter(Scheduler scheduler, IGithubUserRepositories userRepos, Router router) {
        SCHEDULER = scheduler;
        USER_REPOSITORIES = userRepos;
        ROUTER = router;
    }

    public void setUser(GithubUser gitUser){
        this.gitUser = gitUser;
    }

    private class ReposListPresenter implements IReposListPresenter {
        private List<GithubUserRepository> mRepos = new ArrayList<>();

        @Override
        public void onItemClick(RepoItemView view) {
            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.getPos());
            }

            GithubUserRepository reposItem = mRepos.get(view.getPos());
            ROUTER.navigateTo(new Screens.RepoInfoScreen(reposItem));
        }

        @Override
        public void bindView(RepoItemView view) {
            GithubUserRepository reposItem = mRepos.get(view.getPos());
            view.setRepoName(reposItem.getName());
        }

        @Override
        public int getCount() {
            return mRepos.size();
        }
    }

    private final UserInfoPresenter.ReposListPresenter REPOS_LIST_PRESENTER = new ReposListPresenter();

    public IReposListPresenter getPresenter() {
        return REPOS_LIST_PRESENTER;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData();
    }

    private void loadData() {
        Disposable disposables = USER_REPOSITORIES.getRepos(gitUser.getReposUrl(), gitUser.getId()).observeOn(SCHEDULER).subscribe
                (repos -> {
                            REPOS_LIST_PRESENTER.mRepos.clear();
                            REPOS_LIST_PRESENTER.mRepos.addAll(repos);
                            getViewState().updateList();
                        },
                        (e) -> Log.w(TAG, "Error" + e.getMessage()));
    }

    public boolean backPressed() {
        ROUTER.exit();
        return true;
    }

}
