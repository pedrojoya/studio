package es.iessaladillo.pedrojoya.pr153;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

// Modelo Alumno.
public class Alumno extends BaseObservable {

    private  String nombre;
    private  String direccion;
    private  String foto;

    public Alumno(String nombre, String direccion, int edad, String urlFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.foto = urlFoto;

    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr153.BR.nombre);
    }

    @Bindable
    public String getFoto() {
        return foto;

    }
    public void setFoto(String foto) {
        this.foto = foto;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr153.BR.foto);
    }

    @Bindable
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr153.BR.direccion);
    }

}
