package vn.mvs.jenkies.secretary;

/**
 * Created by tienbm on 16/09/2014.
 */

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.gtranslate.Language;
import com.gtranslate.Audio;
import javazoom.jl.decoder.JavaLayerException;

import org.apache.commons.codec.digest.DigestUtils;

public class AudioPlayer {
    private InputStream inputStream;
    private Audio audio;
    private String systemPath;

    public AudioPlayer() {
        this.audio = Audio.getInstance();
        this.systemPath = System.getProperty("user.dir");
    }

    public void play(String fileName) {
        try {
            this.inputStream = new FileInputStream(fileName);
            audio.play(this.inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }

    public void getMp3FromGoogleTrans(String content, String language) {
        try {
            inputStream = this.audio.getAudio(content, language);
            writeMp3File(inputStream, systemPath + "/data/mp3/" + generateHashOfContent(content) + ".mp3");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void writeMp3File(InputStream inputStream, String outFilePath) {
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

    public String generateHashOfContent(String content) throws NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        md.update(content.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }


    public String genenerateHash(String content){
        return DigestUtils.md5Hex(content);
    }

}
