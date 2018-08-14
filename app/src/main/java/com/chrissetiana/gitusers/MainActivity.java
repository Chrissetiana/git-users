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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SOURCE = "https://api.github.com/users/";
    private static final int LOADER_ID = 1;
    UserAdapter userAdapter;
    RepoAdapter repoAdapter;
    ListView userList;
    EditText editText;
    TextView emptyText;
    View progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.search_text);

        userList = findViewById(R.id.list_result);

        emptyText = findViewById(R.id.list_empty);
        userList.setEmptyView(emptyText);

        progressBar = findViewById(R.id.list_progress);
        progressBar.setVisibility(View.INVISIBLE);

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                assert connectivityManager != null;
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if ((networkInfo != null && networkInfo.isConnected())) {
                    loadQuery();
                    Log.d(TAG, "Search started");
                } else {
                    emptyText.setText(getString(R.string.no_conn));
                }
            }
        });

        // getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    private void loadQuery() {
        String searchQuery = SOURCE + editText.getText().toString().trim();

        Bundle bundle = new Bundle();
        bundle.putString("query", searchQuery);

        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);

        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this);
        }
    }

    private void loadData(String data) {
        Toast.makeText(this, "The loader returned " + data, Toast.LENGTH_LONG).show();
        Log.d(TAG, data);
        userList.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable final Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            protected void onStartLoading() {
                if (bundle == null) {
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Nullable
            @Override
            public String loadInBackground() {
                String str = bundle.getString("query");
                if (str == null || TextUtils.isEmpty(str)) {
                    return null;
                }
                Log.d(TAG, str);
                return UserQuery.fetchData(str);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        progressBar.setVisibility(View.INVISIBLE);
        if (data == null) {
            userList.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_result));
        } else {
            loadData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }
}
