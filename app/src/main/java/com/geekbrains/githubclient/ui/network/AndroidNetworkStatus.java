package com.geekbrains.githubclient.ui.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.util.Log;
import androidx.annotation.NonNull;
import com.geekbrains.githubclient.GithubApplication;
import com.geekbrains.githubclient.mvp.model.network.INetworkStatus;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class AndroidNetworkStatus implements INetworkStatus {
    private static final String TAG = AndroidNetworkStatus.class.getSimpleName();

    private final BehaviorSubject<Boolean> STATUS_OBJECT = BehaviorSubject.create();

    public AndroidNetworkStatus() {

        STATUS_OBJECT.onNext(false);

        ConnectivityManager connectivityManager = (ConnectivityManager) GithubApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest networkRequest = new NetworkRequest.Builder().build();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                if (GithubApplication.DEBUG) {
                    Log.d(TAG, "onAvailable");
                }
                STATUS_OBJECT.onNext(true);
            }

            @Override
            public void onUnavailable() {
                super.onUnavailable();
                if (GithubApplication.DEBUG) {
                    Log.d(TAG, "onUnavailable");
                }
                STATUS_OBJECT.onNext(false);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                if (GithubApplication.DEBUG) {
                    Log.d(TAG, "onLost");
                }
                STATUS_OBJECT.onNext(false);
            }
        });
    }

    @Override
    public Observable<Boolean> isOnline() {
        return STATUS_OBJECT;
    }

    @Override
    public Single<Boolean> isOnlineSingle() {
        return STATUS_OBJECT.first(false);
    }
}
