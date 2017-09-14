package es.iessaladillo.pedrojoya.pr095.data.model;

import android.os.Environment;

import java.io.File;

import static es.iessaladillo.pedrojoya.pr095.Constants.MP3_FILE_EXTENSION;

public class Song {

    private final String name;
    private final String duration;
    private final String author;
    private final String url;

    public Song(String name, String duration, String author, String url) {
        this.name = name;
        this.duration = duration;
        this.author = author;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name + " (" + duration + ")";
    }

    public File getPublicFile() {
        File directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC);
        File file = new File(directory, name + MP3_FILE_EXTENSION);
        return file.exists() ? file : null;
    }


}
