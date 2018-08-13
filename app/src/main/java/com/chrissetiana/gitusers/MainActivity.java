package com.chrissetiana.gitusers;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<String> {

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
                loadQuery();
            }
        });

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable final Bundle bundle) {
        String searchQuery = SOURCE + editText.getText().toString().trim();
        return new UserLoader(this, searchQuery);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        progressBar.setVisibility(View.INVISIBLE);
        if (data == null) {
            showError();
        } else {
            loadData();
            showData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
    }

    private void loadQuery() {
        Bundle bundle = new Bundle();
        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(LOADER_ID, bundle, this);
        } else {
            loaderManager.restartLoader(LOADER_ID, bundle, this);
        }
    }

    private void loadData() {
        // update ui here or use adapter?
    }

    private void showData(String data) {
        userList.setVisibility(View.VISIBLE);
        emptyText.setVisibility(View.INVISIBLE);
    }

    private void showError() {
        userList.setVisibility(View.INVISIBLE);
        emptyText.setVisibility(View.VISIBLE);
    }

    class UserLoader extends AsyncTaskLoader<String> {
        String str;

        UserLoader(Context context, String source) {
            super(context);
            str = source;
        }

        @Override
        protected void onStartLoading() {
            progressBar.setVisibility(View.VISIBLE);
            forceLoad();
        }

        @Nullable
        @Override
        public String loadInBackground() {
            if (str == null || TextUtils.isEmpty(str)) {
                return null;
            }
            return UserQuery.fetchData(str);
        }
    }
}
