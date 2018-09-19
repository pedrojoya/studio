package es.iessaladillo.pedrojoya.pr015.data.local.model;

public class Word {

    private long id;
    private final int photoResId;
    private final String english;
    private final String spanish;

    @SuppressWarnings("WeakerAccess")
    public Word(long id, int photoResId, String english, String spanish) {
        this.id = id;
        this.photoResId = photoResId;
        this.english = english;
        this.spanish = spanish;
    }

    public Word(int photoResId, String english, String spanish) {
        this(0, photoResId, english, spanish);
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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