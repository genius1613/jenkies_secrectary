package vn.mvs.jenkies.secretary;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import javazoom.jl.decoder.JavaLayerException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import vn.mvs.jenkies.git.GitNotification;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;

/**
 * Created by tienbm on 16/09/2014.
 */
public class Reports {

    private XMLConfiguration configuration;

    public Reports(XMLConfiguration configuration) {
        String path = System.getProperty("user.dir");
        this.configuration = configuration;
    }

    public List<Message> loadContent() {
        List<HierarchicalConfiguration> properties = configuration.configurationsAt("email.properties");
        List<Message> messages = new ArrayList<Message>();
        if (properties != null && properties.size() > 0) {
            for (HierarchicalConfiguration sub : properties) {
                String time = sub.getString("time");
                String content = sub.getString("content");
                Message mess = new Message(time, content);
                messages.add(mess);
            }

        } else {
            return null;
        }

        return messages;
    }

    public void saveReportToMp3 (List<Message> messages) {
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            for (Message message : messages) {

            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }


    }

    public void runReport(){
        try {
            String path = System.getProperty("user.dir");
            GitNotification gitNotification = new GitNotification();
            String message = gitNotification.getNotifcations();
            Audio audio = Audio.getInstance();
            InputStream sound = audio.getAudio("Good morning, Mr Tienbm." + " You're two issues which must be solved today", Language.ENGLISH);
//            InputStream sound = audio.getAudio(message, Language.ENGLISH);
            audio.play(sound);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public void writeMp3File(InputStream inputStream, String outFilePath){
        byte[] outBytes = inputStreamToByteArray(inputStream);
        byteArrayToFile(outBytes, outFilePath);
    }

    public byte[] inputStreamToByteArray(InputStream inStream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void byteArrayToFile(byte[] byteArray, String outFilePath) {

        try {
            FileOutputStream fos = new FileOutputStream(outFilePath);
            fos.write(byteArray);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
