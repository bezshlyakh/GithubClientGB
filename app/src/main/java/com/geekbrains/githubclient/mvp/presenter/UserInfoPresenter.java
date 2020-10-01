package com.geekbrains.githubclient.mvp.presenter;

import android.util.Log;
import android.widget.TextView;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.view.UserInfoView;

import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class UserInfoPresenter extends MvpPresenter<UserInfoView> {

    private static final String TAG = UserInfoPresenter.class.getSimpleName();
    private static final boolean VERBOSE = true;
    private final Router mRouter = GithubApplication.INSTANCE.getRouter();
    private String userLogin;

    public boolean backPressed() {
        mRouter.exit();
        return true;
    }

}
