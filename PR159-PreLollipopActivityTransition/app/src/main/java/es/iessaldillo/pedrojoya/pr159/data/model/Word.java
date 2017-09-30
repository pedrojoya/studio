package es.iessaldillo.pedrojoya.pr159.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.photoResId);
        dest.writeString(this.english);
        dest.writeString(this.spanish);
    }

    @SuppressWarnings("WeakerAccess")
    protected Word(Parcel in) {
        this.photoResId = in.readInt();
        this.english = in.readString();
        this.spanish = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel source) {
            return new Word(source);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

}