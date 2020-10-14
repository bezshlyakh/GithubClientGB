package com.geekbrains.githubclient.navigation;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import com.geekbrains.githubclient.ui.fragment.RepoInfoFragment;
import com.geekbrains.githubclient.ui.fragment.UserInfoFragment;
import com.geekbrains.githubclient.ui.fragment.UsersFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {
    public static class UsersScreen extends SupportAppScreen {
        @Override
        public Fragment getFragment() {
            return new UsersFragment();
        }
    }

    public static class UserInfoScreen extends SupportAppScreen {
        private final Parcelable GIT_USER;

        public UserInfoScreen(Parcelable user){
            this.GIT_USER = user;
        }

        @Override
        public Fragment getFragment() {
            return UserInfoFragment.newInstance(GIT_USER);
        }
    }

    public static class RepoInfoScreen extends SupportAppScreen {
        private final Parcelable USER_REPO;

        public RepoInfoScreen(Parcelable repo){
            this.USER_REPO = repo;
        }

        @Override
        public Fragment getFragment() {
            return RepoInfoFragment.newInstance(USER_REPO);
        }
    }
}
