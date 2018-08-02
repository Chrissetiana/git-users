package com.chrissetiana.gitusers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<UserActivity> {

    UserAdapter(Activity context, ArrayList<UserActivity> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_layout, parent, false);
        }

        UserActivity current = getItem(position);
        assert current != null;

        ImageView thumbnail = view.findViewById(R.id.user_image);
        Picasso.get()
                .load(current.getUserimage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(thumbnail);

        TextView name = view.findViewById(R.id.user_name);
        name.setText(current.getUsername());

        TextView bio = view.findViewById(R.id.user_bio);
        bio.setText(current.getBio());

        TextView repo = view.findViewById(R.id.user_repo);
        repo.setText(current.getRepo());

        TextView gist = view.findViewById(R.id.user_gist);
        gist.setText(current.getGist());

        TextView followers = view.findViewById(R.id.user_followers);
        followers.setText(current.getUfollowers());

        TextView following = view.findViewById(R.id.user_following);
        following.setText(current.getUfollowing());

        return view;
    }
}
