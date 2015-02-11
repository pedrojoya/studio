package es.iessaladillo.pedrojoya.pr113.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tarea implements Parcelable {
    private String concepto;
    private String createdAt;
    private String objectId;
    private String responsable;
    private String updatedAt;

    public Tarea(String concepto, String createdAt, String objectId, String responsable, String updatedAt) {
        this.concepto = concepto;
        this.createdAt = createdAt;
        this.objectId = objectId;
        this.responsable = responsable;
        this.updatedAt = updatedAt;
    }

    public Tarea(String concepto, String responsable) {
        this.concepto = concepto;
        this.responsable = responsable;
    }

    public Tarea() {
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.concepto);
        dest.writeString(this.createdAt);
        dest.writeString(this.objectId);
        dest.writeString(this.responsable);
        dest.writeString(this.updatedAt);
    }

    private Tarea(Parcel in) {
        this.concepto = in.readString();
        this.createdAt = in.readString();
        this.objectId = in.readString();
        this.responsable = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<Tarea> CREATOR = new Creator<Tarea>() {
        public Tarea createFromParcel(Parcel source) {
            return new Tarea(source);
        }

        public Tarea[] newArray(int size) {
            return new Tarea[size];
        }
    };
}
