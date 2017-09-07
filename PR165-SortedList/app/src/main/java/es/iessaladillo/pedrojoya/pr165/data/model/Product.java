package es.iessaladillo.pedrojoya.pr165.data.model;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("unused")
public class Product implements Parcelable {

    private long id;
    private String name;
    private float quantity;
    private String unit;
    private boolean bought;

    public Product(long id, String name, float quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.bought = false;
    }

    public long getId() {
        return id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public void toggleBought() {
        this.bought = !this.bought;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeFloat(this.quantity);
        dest.writeString(this.unit);
        dest.writeByte(bought ? (byte) 1 : (byte) 0);
    }

    protected Product(Parcel in) {
        this.name = in.readString();
        this.quantity = in.readFloat();
        this.unit = in.readString();
        this.bought = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

}
