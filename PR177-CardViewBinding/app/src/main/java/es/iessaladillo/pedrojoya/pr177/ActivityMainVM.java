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

public class ActivityMainVM extends BaseObservable {

    private String cuenta;
    private String propina;
    private String porcentaje;
    private String total;
    private String escote;
    private String comensales;
    private String moneda;

    private final String mSimboloDecimal;
    private final float mDefaultCuenta;
    private final int mDefaultPorcentaje;
    private final int mDefaultComensales;
    private final NumberFormat mFormateador;

    public ActivityMainVM(float defaultCuenta, int defaultPorcentaje, int defaultComensales) {
        mDefaultCuenta = defaultCuenta;
        mDefaultPorcentaje = defaultPorcentaje;
        mDefaultComensales = defaultComensales;
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
        mSimboloDecimal = decimalSymbols.getDecimalSeparator() + "";
        mFormateador = NumberFormat.getInstance(Locale.getDefault());
        moneda = decimalSymbols.getCurrencySymbol();
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

    @Bindable
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
        if (TextUtils.isEmpty(cuenta)) {
            cuenta = formatear(mDefaultCuenta);
        }
        calcular();
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.cuenta);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.propina);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.total);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
    }

    @Bindable
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
        this.porcentaje = porcentaje;
        if (TextUtils.isEmpty(porcentaje)) {
            porcentaje = formatear(mDefaultPorcentaje);
        }
        calcular();
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.cuenta);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.propina);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.total);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.porcentaje);
    }

    @Bindable
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.total);
    }

    @Bindable
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
        this.comensales = comensales;
        if (TextUtils.isEmpty(comensales)) {
            comensales = formatear(mDefaultComensales);
        }
        calcular();
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.comensales);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr177.BR.escote);
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


}
