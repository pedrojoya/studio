package es.iessaladillo.pedrojoya.pr182.login.events;

import es.iessaladillo.pedrojoya.pr182.utils.ErrorEvent;

public class ErrorSigningUpEvent extends ErrorEvent {
    public ErrorSigningUpEvent(String errorMessage) {
        super(errorMessage);
    }
}
