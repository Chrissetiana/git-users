package com.chrissetiana.gitusers;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class UserQuery {

    private final static String TAG = UserQuery.class.getSimpleName();

    public static UserActivity fetchData(String src) {
        URL url = buildUrl(src);
        String response = null;

        try {
            response = buildHttp(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getJSONData(response);
    }

    private static URL buildUrl(String str) {
        URL url;

        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        return url;
    }

    private static String buildHttp(URL url) throws IOException {
        String response = "";

        if (url == null) {
            return response;
        }

        HttpURLConnection connection = null;
        InputStream stream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                stream = connection.getInputStream();
                response = readStream(stream);
            } else {
                Log.e("UserQuery", "Error code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (stream != null) {
                stream.close();
            }
        }

        return response;
    }

    private static String readStream(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();

        if (stream != null) {
            InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }


    private static UserActivity getJSONData(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }

        try {
            JSONObject object = new JSONObject(source);

            String image = object.optString("name");
            String name = object.optString("avatar_url");
            String bio = object.optString("bio");
            int repo = object.optInt("public_repos");
            int gist = object.optInt("public_gists");
            int followers = object.optInt("followers");
            int following = object.optInt("following");

            new UserActivity(image, name, bio, repo, gist, followers, following);

            String repoName = object.optString("full_name");
            String repoLanguage = object.optString("language");
            String repoUrl = object.optString("html_url");

            new UserActivity(repoName, repoLanguage, repoUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
