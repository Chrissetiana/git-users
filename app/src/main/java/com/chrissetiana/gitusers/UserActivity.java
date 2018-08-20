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
        setUserimage(userimage);
        setUsername(username);
        setBio(bio);
        setRepo(repo);
        setGist(gist);
        setFollowers(ufollowers);
        setFollowing(ufollowing);
        setRepoName(repoName);
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getRepo() {
        return "Repositories: " + repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getGist() {
        return "Gists: " + gist;
    }

    public void setGist(String gist) {
        this.gist = gist;
    }

    public String getUfollowers() {
        return "Followers: " + ufollowers;
    }

    public void setFollowers(String ufollowers) {
        this.ufollowers = ufollowers;
    }

    public String getUfollowing() {
        return "Following: " + ufollowing;
    }

    public void setFollowing(String ufollowing) {
        this.ufollowing = ufollowing;
    }

    public ArrayList<String> getRepoName() {
        return repoName;
    }

    public void setRepoName(ArrayList<String> repoName) {
        this.repoName = repoName;
    }

    public String getRepoLanguage() {
        return repoLanguage;
    }

    public String getRepoLink() {
        return repoLink;
    }
}
