package com.geekbrains.githubclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.geekbrains.githubclient.R;
import com.geekbrains.githubclient.mvp.presenter.list.IReposListPresenter;
import com.geekbrains.githubclient.mvp.view.RepoItemView;

public class RepoRVAdapter extends RecyclerView.Adapter<RepoRVAdapter.ViewHolder>{
    private final IReposListPresenter REPOS_PRESENTER;

    public RepoRVAdapter(IReposListPresenter presenter) {
        REPOS_PRESENTER = presenter;
    }

    @NonNull
    @Override
    public RepoRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View repoView = inflater.inflate(R.layout.item_repo, parent, false);
        return new RepoRVAdapter.ViewHolder(repoView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoRVAdapter.ViewHolder holder, int position) {
        holder.position = position;
        holder.itemView.setOnClickListener(view -> REPOS_PRESENTER.onItemClick(holder));
        REPOS_PRESENTER.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return REPOS_PRESENTER.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements RepoItemView {
        TextView textView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_repo_name);
        }

        @Override
        public void setRepoName(String text) {
            textView.setText(text);
        }

        @Override
        public int getPos() {
            return position;
        }
    }

}
