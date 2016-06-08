package es.iessaladillo.pedrojoya.pr182.login.events;

import es.iessaladillo.pedrojoya.pr182.utils.ErrorEvent;

public class ErrorCheckingAuthenticatedEvent extends ErrorEvent {
    public ErrorCheckingAuthenticatedEvent(String errorMessage) {
        super(errorMessage);
    }
}
