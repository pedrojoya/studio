package es.iessaladillo.pedrojoya.pr017;

class Concepto {

    // Propiedades.
    private int fotoResId;
    private String english;
    private String spanish;

    // Constructores.
    public Concepto(int fotoResId, String english, String spanish) {
        this.fotoResId = fotoResId;
        this.english = english;
        this.spanish = spanish;
    }

    public Concepto() {
    }

    // Getters y Setters.
    public int getFotoResId() {
        return fotoResId;
    }

    public void setFotoResId(int fotoResId) {
        this.fotoResId = fotoResId;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getSpanish() {
        return spanish;
    }

    public void setSpanish(String spanish) {
        this.spanish = spanish;
    }

}
