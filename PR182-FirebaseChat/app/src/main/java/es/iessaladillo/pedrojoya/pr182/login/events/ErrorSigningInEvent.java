package es.iessaladillo.pedrojoya.pr182.login.events;

import es.iessaladillo.pedrojoya.pr182.utils.ErrorEvent;

public class ErrorSigningInEvent extends ErrorEvent{
    public ErrorSigningInEvent(String errorMessage) {
        super(errorMessage);
    }
}
