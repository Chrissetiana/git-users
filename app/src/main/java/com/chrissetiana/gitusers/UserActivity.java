package com.chrissetiana.gitusers;

import java.util.ArrayList;

public class UserActivity extends ArrayList {

    private String userimage; // img avatar_url""
    private String username; // name name""
    private String bio; // bio bio""
    private int repo; // repositories public_repos# -> url html_url""
    private int gist; // gists public_gists# -> https://gist.github.com/
    private int ufollowers; // followers followers#
    private int ufollowing; // following following#
    private String repoName;  // repo full_name"" (e.g. chrissetiana/acdss_ || name"" (e.g. acdss)
    private String repoLanguage; // language language""
    private String repoLink; // https://api.github.com/users/chrissetiana/repos

    UserActivity(String userimage, String username, String bio, int repo, int gist, int ufollowers, int ufollowing, String repoName, String repoLanguage, String repoLink) {
        this.userimage = userimage;
        this.username = username;
        this.bio = bio;
        this.repo = repo;
        this.gist = gist;
        this.ufollowers = ufollowers;
        this.ufollowing = ufollowing;
        this.repoName = repoName;
        this.repoLanguage = repoLanguage;
        this.repoLink = repoLink;
    }

    public String getUserimage() {
        return userimage;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public int getRepo() {
        return repo;
    }

    public int getGist() {
        return gist;
    }

    public int getUfollowers() {
        return ufollowers;
    }

    public int getUfollowing() {
        return ufollowing;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getRepoLanguage() {
        return repoLanguage;
    }

    public String getRepoLink() {
        return repoLink;
    }
}
