package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.UserItemView;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<UsersView> {
    private static final String TAG = UsersPresenter.class.getSimpleName();

    private static final boolean VERBOSE = true;

    private final Scheduler SCHEDULER;
    private final IGithubUsersRepo USERS_REPO;
    private final Router ROUTER;
    private Disposable disposables;

    public UsersPresenter(Scheduler scheduler, IGithubUsersRepo usersRepo, Router router) {
        SCHEDULER = scheduler;
        USERS_REPO = usersRepo;
        ROUTER = router;
    }


    private class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();

        @Override
        public void onItemClick(UserItemView view) {
            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.getPos());
            }
            GithubUser githubUser = mUsers.get(view.getPos());
            ROUTER.navigateTo(new Screens.UserInfoScreen(githubUser));
        }

        @Override
        public void bindView(UserItemView view) {
            GithubUser user = mUsers.get(view.getPos());
            view.setLogin(user.getLogin());
            view.loadAvatar(user.getAvatarUrl());
        }

        @Override
        public int getCount() {
            return mUsers.size();
        }
    }

    private final UsersPresenter.UsersListPresenter USERS_LIST_PRESENTER = new UsersPresenter.UsersListPresenter();

    public IUserListPresenter getPresenter() {
        return USERS_LIST_PRESENTER;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        getViewState().init();
        loadData();
    }

    private void loadData() {
        disposables = USERS_REPO.getUsers().observeOn(SCHEDULER).subscribe
                (repos-> {
                        USERS_LIST_PRESENTER.mUsers.clear();
                        USERS_LIST_PRESENTER.mUsers.addAll(repos);
                        getViewState().updateList();
                        },
                        (e) -> Log.w(TAG, "Error" + e.getMessage()));
    }

    public boolean backPressed() {
        ROUTER.backTo(new Screens.UsersScreen());
        disposables.dispose();
        return true;
    }

}
