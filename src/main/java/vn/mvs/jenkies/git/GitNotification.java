package vn.mvs.jenkies.git;

/**
 * Created by tienbm on 11/09/2014.
 */

import org.apache.commons.configuration.*;
import org.apache.commons.configuration.ConfigurationException;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabIssue;
import org.gitlab.api.models.GitlabSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.*;
import java.io.IOException;
import java.io.SyncFailedException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class GitNotification {
    Logger logger = LoggerFactory.getLogger(GitNotification.class);

    private String gitAccount = null;
    private String gitPassword = null;
    private String gitHost = null;
    private String configPath = "conf/jenkies-config.xml";

    private XMLConfiguration xmlConfiguration;
    private GitlabAPI gitlabAPI;
    private GitlabSession gitlabSession;

    public GitNotification(){
        try{
            loadConfiguration();
            GitlabSession gitlabSession = GitlabAPI.connect(this.gitHost, this.gitAccount, this.gitPassword);
            GitlabAPI gitlabAPI = GitlabAPI.connect(this.gitHost, gitlabSession.getPrivateToken());
        }catch (ConfigurationException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    public GitNotification(String configPath){
        try{
            loadConfiguration(configPath);
            GitlabSession gitlabSession = GitlabAPI.connect(this.gitHost, this.gitAccount, this.gitPassword);
            GitlabAPI gitlabAPI = GitlabAPI.connect(this.gitHost, gitlabSession.getPrivateToken());

        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public String getGitAccount(){
        return gitAccount;
    }

    public String getGitPassword(){
        return gitPassword;
    }

    public void setGitAccount(String account){
        this.gitAccount=account;
    }

    public void loadConfiguration(String configPath){
        try{

            xmlConfiguration = new XMLConfiguration(configPath);
            List<HierarchicalConfiguration> properties = xmlConfiguration.configurationsAt("properties");
            logger.info(properties.toString());
            if (properties != null && properties.size() > 0) {
                for (HierarchicalConfiguration sub : properties) {
                    String fieldName = sub.getString("name");
                    logger.info(fieldName);
                    String fieldValue = sub.getString("value");
                    if (fieldName.equalsIgnoreCase("git-account")) {
                        this.gitAccount = fieldValue;
                    } else if (fieldName.equalsIgnoreCase("git-password")) {
                        this.gitPassword = fieldValue;
                    } else if (fieldName.equalsIgnoreCase("git-host")) {
                        this.gitHost = fieldValue;
                    }
                }
            }
        }catch (ConfigurationException e){
            e.printStackTrace();
        }
    }

    public void loadConfiguration() throws ConfigurationException, URISyntaxException, MalformedURLException{
        URL URL = getClass().getResource("../../../../").toURI().toURL();
        String path = URL.getPath()+"../../../" + this.configPath;
        xmlConfiguration = new XMLConfiguration(path);
        List<HierarchicalConfiguration> properties = xmlConfiguration.configurationsAt("properties");
        logger.info(properties.toString());
        if (properties != null && properties.size() > 0) {
            for (HierarchicalConfiguration sub : properties) {
                String fieldName = sub.getString("name");
                logger.info(fieldName);
                String fieldValue = sub.getString("value");
                if (fieldName.equalsIgnoreCase("git-account")) {
                    this.gitAccount = fieldValue;
                } else if (fieldName.equalsIgnoreCase("git-password")) {
                    this.gitPassword = fieldValue;
                } else if (fieldName.equalsIgnoreCase("git-host")) {
                    this.gitHost = fieldValue;
                }
            }
        }
    }
    public void run(){

    }

    public String getNotifcations() throws IOException{

        GitlabAPI gitlabAPI = GitlabAPI.connect(this.gitHost, "oVDMNuTQXsuoZ5Dqru7i");
        List<GitlabIssue> gitlabIssueList = gitlabAPI.getIssueAssigned(true);
        for(GitlabIssue issue: gitlabIssueList){
            System.out.println(issue.getDescription());

        }
        String message = "Hello, Mr" + this.gitAccount +". You're have " + gitlabIssueList.size() +
                " non closed issues ";
        return message;
    }
}
