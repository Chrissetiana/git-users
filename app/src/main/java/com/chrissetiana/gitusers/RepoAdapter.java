package com.chrissetiana.gitusers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RepoAdapter extends ArrayAdapter<UserActivity> {

    RepoAdapter(Activity context, ArrayList<UserActivity> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        UserActivity current = getItem(position);
        assert current != null;

        TextView repoName = view.findViewById(android.R.id.text1);
        repoName.setText(current.getRepoName());

        TextView repoLang = view.findViewById(android.R.id.text2);
        repoLang.setText(current.getRepoLanguage());

        return view;
    }
}
