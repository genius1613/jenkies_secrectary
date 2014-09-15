package vn.mvs.jenkies.secretary;

/**
 * Created by tienbm on 11/09/2014.
 */


import java.io.IOException;
import java.util.List;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabIssue;

import com.gtranslate.Translator;
import com.gtranslate.Language;
import com.gtranslate.Audio;
import org.gitlab.api.models.GitlabSession;
import vn.mvs.jenkies.git.GitNotification;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hello world");
        try{
            GitNotification gitNotification = new GitNotification();
            String message = gitNotification.getNotifcations();
            Audio audio = Audio.getInstance();
//            InputStream sound  = audio.getAudio("Good morning, Mr Tienbm."+" You're two issues which must be solved today", Language.ENGLISH);
            InputStream sound = audio.getAudio(message, Language.ENGLISH);
            audio.play(sound);

        }catch (IOException e)
        {
            e.printStackTrace();
        }catch (JavaLayerException e)
        {
            e.printStackTrace();
        }

    }
}
