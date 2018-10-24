package es.iessaladillo.pedrojoya.pr097.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreBoard implements Parcelable {

    private int count;

    public int getScore() {
        return count;
    }

    public void incrementScore() {
        count++;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
    }

    public ScoreBoard() {
    }

    private ScoreBoard(Parcel in) {
        this.count = in.readInt();
    }

    public static final Parcelable.Creator<ScoreBoard> CREATOR = new Parcelable.Creator<ScoreBoard>() {
        @Override
        public ScoreBoard createFromParcel(Parcel source) {
            return new ScoreBoard(source);
        }

        @Override
        public ScoreBoard[] newArray(int size) {
            return new ScoreBoard[size];
        }
    };

}
