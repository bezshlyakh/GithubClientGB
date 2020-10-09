package com.geekbrains.githubclient.navigation;

import android.os.Parcelable;

import androidx.fragment.app.Fragment;

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
        private Parcelable gitUser;

        public UserInfoScreen(Parcelable user){
            this.gitUser = user;
        }

        @Override
        public Fragment getFragment() {
            return UserInfoFragment.newInstance(gitUser);
        }
    }

}
