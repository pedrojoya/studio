package es.iessaladillo.pedrojoya.pr187.events;

public class ProgressEvent {

    private final int numTrabajo;

    public ProgressEvent(int numTrabajo) {
        this.numTrabajo = numTrabajo;
    }

    public int getNumTrabajo() {
        return numTrabajo;
    }

}
