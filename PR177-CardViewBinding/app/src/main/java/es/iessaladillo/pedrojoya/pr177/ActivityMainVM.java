package es.iessaladillo.pedrojoya.pr177;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@SuppressWarnings({"WeakerAccess", "unused", "UnusedParameters", "UnusedAssignment"})
public class ActivityMainVM extends BaseObservable implements Parcelable {

    private String cuenta;
    private String propina;
    private String porcentaje;
    private String total;
    private String escote;
    private String comensales;
    private String moneda;

    private final float mDefaultCuenta;
    private final int mDefaultPorcentaje;
    private final int mDefaultComensales;
    private final NumberFormat mFormateador;

    @SuppressWarnings("SameParameterValue")
    public ActivityMainVM(float defaultCuenta, int defaultPorcentaje, int defaultComensales) {
        mDefaultCuenta = defaultCuenta;
        mDefaultPorcentaje = defaultPorcentaje;
        mDefaultComensales = defaultComensales;
        mFormateador = NumberFormat.getInstance(Locale.getDefault());
        setLocaleDefaultMoneda();
        cuenta = formatear(mDefaultCuenta);
        porcentaje = formatear(mDefaultPorcentaje);
        comensales = formatear(mDefaultComensales);
        calcular();
    }

    private String formatear(float valor) {
        return String.format(Locale.getDefault(), "%.2f", valor);
    }

    private String formatear(int valor) {
        return String.valueOf(valor);
    }

    @Bindable({"porcentaje"})
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        if (TextUtils.isEmpty(cuenta)) {
            cuenta = formatear(mDefaultCuenta);
        }
        this.cuenta = cuenta;
        calcular();
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.cuenta);
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.propina);
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.total);
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
    }

    @Bindable({"cuenta"})
    public String getPropina() {
        return propina;
    }

    public void setPropina(String propina) {
        this.propina = propina;
        calcular();
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.propina);
    }

    @Bindable
    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        if (TextUtils.isEmpty(porcentaje)) {
            porcentaje = formatear(mDefaultPorcentaje);
        }
        this.porcentaje = porcentaje;
        calcular();
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.cuenta);
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.propina);
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.total);
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.porcentaje);
    }

    @Bindable({"cuenta"})
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.total);
    }

    @Bindable({"cuenta", "comensales"})
    public String getEscote() {
        return escote;
    }

    public void setEscote(String escote) {
        this.escote = escote;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
    }

    @Bindable
    public String getComensales() {
        return comensales;
    }

    public void setComensales(String comensales) {
        if (TextUtils.isEmpty(comensales)) {
            comensales = formatear(mDefaultComensales);
        }
        this.comensales = comensales;
        calcular();
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.comensales);
//        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
    }

    @Bindable
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.moneda);
    }

    private void calcular() {
        float fCuenta = mDefaultCuenta;
        try {
            fCuenta = (mFormateador.parse(cuenta)).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        float fPorcentaje = mDefaultPorcentaje;
        try {
            fPorcentaje = (mFormateador.parse(porcentaje)).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        float fPropina = (fCuenta * fPorcentaje) / 100;
        float fTotal = fCuenta + fPropina;
        propina = formatear(fPropina);
        total = formatear(fTotal);
        calcularEscote(fTotal);
    }

    private void calcularEscote(float fTotal) {
        float fComensales = mDefaultComensales;
        try {
            fComensales = (mFormateador.parse(comensales)).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        float fEscote = fTotal / fComensales;
        escote = formatear(fEscote);
    }

    public void redondearEscote(View v) {
        try {
            float fEscote = (mFormateador.parse(escote)).floatValue();
            float nuevoEscote = (float) Math.floor(fEscote);
            if (nuevoEscote != fEscote) {
                nuevoEscote += 1;
            }
            setEscote(formatear(nuevoEscote));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void redondearTotal(View v) {
        try {
            float fTotal = (mFormateador.parse(total)).floatValue();
            float nuevoTotal = (float) Math.floor(fTotal);
            if (nuevoTotal != fTotal) {
                nuevoTotal += 1;
            }
            setTotal(formatear(nuevoTotal));
            calcularEscote(nuevoTotal);
            notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void resetTotal(View v) {
        setCuenta(formatear(mDefaultCuenta));
        setPorcentaje(formatear(mDefaultPorcentaje));
    }

    public void resetEscote(View v) {
        setComensales(formatear(mDefaultComensales));
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cuenta);
        dest.writeString(this.propina);
        dest.writeString(this.porcentaje);
        dest.writeString(this.total);
        dest.writeString(this.escote);
        dest.writeString(this.comensales);
        dest.writeFloat(this.mDefaultCuenta);
        dest.writeInt(this.mDefaultPorcentaje);
        dest.writeInt(this.mDefaultComensales);
    }

    protected ActivityMainVM(Parcel in) {
        this.cuenta = in.readString();
        this.propina = in.readString();
        this.porcentaje = in.readString();
        this.total = in.readString();
        this.escote = in.readString();
        this.comensales = in.readString();
        this.mDefaultCuenta = in.readFloat();
        this.mDefaultPorcentaje = in.readInt();
        this.mDefaultComensales = in.readInt();
        mFormateador = NumberFormat.getInstance(Locale.getDefault());
        setLocaleDefaultMoneda();
    }

    private void setLocaleDefaultMoneda() {
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols(Locale.getDefault());
        this.moneda = decimalSymbols.getCurrencySymbol();
    }

    public void resetMoneda() {
        setLocaleDefaultMoneda();
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.moneda);
    }

    public static final Parcelable.Creator<ActivityMainVM> CREATOR = new Parcelable
            .Creator<ActivityMainVM>() {
        @Override
        public ActivityMainVM createFromParcel(Parcel source) {
            return new ActivityMainVM(source);
        }

        @Override
        public ActivityMainVM[] newArray(int size) {
            return new ActivityMainVM[size];
        }
    };
}
