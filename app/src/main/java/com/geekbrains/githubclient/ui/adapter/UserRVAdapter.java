package com.geekbrains.githubclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.presenter.list.IUserListPresenter;
import com.geekbrains.githubclient.mvp.view.UserItemView;
import com.geekbrains.githubclient.mvp.view.image.GlideImageLoader;
import com.geekbrains.githubclient.mvp.view.image.IImageLoader;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.ViewHolder> {
    private final IUserListPresenter USERS_PRESENTER;

    public UserRVAdapter(IUserListPresenter presenter) {
        USERS_PRESENTER = presenter;
    }
    private static final IImageLoader<ImageView> IMAGE_LOADER = new GlideImageLoader();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position = position;
        holder.itemView.setOnClickListener(view -> USERS_PRESENTER.onItemClick(holder));
        USERS_PRESENTER.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return USERS_PRESENTER.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements UserItemView {
        TextView textView;
        ImageView avatarView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_login);
            avatarView = itemView.findViewById(R.id.iv_avatar);
        }

        @Override
        public void setLogin(String text) {
            textView.setText(text);
        }

        @Override
        public void loadAvatar(String url) {
            IMAGE_LOADER.loadImage(url, avatarView);
        }

        @Override
        public int getPos() {
            return position;
        }
    }

}
