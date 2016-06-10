package es.iessaladillo.pedrojoya.pr182.login;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class LoginPresenterImplTest {

    private LoginView mView;
    private LoginPresenterImpl mPresenter;

    @Before
    public void setUp() throws Exception {
        mPresenter = new LoginPresenterImpl();
        mView = mock(LoginView.class);
    }

    @Test
    public void testWantToSignIn() throws Exception {

    }

    @Test
    public void testWantToSignUp() throws Exception {

    }

    @Test
    public void testWantToCheckAuthenticated() throws Exception {

    }

    @Test
    public void testOnViewAttached() throws Exception {
    }

    @Test
    public void testOnViewDetached() throws Exception {
        mPresenter.onViewDetached();
        assertNull(mView);
    }

    @Test
    public void testOnEvent() throws Exception {

    }

    @Test
    public void testOnEvent1() throws Exception {

    }

    @Test
    public void testOnEvent2() throws Exception {

    }

    @Test
    public void testOnEvent3() throws Exception {

    }

    @Test
    public void testOnEvent4() throws Exception {

    }
}