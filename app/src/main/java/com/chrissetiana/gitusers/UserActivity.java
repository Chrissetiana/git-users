package com.chrissetiana.gitusers;

import java.util.ArrayList;

public class UserActivity {

    // https://api.github.com/users/chrissetiana
    private String userimage; // avatar_url""
    private String username; // name""
    private String bio; // bio""
    private String repo; // public_repos#
    private String gist; // public_gists#
    private String ufollowers; // followers#
    private String ufollowing; // following#
    // https://api.github.com/users/chrissetiana/repos
    private ArrayList<String> repoName;  // full_name"" (e.g. chrissetiana/acdss_ || name"" (e.g. acdss)
    private String repoLanguage; // language""
    private String repoLink; // html_url""

    UserActivity(String userimage, String username, String bio, String repo, String gist, String ufollowers, String ufollowing, ArrayList<String> repoName) {
        this.userimage = userimage;
        this.username = username;
        this.bio = bio;
        this.repo = repo;
        this.gist = gist;
        this.ufollowers = ufollowers;
        this.ufollowing = ufollowing;
        this.repoName = repoName;
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

    public String getRepo() {
        return "Repositories: " + repo;
    }

    public String getGist() {
        return "Gists: " + gist;
    }

    public String getUfollowers() {
        return "Followers: " + ufollowers;
    }

    public String getUfollowing() {
        return "Following: " + ufollowing;
    }

    public ArrayList<String> getRepoName() {
        return repoName;
    }

    public String getRepoLanguage() {
        return repoLanguage;
    }

    public String getRepoLink() {
        return repoLink;
    }
}
