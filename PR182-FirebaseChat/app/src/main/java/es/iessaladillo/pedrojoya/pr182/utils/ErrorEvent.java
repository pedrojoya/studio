package es.iessaladillo.pedrojoya.pr182.utils;

public class ErrorEvent {

    private String errorMessage;

    public ErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
