package es.iessaladillo.pedrojoya.pr017.data.model;

public class Word {

    private final int photoResId;
    private final String english;
    private final String spanish;

    public Word(int photoResId, String english, String spanish) {
        this.photoResId = photoResId;
        this.english = english;
        this.spanish = spanish;
    }

    public int getPhotoResId() {
        return photoResId;
    }

    public String getEnglish() {
        return english;
    }

    public String getSpanish() {
        return spanish;
    }

}