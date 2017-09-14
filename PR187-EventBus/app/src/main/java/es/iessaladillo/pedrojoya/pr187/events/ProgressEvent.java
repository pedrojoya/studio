package es.iessaladillo.pedrojoya.pr187.events;

public class ProgressEvent {

    private final int stepNumber;

    public ProgressEvent(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getStepNumber() {
        return stepNumber;
    }

}
