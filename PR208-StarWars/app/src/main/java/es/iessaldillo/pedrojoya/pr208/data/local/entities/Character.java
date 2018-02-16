package es.iessaldillo.pedrojoya.pr208.data.local.entities;

public class Character {

    private String nombre;
    private String planeta;

    public Character(String nombre, String planeta) {
        this.nombre = nombre;
        this.planeta = planeta;
    }

    public static Character newInstance(String nombre, String planeta) {
        return new Character(nombre, planeta);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlaneta() {
        return planeta;
    }

    public void setPlaneta(String planeta) {
        this.planeta = planeta;
    }
}
