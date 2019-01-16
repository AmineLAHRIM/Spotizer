package com.materialviewinc.playband.classes;

public class Music {

    private int id;
    private String audio;
    private String title;

    public int getId() {
        return id;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        this.title = mTitle;
    }

    public Music(int id, String mAudio, String mTitle) {
        this.id = id;
        this.audio = mAudio;
        this.title = mTitle;
    }

}
