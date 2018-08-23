package com.chrissetiana.gitusers;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

public class UserQuery {

    private static String userData, repoData;

    public static List<UserActivity> fetchData(String userStr, String repoStr) {
        URL userUrl = buildUrl(userStr);
        URL repoUrl = buildUrl(repoStr);

        userData = null;
        repoData = null;

        try {
            userData = buildHttp(userUrl);
            repoData = buildHttp(repoUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getJSONData(userData, repoData);
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


    private static List<UserActivity> getJSONData(String userUrl, String repoUrl) {
        if (TextUtils.isEmpty(userUrl)) {
            return null;
        }

        List<UserActivity> users = new ArrayList<>();

        try {
            String image = getUserData("avatar_url");
            String name = getUserData("name");
            String bio = getUserData("bio");
            String repo = getUserData("public_repos");
            String gist = getUserData("public_gists");
            String followers = getUserData("followers");
            String following = getUserData("following");

            JSONArray gitRepo = new JSONArray(repoUrl);
            int repoNum = gitRepo.length();

            List<String> repoList = new ArrayList<>();

            for (int i = 0; i < repoNum; i++) {
                JSONObject repoDetail = gitRepo.getJSONObject(i);
                // String repoName = getRepoData(i, "full_name");

                String repoName = repoDetail.optString("full_name");
                String repoLang = repoDetail.optString("language");
                String repoLink = repoDetail.optString("html_url");

                List<String> repositories = new ArrayList<>();
                repositories.add(repoName);
                repositories.add(repoLang);
                repositories.add(repoLink);

                String[] repos = repositories.toArray(new String[repositories.size()]);

                for (int j = 0; j < repositories.size(); j++) {
                    repoList.add(repos[j]);
                }
            }

            UserActivity user = new UserActivity(image, name, bio, repo, gist, followers, following, repoList);
            users.add(user);
            Log.d("UserQuery", "You've reached JSON method" + "\n" + image + "\n" + name + "\n" + bio + "\n" + repo + "\n" + gist + "\n" + followers + "\n" + following + "\n" + repoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;
    }

    private static String getUserData(String key) throws JSONException {
        JSONObject gitUser = new JSONObject(userData);
        return gitUser.optString(key);
    }

    /*private static String getRepoData(int i, String key) throws JSONException {
        JSONArray gitRepo = new JSONArray(repoData);
        JSONObject repoDetail = gitRepo.getJSONObject(i);
        return repoDetail.optString(key);
    }*/
}
