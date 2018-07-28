package com.chrissetiana.gitusers;

public class UserActivity {

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

    private String userimage;
    private String username;
    private String bio;
    private int repo;
    private int gist;
    private int ufollowers;
    private int ufollowing;
    private String repoName;
    private String repoLanguage;
    private String repoLink;

    public UserActivity(String userimage, String username, String bio, int repo, int gist, int ufollowers, int ufollowing, String repoName, String repoLanguage, String repoLink) {
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
