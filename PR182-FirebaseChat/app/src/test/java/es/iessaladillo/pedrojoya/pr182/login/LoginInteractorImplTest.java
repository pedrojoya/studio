package es.iessaladillo.pedrojoya.pr182.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.MockAnnotationProcessor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class LoginInteractorImplTest {

    @Mock
    LoginRepository mRepository;

    LoginInteractorImpl mImplementor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mImplementor = new LoginInteractorImpl(mRepository);
    }

    @Test
    public void testDoSignIn() throws Exception {
        mImplementor.doSignIn("baldomero", "llegateligero");
        verify(mRepository).signIn("baldomero", "llegateligero");
    }

    @Test
    public void testDoSignUp() throws Exception {
        mImplementor.doSignUp("baldomero", "llegateligero");
        verify(mRepository).signUp("baldomero", "llegateligero");
    }

    @Test
    public void testDoCheckAuthenticated() throws Exception {
        mImplementor.doCheckAuthenticated();
        verify(mRepository).checkAuthenticated();
    }
}