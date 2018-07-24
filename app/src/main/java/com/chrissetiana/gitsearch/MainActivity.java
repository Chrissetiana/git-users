package com.chrissetiana.gitsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

/*
https://api.github.com/users/chrissetiana

img avatar_url""
name name""
bio bio""
repositories public_repos# -> url html_url""
gists public_gists# -> https://gist.github.com/
followers followers#
following following#

this is for each repo:
https://api.github.com/users/chrissetiana/repos
repo full_name"" (e.g. chrissetiana/acdss_ || name"" (e.g. acdss)
language language""

 */
