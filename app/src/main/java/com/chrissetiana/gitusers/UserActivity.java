package com.chrissetiana.gitusers;

import java.util.List;

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
    private List<String> repoName;  // full_name"" (e.g. chrissetiana/acdss_ || name"" (e.g. acdss)
    private String repoLanguage; // language""
    private String repoLink; // html_url""

    UserActivity(String userimage, String username, String bio, String repo, String gist, String ufollowers, String ufollowing, List<String> repoName) {
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

    private void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    private void setBio(String bio) {
        this.bio = bio;
    }

    public String getRepo() {
        return "Repositories: " + repo;
    }

    private void setRepo(String repo) {
        this.repo = repo;
    }

    public String getGist() {
        return "Gists: " + gist;
    }

    private void setGist(String gist) {
        this.gist = gist;
    }

    public String getUfollowers() {
        return "Followers: " + ufollowers;
    }

    private void setFollowers(String ufollowers) {
        this.ufollowers = ufollowers;
    }

    public String getUfollowing() {
        return "Following: " + ufollowing;
    }

    public void setFollowing(String ufollowing) {
        this.ufollowing = ufollowing;
    }

    public List<String> getRepoName() {
        return repoName;
    }

    private void setRepoName(List<String> repoName) {
        this.repoName = repoName;
    }

    public String getRepoLanguage() {
        return repoLanguage;
    }

    public String getRepoLink() {
        return repoLink;
    }
}
