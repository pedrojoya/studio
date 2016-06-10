package es.iessaladillo.pedrojoya.pr182.login;

import android.os.Handler;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningInEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningUpEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.SignedInEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.SignedUpEvent;

import static org.junit.Assert.*;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

public class LoginFirebaseRepositoryTest {

    @Mock
    EventBus mEventBus;

    LoginFirebaseRepository mRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mRepository = new LoginFirebaseRepository(mEventBus);
    }

    @Test
    @Ignore("not implemented yet")
    public void testSignInSuccessfully() throws Exception {
        mRepository.signIn("baldomero", "llegateligero");
        verify(mEventBus).post(isA(SignedInEvent.class));
    }

    @Test
    @Ignore("not implemented yet")
    public void testSignInError() throws Exception {
        mRepository.signIn("germangines", "llegateligero");
        verify(mEventBus).post(isA(ErrorSigningInEvent.class));
    }

    @Test
    @Ignore("not implemented yet")
    public void testSignUpSuccesfully() throws Exception {
        mRepository.signUp("germangines", "lospapelespordelante");
        verify(mEventBus).post(isA(SignedUpEvent.class));
    }

    @Test
    @Ignore("not implemented yet")
    public void testSignUpError() throws Exception {
        mRepository.signUp("baldomero", "llegateligero");
        verify(mEventBus).post(isA(ErrorSigningUpEvent.class));
    }

    @Test
    @Ignore("not implemented yet")
    public void testCheckAuthenticated() throws Exception {
        fail("Need implementing Firebase");
    }
}