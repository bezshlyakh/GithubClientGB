package com.geekbrains.githubclient.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.model.entity.GithubUser;
import com.geekbrains.githubclient.mvp.model.entity.room.Database;
import com.geekbrains.githubclient.mvp.model.repo.IGithubUserRepositories;
import com.geekbrains.githubclient.mvp.model.repo.retrofit.RetrofitGithubUserRepositories;
import com.geekbrains.githubclient.mvp.presenter.UserInfoPresenter;
import com.geekbrains.githubclient.mvp.view.UserInfoView;
import com.geekbrains.githubclient.mvp.view.image.GlideImageLoader;
import com.geekbrains.githubclient.mvp.view.image.IImageLoader;
import com.geekbrains.githubclient.ui.BackButtonListener;
import com.geekbrains.githubclient.ui.adapter.RepoRVAdapter;
import com.geekbrains.githubclient.ui.network.AndroidNetworkStatus;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class UserInfoFragment extends MvpAppCompatFragment implements UserInfoView, BackButtonListener {

    private final String TAG = "UserInfoFragment";
    private View mView;
    private GithubUser gitUser;
    private static final IImageLoader<ImageView> IMAGE_LOADER = new GlideImageLoader();
    private RecyclerView mRecyclerView;
    private RepoRVAdapter mAdapter;

    @InjectPresenter
    UserInfoPresenter mPresenter;

    @ProvidePresenter
    UserInfoPresenter provideReposPresenter() {
        IGithubUserRepositories userRepositories = new RetrofitGithubUserRepositories((GithubApplication.INSTANCE).getApi(),
                new AndroidNetworkStatus(),
                Database.getInstance());
        Router router = GithubApplication.INSTANCE.getRouter();
        return new UserInfoPresenter(AndroidSchedulers.mainThread(), userRepositories, router);
    }


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
        assert getArguments() != null;
        gitUser = getArguments().getParcelable("user");
        Log.v(TAG, "fragment created");
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
        mRecyclerView = mView.findViewById(R.id.rv_repos);
        mPresenter.setUser(gitUser);
        Log.v(TAG, "view created");
        init();
    }

    @Override
    public void init() {
        TextView loginTV = mView.findViewById(R.id.tv_user_login_info);
        loginTV.setText(gitUser.getLogin());
        ImageView avatarImg = mView.findViewById(R.id.iv_avatar_user_info);
        IMAGE_LOADER.loadImage(gitUser.getAvatarUrl(), avatarImg);
        initRecycler();
    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }

    public void initRecycler(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mView.getContext());
        mAdapter = new RepoRVAdapter(mPresenter.getPresenter());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }

}
