package es.iessaladillo.pedrojoya.pr182.login;

import org.greenrobot.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningInEvent;
import es.iessaladillo.pedrojoya.pr182.login.events.ErrorSigningUpEvent;

import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterImplTest {

    @Mock
    private LoginView mView;
    @Mock
    private LoginInteractorImpl mInteractor;
    @Mock
    private EventBus mEventBus;



    private LoginPresenterImpl mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new LoginPresenterImpl(mView, mInteractor, mEventBus);
    }

    @Test
    public void testWantToSignIn() throws Exception {
        mPresenter.wantToSignIn("baldomero", "llegateligero");
        verify(mView).onLoadingStarted();
        verify(mInteractor).doSignIn("baldomero", "llegateligero");
    }

    @Test
    public void testWantToSignUp() throws Exception {
        mPresenter.wantToSignUp("baldomero", "llegateligero");
        verify(mView).onLoadingStarted();
        verify(mInteractor).doSignUp("baldomero", "llegateligero");
    }

    @Test
    public void testWantToCheckAuthenticated() throws Exception {
        mPresenter.wantToCheckAuthenticated();
        verify(mView).onLoadingStarted();
        verify(mInteractor).doCheckAuthenticated();
    }

    @Test
    public void testOnSignedIn() throws Exception {
        mPresenter.onSignedIn(null);
        verify(mView).onLoadingFinished();
        verify(mView).showUserHasSignedIn();
        verify(mView).navigateToContactsActivity();
    }

    @Test
    public void testOnErrorSigningIn() throws Exception {
        String mensaje = "La cosa esta muy mala";
        ErrorSigningInEvent event = mock(ErrorSigningInEvent.class);
        when(event.getErrorMessage()).thenReturn(mensaje);
        mPresenter.onErrorSigningIn(event);
        verify(mView).showErrorSigningIn(mensaje);
    }

    @Test
    public void testOnSignedUp() throws Exception {
        mPresenter.onSignedUp(null);
        verify(mView).onLoadingFinished();
        verify(mView).showUserHasSignedUp();
        verify(mView).navigateToContactsActivity();
    }

    @Test
    public void testOnErrorSigningUp() throws Exception {
        String mensaje = "La cosa esta muy mala";
        ErrorSigningUpEvent event = mock(ErrorSigningUpEvent.class);
        when(event.getErrorMessage()).thenReturn(mensaje);
        mPresenter.onErrorSigningUp(event);
        verify(mView).showErrorSigningUp(mensaje);
    }

    @Test
    public void testOnAuthenticated() throws Exception {
        mPresenter.onAuthenticated(null);
        verify(mView).onLoadingFinished();
        verify(mView).navigateToContactsActivity();
    }

}