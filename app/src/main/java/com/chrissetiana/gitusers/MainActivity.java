package com.chrissetiana.gitusers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    UserAdapter userAdapter;
    RepoAdapter repoAdapter;
    TextView emptyText;
    View progressBar;
    private String source = "https://api.github.com/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView userList = findViewById(R.id.list_result);
        userAdapter = new UserAdapter(this, new ArrayList<UserActivity>());
        userList.setAdapter(userAdapter);

        ListView repoList = findViewById(R.id.list_repo);
        repoAdapter = new RepoAdapter(this, new ArrayList<UserActivity>());
        repoList.setAdapter(repoAdapter);

        repoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                UserActivity current = repoAdapter.getItem(position);
                assert current != null;

                Uri uri = Uri.parse(current.getRepoLink());

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Redirecting to online repository", Toast.LENGTH_SHORT).show();

                Log.d("MainActivity", "Redirecting to " + uri.toString());
            }
        });

        emptyText = findViewById(R.id.list_empty);
        userList.setEmptyView(emptyText);

        progressBar = findViewById(R.id.list_progress);
        progressBar.setVisibility(View.GONE);

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchText = findViewById(R.id.search_text);
                String searchQuery = source + searchText.getText().toString().trim();

                UserAsyncTask task = new UserAsyncTask();
                task.execute(searchQuery);

                progressBar.setVisibility(View.VISIBLE);

                Log.d("MainActivity", searchQuery);
            }
        });
    }

    private class UserAsyncTask extends AsyncTask<String, Void, UserActivity> {

        @Override
        protected UserActivity doInBackground(String... strings) {
            if (strings[0] == null) {
                return null;
            }

            return UserQuery.fetchData(strings[0]);
        }

        @Override
        protected void onPostExecute(UserActivity userActivity) {
            progressBar.setVisibility(View.VISIBLE);
            userAdapter.clear();
            repoAdapter.clear();

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                if (userActivity != null && !userActivity.isEmpty()) {
                    userAdapter.addAll();
                    repoAdapter.addAll();
                } else {
                    emptyText.setText(getString(R.string.no_result));
                }
            } else {
                progressBar.setVisibility(View.GONE);
                emptyText.setText(getString(R.string.no_conn));
            }
        }
    }
}
