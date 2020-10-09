package com.geekbrains.githubclient.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.presenter.UserInfoPresenter;
import com.geekbrains.githubclient.mvp.view.UserInfoView;
import com.geekbrains.githubclient.ui.BackButtonListener;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class UserInfoFragment extends MvpAppCompatFragment implements UserInfoView, BackButtonListener {

    private TextView loginTV;
    private View mView;
    private GithubUser gitUser;

    @InjectPresenter
    UserInfoPresenter mPresenter;

    public static UserInfoFragment newInstance(Parcelable gitUser) {
        UserInfoFragment myFragment = new UserInfoFragment();

        Bundle args = new Bundle();
        args.putParcelable("user", gitUser);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gitUser = getArguments().getParcelable("user");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user_info, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void init() {
        loginTV = mView.findViewById(R.id.tv_user_login_info);
        loginTV.setText(gitUser.getLogin());
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }

}
