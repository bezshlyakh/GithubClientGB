package com.geekbrains.githubclient.mvp.presenter;

import com.geekbrains.githubclient.mvp.view.RepoInfoView;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

public class RepoInfoPresenter extends MvpPresenter<RepoInfoView> {

    private static final String TAG = RepoInfoPresenter.class.getSimpleName();
    private final Router ROUTER;

    public RepoInfoPresenter(Router router) {
        ROUTER = router;
    }

    public boolean backPressed() {
        ROUTER.exit();
        return true;
    }
}
