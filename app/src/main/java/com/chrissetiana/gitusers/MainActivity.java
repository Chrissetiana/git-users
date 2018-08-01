package com.chrissetiana.gitusers;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    UserAdapter adapter;
    private String source = "https://api.github.com/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new UserAdapter(this, new ArrayList<UserActivity>());

        ListView list = findViewById(R.id.list_repo);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                UserActivity current = adapter.getItem(position);
                assert current != null;

                Uri uri = Uri.parse(current.getRepoLink());

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Redirecting to online repository", Toast.LENGTH_SHORT).show();

                Log.d("MainActivity", "Redirecting to " + uri.toString());
            }
        });

        ImageButton searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchText = findViewById(R.id.search_text);
                String searchQuery = source + searchText.toString().trim();

                UserAsyncTask task = new UserAsyncTask();
                task.execute(searchQuery);

                Log.d("MainActivity", searchQuery);
            }
        });
    }

    private class UserAsyncTask extends AsyncTask<String, Void, UserActivity> {

        @Override
        protected UserActivity doInBackground(String... strings) {
            if(strings[0] == null) {
                return null;
            }

            return UserQuery.fetchData(strings[0]);
        }

        @Override
        protected void onPostExecute(UserActivity userActivity) {
            super.onPostExecute(userActivity);
        }
    }
}
