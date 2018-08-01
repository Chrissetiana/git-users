package com.chrissetiana.gitusers;

import java.util.ArrayList;

public class UserActivity extends ArrayList {

    // https://api.github.com/users/chrissetiana
    private String userimage; // avatar_url""
    private String username; // name""
    private String bio; // bio""
    private int repo; // public_repos#
    private int gist; // public_gists#
    private int ufollowers; // followers#
    private int ufollowing; // following#
    // https://api.github.com/users/chrissetiana/repos
    private String repoName;  // repo full_name"" (e.g. chrissetiana/acdss_ || name"" (e.g. acdss)
    private String repoLanguage; // language language""
    private String repoLink; // url html_url""

    public UserActivity(String userimage, String username, String bio, int repo, int gist, int ufollowers, int ufollowing) {
        this.userimage = userimage;
        this.username = username;
        this.bio = bio;
        this.repo = repo;
        this.gist = gist;
        this.ufollowers = ufollowers;
        this.ufollowing = ufollowing;
    }

    public UserActivity(String repoName, String repoLanguage, String repoLink) {
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
