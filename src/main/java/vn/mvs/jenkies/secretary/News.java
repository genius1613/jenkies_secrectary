package vn.mvs.jenkies.secretary;

/**
 * Created by tienbm on 16/09/2014.
 */
public class News {
    private String playTime =
            null;
    private String content= null;

    public String getPlayTime(){
        return this.playTime;
    }

    public String getContent(){
        return this.content;
    }

    public void setPlayTime(String playTime){
        this.playTime = playTime;
    }

    public void setContent(String content){
        this.content = content;
    }

    public News(){

    }

    public News(String time, String content){
        this.content = content;
        this.playTime = time;
    }
}
