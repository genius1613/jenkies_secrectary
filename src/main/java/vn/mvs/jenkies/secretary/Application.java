package vn.mvs.jenkies.secretary;

/**
 * Created by tienbm on 11/09/2014.
 */


import java.io.IOException;
import java.util.List;

import org.gitlab.api.http.GitlabHTTPRequestor;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabIssue;
import org.gitlab.api.models.GitlabSession;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hello world");
        try{
            GitlabAPI gitlabAPI = GitlabAPI.connect("http://git.mv","oVDMNuTQXsuoZ5Dqru7i");
            GitlabIssue gitlabSession = gitlabAPI.getIssue("39",106);
            System.out.println(gitlabSession.getAuthor().getName());
            List<GitlabIssue> gitlabIssueList = gitlabAPI.getIssueAssigned(true);
            for(GitlabIssue issue: gitlabIssueList){
                System.out.println(issue.getDescription());
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
