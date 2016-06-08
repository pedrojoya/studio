package es.iessaladillo.pedrojoya.pr182.login;

import android.os.Handler;

import org.greenrobot.eventbus.EventBus;

import es.iessaladillo.pedrojoya.pr182.login.events.AuthenticatedEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningInEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningUpEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.SignedInEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.SignedUpEvent;

public class LoginFirebaseRepository implements LoginRepository {

    public LoginFirebaseRepository() {
        mEventBus = EventBus.getDefault();
    }

    private final EventBus mEventBus;

    @Override
    public void signIn(String email, String password) {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mEventBus.post(new SignedInEvent());
               //mEventBus.post(new ErrorSigningInEvent("Error signing in"));
            }
        }, 2000);
    }

    @Override
    public void signUp(String email, String password) {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mEventBus.post(new ErrorSigningUpEvent("Error signing up"));
            }
        }, 2000);
    }

    @Override
    public void checkAuthenticated() {
/*
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mEventBus.post(new AuthenticatedEvent());
            }
        }, 2000);
*/
    }

}
