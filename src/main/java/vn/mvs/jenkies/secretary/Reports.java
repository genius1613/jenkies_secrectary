package vn.mvs.jenkies.secretary;

import com.gtranslate.Language;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tienbm on 16/09/2014.
 */
public class Reports {

    private XMLConfiguration configuration;
    private XMLConfiguration playXmlConfiguration;

    private String systemPath;

    private AudioPlayer audioPlayer;

    public Reports(XMLConfiguration configuration) {
        systemPath = System.getProperty("user.dir");
        this.configuration = configuration;
    }

    public Reports() {
        systemPath = System.getProperty("user.dir");
        try {
            playXmlConfiguration = new XMLConfiguration(systemPath + "/conf/play.xml");

        } catch (ConfigurationException e) {
            e.printStackTrace();

        }

    }


    public List<News> loadMessages() {
        List<HierarchicalConfiguration> playMessage = playXmlConfiguration.configurationsAt("message.properties");
        List<News> messages = new ArrayList<News>();
        if (playMessage != null && playMessage.size() > 0) {
            for (HierarchicalConfiguration sub : playMessage) {
                String time = sub.getString("time");
                String content = sub.getString("content");
                News mess = new News(time, content);
                messages.add(mess);
            }

        } else {
            return null;
        }

        return messages;
    }

    public boolean checkFileIsExist(String folderDir, String fileName) {
        File file = new File(folderDir, fileName);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkFileIsExist(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }


    public void runReport() {
        try {
            audioPlayer = new AudioPlayer();
            List<News> news = loadMessages();
            for (News nW : news) {
                String fileName = audioPlayer.genenerateHash(nW.getContent());
                String filePath = this.systemPath + "/data/mp3/" + fileName + ".mp3";
//                check if file is not exist, get mp3 from google translate
                if (!checkFileIsExist(filePath)) {
                    //get mp3 file from google translate and save to local dir and then play
                    Language language = Language.getInstance();
                    audioPlayer.getMp3FromGoogleTrans(nW.getContent(), language.ENGLISH);

                }
                //do play Mp3 file
                if(checkFileIsExist(filePath)){
                    audioPlayer.play(filePath);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
