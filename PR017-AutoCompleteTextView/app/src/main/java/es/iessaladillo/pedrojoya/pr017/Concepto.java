package es.iessaladillo.pedrojoya.pr017;

class Concepto {

    // Propiedades.
    private final int fotoResId;
    private final String english;
    private String spanish;

    // Constructores.
    public Concepto(int fotoResId, String english, String spanish) {
        this.fotoResId = fotoResId;
        this.english = english;
        this.spanish = spanish;
    }

    // Getters y Setters.
    public int getFotoResId() {
        return fotoResId;
    }

    public String getEnglish() {
        return english;
    }

}