package com.materialviewinc.playband.classes;

import java.util.ArrayList;

public class Album {

    public static ArrayList<Album> allAlbumInstance;
    private int id;
    private String title;
    private String subTitle;
    private String imageAlbum;
    private int imageBtn_Play = -1;
    private ArrayList<Music> musics;


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        this.imageAlbum = imageAlbum;
    }

    public int getImageBtn_Play() {
        return imageBtn_Play;
    }

    public void setImageBtn_Play(int imageBtn_Play) {
        this.imageBtn_Play = imageBtn_Play;
    }

    public ArrayList<Music> getMusics() {
        return musics;
    }

    public void setMusics(ArrayList<Music> musics) {
        this.musics = musics;
    }


    public Album(int id, String title, String subTitle, String imageAlbum) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.imageAlbum = imageAlbum;
        if (allAlbumInstance != null) {
            allAlbumInstance.add(this);
        } else {
            allAlbumInstance = new ArrayList<Album>();
            allAlbumInstance.add(this);
        }
    }

    public Album(int id, String title, String subTitle, String imageAlbum, ArrayList<Music> musics) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.imageAlbum = imageAlbum;
        this.musics = musics;
        if (allAlbumInstance != null) {
            allAlbumInstance.add(this);

        } else {
            allAlbumInstance = new ArrayList<Album>();
            allAlbumInstance.add(this);
        }

    }

    public boolean hasMusics() {
        return musics != null;
    }

    public static Album findAlbum(int idAlbum) {
        Album foundedAlbum = null;
        for (int i = 0; i <= allAlbumInstance.size() - 1; i++) {
            if (allAlbumInstance.get(i).getId() == idAlbum) {
                foundedAlbum = allAlbumInstance.get(i);
                return foundedAlbum;
            }
        }
        return foundedAlbum;

    }

    public static int nextMusic(int idAlbum, int idMusic) {
        Album foundedAlbum = findAlbum(idAlbum);
        int idNextMusic = -1;
        int j = -1;
        ArrayList<Music> albumMusics = foundedAlbum.getMusics();
        for (int i = 0; i <= albumMusics.size() - 1; i++) {
            if (j == idMusic) {
                idNextMusic = albumMusics.get(i).getId();
                break;
            }
            j = albumMusics.get(i).getId();
        }
        return idNextMusic;
    }

    public static Music findMusicByIdInAlbum(int idAlbum, int idMusic) {
        Album foundedAlbum = findAlbum(idAlbum);
        ArrayList<Music> albumMusics = foundedAlbum.getMusics();
        Music foundedMusic = null;
        for (int i = 0; i <= albumMusics.size() - 1; i++) {
            foundedMusic = albumMusics.get(i);
            if (foundedMusic.getId() == idMusic) {
                return foundedMusic;
            }
        }
        return foundedMusic;
    }

    public static int getPositionMusicFromAlbum(int idAlbum, int idMusic) {
        Album foundedAlbum = findAlbum(idAlbum);
        ArrayList<Music> albumMusics = foundedAlbum.getMusics();
        int foundedPositionMusic = -1;
        int j = -1;
        for (int i = 0; i <= albumMusics.size() - 1; i++) {
            if (albumMusics.get(i).getId() == idMusic) {
                foundedPositionMusic++;
                j = 0;
                break;
            }
            foundedPositionMusic++;

        }
        if (j != -1) {
            return foundedPositionMusic;

        } else {
            return j;
        }
    }

}
