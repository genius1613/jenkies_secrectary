package vn.mvs.jenkies;

/**
 * Created by tienbm on 11/09/2014.
 */


import java.io.IOException;
import java.io.InputStream;
import java.io.*;

import javazoom.jl.decoder.JavaLayerException;
import com.gtranslate.Language;
import com.gtranslate.Audio;
import vn.mvs.jenkies.git.GitNotification;


public class Application {
    public static void main(String[] args) {
        System.out.println("Hello world");
        try {
            String path = System.getProperty("user.dir");
            GitNotification gitNotification = new GitNotification();
            String message = gitNotification.getNotifcations();
            Audio audio = Audio.getInstance();
            InputStream sound = audio.getAudio("Good morning, Mr Tienbm." + " You're two issues which must be solved today", Language.ENGLISH);
//            InputStream sound = audio.getAudio(message, Language.ENGLISH);
            byte[] resultBytes = inputStreamToByteArray(sound);
            byteArrayToFile(resultBytes, path + "/data/mp3/test.mp3");
            audio.play(sound);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }

    public static byte[] inputStreamToByteArray(InputStream inStream) {
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

    public static void byteArrayToFile(byte[] byteArray, String outFilePath) {

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
