package com.geekbrains.githubclient.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.model.entity.GithubUserRepository;
import com.geekbrains.githubclient.mvp.presenter.RepoInfoPresenter;
import com.geekbrains.githubclient.mvp.view.RepoInfoView;
import com.geekbrains.githubclient.ui.BackButtonListener;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Router;

public class RepoInfoFragment extends MvpAppCompatFragment implements RepoInfoView, BackButtonListener {

    private final String TAG = "RepoInfoFragment";
    private View mView;
    private GithubUserRepository repo;

    @InjectPresenter
    RepoInfoPresenter mPresenter;

    @ProvidePresenter
    RepoInfoPresenter provideRepoInfoPresenter() {
        Router router = GithubApplication.INSTANCE.getRouter();
        return new RepoInfoPresenter(router);
    }

    public static RepoInfoFragment newInstance(Parcelable userRepo) {
        RepoInfoFragment myFragment = new RepoInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("repo", userRepo);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        repo = getArguments().getParcelable("repo");
        Log.v(TAG, "fragment created");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_repo_info, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.v(TAG, "view created");
        init();
    }

    @Override
    public void init() {
        TextView repoNameTV = mView.findViewById(R.id.tv_repo_full_name);
        repoNameTV.setText(repo.getFullName());

        TextView repoDiscrTV = mView.findViewById(R.id.tv_repo_description);
        repoDiscrTV.setText(repo.getDescription());

        TextView repoForksNumber = mView.findViewById(R.id.tv_repo_forks_number);
        repoForksNumber.setText(repo.getForks());

    }

    @Override
    public boolean backPressed() {
        return mPresenter.backPressed();
    }
}
