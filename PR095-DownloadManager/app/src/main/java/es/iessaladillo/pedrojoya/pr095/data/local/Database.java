package es.iessaladillo.pedrojoya.pr095.data.local;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr095.data.model.Song;

public class Database {

    private static Database instance;
    private final ArrayList<Song> songs;

    private Database() {
        songs = new ArrayList<>();
        songs.add(new Song("Morning Mood", "3:43", "Grieg",
                "https://www.youtube.com/audiolibrary_download?vid=036500ffbf472dcc"));
        songs.add(new Song("Brahms Lullaby", "1:46", "Ron Meixsell",
                "https://www.youtube.com/audiolibrary_download?vid=9894a50b486c6136"));
        songs.add(new Song("Triangles", "3:05", "Silent Partner",
                "https://www.youtube.com/audiolibrary_download?vid=8c9219f54213cb4f"));
        songs.add(new Song("From Russia With Love", "2:26", "Huma-Huma",
                "https://www.youtube.com/audiolibrary_download?vid=4e8d1a0fdb3bbe12"));
        songs.add(new Song("Les Toreadors from Carmen", "2:21", "Bizet",
                "https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e73"));
        songs.add(new Song("Funeral March", "9:25", "Chopin",
                "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        songs.add(new Song("Dancing on Green Grass", "1:54", "The Green Orbs",
                "https://www.youtube.com/audiolibrary_download?vid=81cb790358aa232c"));
        songs.add(new Song("Roller Blades", "2:10", "Otis McDonald",
                "https://www.youtube.com/audiolibrary_download?vid=42b9cb1799a7110f"));
        songs.add(new Song("Aurora Borealis", "1:40", "Bird Creek",
                "https://www.youtube.com/audiolibrary_download?vid=71e7af02e3fde394"));
        songs.add(new Song("Sour Tennessee Red", "2:11", "John Deley and the 41",
                "https://www.youtube.com/audiolibrary_download?vid=f24590587cad9a9b"));
        songs.add(new Song("Water Lily", "2:09", "The 126ers",
                "https://www.youtube.com/audiolibrary_download?vid=5875315a21edd73b"));
        songs.add(new Song("Redhead From Mars", "3:29", "Silent Partner",
                "https://www.youtube.com/audiolibrary_download?vid=7b17c89cc371a1bc"));
        songs.add(new Song("Destructoid", "1:34", "MK2",
                "https://www.youtube.com/audiolibrary_download?vid=5ad1f342b4676fc1"));
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<Song> getSongs() {
        return songs;
    }

}
