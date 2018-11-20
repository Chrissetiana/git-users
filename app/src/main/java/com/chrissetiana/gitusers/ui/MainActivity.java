package com.chrissetiana.gitusers.ui;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chrissetiana.gitusers.R;
import com.chrissetiana.gitusers.data.model.UserActivity;
import com.chrissetiana.gitusers.data.remote.UserQuery;
import com.chrissetiana.gitusers.util.RepoAdapter;
import com.chrissetiana.gitusers.util.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<UserActivity>>, RepoAdapter.ListItemClickListener {

    private static final String USER_SOURCE = "https://api.github.com/users/";
    private static final String REPO_SOURCE = "/repos";
    private static final String USER_QUERY = "user_query";
    private static final String REPO_QUERY = "repo_query";
    private static final int LOADER_ID = 1;
    private EditText searchText;
    private ListView searchResult;
    private TextView emptyText;
    private View progressBar;
    private RecyclerView recycler;
    private UserAdapter userAdapter;
    private RepoAdapter repoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.search_text);
        searchResult = findViewById(R.id.search_result);
        emptyText = findViewById(R.id.list_empty);
        progressBar = findViewById(R.id.list_progress);
        recycler = findViewById(R.id.list_repo);

        userAdapter = new UserAdapter(this, new ArrayList<UserActivity>());
        searchResult.setAdapter(userAdapter);

//        repoAdapter = new RepoAdapter(this);
//        recycler.setAdapter(repoAdapter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recycler.setLayoutManager(layoutManager);
//        recycler.setHasFixedSize(true);

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                assert connectivityManager != null;
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if ((networkInfo != null && networkInfo.isConnected())) {
                    userAdapter.clear();
                    loadQuery();
                } else {
                    emptyText.setText(getString(R.string.no_conn));
                }
            }
        });
    }

    private void loadQuery() {
        String userQuery = USER_SOURCE + searchText.getText().toString().trim();
        String repoQuery = userQuery + REPO_SOURCE;

        Bundle bundle = new Bundle();
        bundle.putString(USER_QUERY, userQuery);
        bundle.putString(REPO_QUERY, repoQuery);

        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);

        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<List<UserActivity>> onCreateLoader(int i, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<List<UserActivity>>(this) {
            @Override
            protected void onStartLoading() {
                if (bundle == null) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<UserActivity> loadInBackground() {
                assert bundle != null;
                String user = bundle.getString(USER_QUERY);
                String repo = bundle.getString(REPO_QUERY);

                if (user == null || TextUtils.isEmpty(user) || repo == null || TextUtils.isEmpty(repo)) {
                    return null;
                }

                Log.d("MainActivity", "Searching for " + user);
                return UserQuery.fetchData(user, repo);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<UserActivity>> loader, List<UserActivity> data) {
        progressBar.setVisibility(View.INVISIBLE);

        if (data == null) {
            searchResult.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_result));
        } else {
            searchResult.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.INVISIBLE);
            userAdapter.addAll(data);
            repoAdapter.setData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<UserActivity>> loader) {
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(this, "Item clicked!", Toast.LENGTH_SHORT).show();
    }
}
