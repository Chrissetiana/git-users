package com.chrissetiana.gitusers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    private int items;

    RepoAdapter(int numItems) {
        items = numItems;
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items;
    }

    @Override
    public int getItemViewType(int position) {
        return android.R.layout.simple_list_item_2;
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        RepoViewHolder(View view) {
            super(view);
        }

        public void bind(int position) {
        }
    }
}
