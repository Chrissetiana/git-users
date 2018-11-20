package com.chrissetiana.gitusers.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrissetiana.gitusers.data.model.UserActivity;

import java.util.ArrayList;
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private List<UserActivity> users;
    private final ListItemClickListener listener;

    public RepoAdapter(ListItemClickListener clickListener) {
        users = new ArrayList<>();
        listener = clickListener;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        UserActivity user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public int getItemViewType(int position) {
        return android.R.layout.simple_list_item_2;
    }

    public void setData(List<UserActivity> data) {
        users = data;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    class RepoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView repoName;
        TextView repoLang;

        RepoViewHolder(View view) {
            super(view);
            repoName = view.findViewById(android.R.id.text1);
            repoLang = view.findViewById(android.R.id.text2);
        }

        void bind(UserActivity position) {
            // repoName.setText(position.getRepoName());
            repoLang.setText(position.getRepoLanguage());
            /*StringBuilder repoList = new StringBuilder();
            for (int i = 0; i < activity.getRepoName().size(); i++) {
                repoList.append(activity.getRepoName().get(i)).append("\n");
            }
            repoName.setText(repoList);*/
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onListItemClick(position);
        }
    }
}
