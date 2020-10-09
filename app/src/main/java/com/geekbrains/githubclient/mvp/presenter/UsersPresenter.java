package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepo;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.UserItemView;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.navigation.Screens;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UsersPresenter extends MvpPresenter<UsersView> {
    private static final String TAG = UsersPresenter.class.getSimpleName();

    private static final boolean VERBOSE = true;

    private GithubUserRepo mUsersRepo = new GithubUserRepo();
    private Router mRouter = GithubApplication.INSTANCE.getRouter();
    private GithubUser githubUser;
    private Disposable disposables;

    private class UsersListPresenter implements IUserListPresenter {
        private List<GithubUser> mUsers = new ArrayList<>();

        @Override
        public void onItemClick(UserItemView view) {
            if (VERBOSE) {
                Log.v(TAG, " onItemClick " + view.getPos());
            }
            githubUser = mUsers.get(view.getPos());
            mRouter.navigateTo(new Screens.UserInfoScreen(githubUser));
        }

        @Override
        public void bindView(UserItemView view) {
            GithubUser user = mUsers.get(view.getPos());
            view.setLogin(user.getLogin());
        }

        @Override
        public int getCount() {
            return mUsers.size();
        }
    }

    private UsersPresenter.UsersListPresenter mUserListPresenter = new UsersPresenter.UsersListPresenter();

    public IUserListPresenter getPresenter() {
        return mUserListPresenter;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().init();
        loadData();
    }

    private void loadData() {
        //List<GithubUser> users = mUsersRepo.getUsers();
        //mUserListPresenter.mUsers.addAll(users);

        disposables = mUsersRepo.getUsers()
                .subscribe(
                        (s) -> Log.i(TAG, "onNext " + mUserListPresenter.mUsers.addAll(s)),
                        (e) -> Log.i(TAG, "onError" + e.getMessage()),
                        () -> Log.i(TAG, "onComplete")
                );
        getViewState().updateList();
    }

    public boolean backPressed() {
        mRouter.backTo(new Screens.UsersScreen());
        disposables.dispose();
        return true;
    }

}
