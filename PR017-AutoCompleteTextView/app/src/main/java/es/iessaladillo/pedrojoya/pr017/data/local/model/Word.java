package es.iessaladillo.pedrojoya.pr017.data.local.model;

@SuppressWarnings("unused")
public class Word {

    private long id;
    private final int photoResId;
    private final String english;
    private final String spanish;

    public Word(long id, int photoResId, String english, String spanish) {
        this.id = id;
        this.photoResId = photoResId;
        this.english = english;
        this.spanish = spanish;
    }

    public Word(int photoResId, String english, String spanish) {
        this(0, photoResId, english, spanish);
    }

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

    @SuppressWarnings("unused")
    public String getSpanish() {
        return spanish;
    }

    @Override
    public String toString() {
        return english;
    }
}