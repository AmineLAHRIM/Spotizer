package com.materialviewinc.playband.classes;

public class Constant {

    private static int selectedIdAlbum = -1;
    private static int selectedIdMusic = -1;

    public static int getSelectedIdAlbum() {
        return selectedIdAlbum;
    }

    public static void setSelectedIdAlbum(int selectedIdAlbum) {
        Constant.selectedIdAlbum = selectedIdAlbum;
    }

    public static int getSelectedIdMusic() {
        return selectedIdMusic;
    }

    public static void setSelectedIdMusic(int selectedIdMusic) {
        Constant.selectedIdMusic = selectedIdMusic;
    }

    public static void resetMusic() {
        Constant.selectedIdMusic = -1;
    }

    public static void resetAlbum() {
        Constant.selectedIdAlbum = -1;
    }

    public static void resetAll(){
        Constant.selectedIdMusic = -1;
        Constant.selectedIdAlbum = -1;

    }
}
