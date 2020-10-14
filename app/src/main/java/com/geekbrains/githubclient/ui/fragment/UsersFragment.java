package com.geekbrains.githubclient.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.model.repo.IGithubUsersRepo;
import com.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubUsersRepo;
import com.geekbrains.githubclient.mvp.presenter.MainPresenter;
import com.geekbrains.githubclient.mvp.presenter.UsersPresenter;
import com.geekbrains.githubclient.mvp.view.UsersView;
import com.geekbrains.githubclient.ui.BackButtonListener;
import com.geekbrains.githubclient.ui.adapter.UserRVAdapter;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class UsersFragment extends MvpAppCompatFragment implements UsersView, BackButtonListener {

    private RecyclerView mRecyclerView;
    private UserRVAdapter mAdapter;

    private View mView;

    @InjectPresenter
    UsersPresenter mPresenter;

    @ProvidePresenter
    UsersPresenter provideUsersPresenter() {
        IGithubUsersRepo usersRepo = new RetrofitGithubUsersRepo((GithubApplication.INSTANCE).getApi());
        Router router = GithubApplication.INSTANCE.getRouter();
        return new UsersPresenter(AndroidSchedulers.mainThread(), usersRepo, router);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_users, container, false);
        mRecyclerView = mView.findViewById(R.id.rv_users);
        return mView;
    }

    @Override
    public void init() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mView.getContext());
        mAdapter = new UserRVAdapter(mPresenter.getPresenter());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }
}
