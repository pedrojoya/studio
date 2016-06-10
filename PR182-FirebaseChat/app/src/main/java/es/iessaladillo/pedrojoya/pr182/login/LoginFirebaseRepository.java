package es.iessaladillo.pedrojoya.pr182.login;

import android.os.Handler;
import android.support.annotation.VisibleForTesting;

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

    @VisibleForTesting
    public LoginFirebaseRepository(EventBus eventBus) {
        mEventBus = eventBus;
    }

    private final EventBus mEventBus;

    @Override
    public void signIn(String email, String password) {
        // TODO Work with Firebase.
        if ("baldomero".equals(email) && "llegateligero".equals(password)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEventBus.post(new SignedInEvent());
                    //mEventBus.post(new ErrorSigningInEvent("Error signing in"));
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEventBus.post(new ErrorSigningInEvent("Error signing in"));
                }
            }, 2000);
        }
    }

    @Override
    public void signUp(String email, String password) {
        // TODO Work with Firebase.
        if ("baldomero".equals(email) && "llegateligero".equals(password)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEventBus.post(new ErrorSigningInEvent("Error signing up"));
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEventBus.post(new SignedUpEvent());
                }
            }, 2000);
        }
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
