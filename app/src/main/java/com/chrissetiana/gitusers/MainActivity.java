package com.chrissetiana.gitusers;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<UserActivity>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SOURCE = "https://api.github.com/users/";
    private static final int LOADER_ID = 1;
    RepoAdapter repoAdapter;
    ListView searchResult;
    EditText searchText;
    TextView emptyText;
    View progressBar;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.search_text);
        searchResult = findViewById(R.id.search_result);

        userAdapter = new UserAdapter(this, new ArrayList<UserActivity>());
        searchResult.setAdapter(userAdapter);

        emptyText = findViewById(R.id.list_empty);
        progressBar = findViewById(R.id.list_progress);

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                assert connectivityManager != null;
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if ((networkInfo != null && networkInfo.isConnected())) {
                    Log.d(TAG, "Search started");
                    userAdapter.clear();
                    loadQuery();
                } else {
                    emptyText.setText(getString(R.string.no_conn));
                }
            }
        });

        // getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void loadQuery() {
        String searchQuery = SOURCE + searchText.getText().toString().trim();

        Bundle bundle = new Bundle();
        bundle.putString("query", searchQuery);

        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);

        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this);
        }

        Log.d(TAG, "Bundle: " + bundle.toString());
    }

    private void loadData(List<UserActivity> data) {
        Log.d(TAG, "loadData received " + data);
        searchResult.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.INVISIBLE);
        userAdapter.addAll(data);
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
                String str = bundle.getString("query");
                Log.d(TAG, "Key received: " + str);
                if (str == null || TextUtils.isEmpty(str)) {
                    return null;
                }
                Log.d(TAG, "Load in bg started for " + str);
                List<UserActivity> activity = UserQuery.fetchData(str);
                Log.d(TAG, "Load in bg returned " + activity);
                return activity;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<UserActivity>> loader, List<UserActivity> data) {
        progressBar.setVisibility(View.INVISIBLE);
        if (data == null) {
            Log.d(TAG, "Loader finished: no data received");
            searchResult.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_result));
        } else {
            loadData(data);
            Log.d(TAG, "Loader finished: data received");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<UserActivity>> loader) {
    }
}
