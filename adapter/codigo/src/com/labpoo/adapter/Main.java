package com.labpoo.adapter;

public class Main {

    public static void main(String[] args) {
        ReprodutorMidia reprodutor = new ReprodutorMp3();

        reprodutor.reproduzir("mp3", "musica.mp3");
        reprodutor.reproduzir("mp4", "video.mp4");
        reprodutor.reproduzir("vlc", "filme.vlc");
        reprodutor.reproduzir("avi", "clipe.avi");
    }
}
