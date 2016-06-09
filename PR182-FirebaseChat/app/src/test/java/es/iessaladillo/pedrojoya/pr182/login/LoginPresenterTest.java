package es.iessaladillo.pedrojoya.pr182.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    @Mock
    private LoginView mLoginView;

    private LoginPresenterImpl mLoginPresenter;

    @Before
    public void setupLoginPresenter() {
        // Inject mocks.
        MockitoAnnotations.initMocks(this);
        // Get a reference to the class under test
        mLoginPresenter = new LoginPresenterImpl();
        mLoginPresenter.onViewAttached(mLoginView);
    }

    @Test
    public void clickOnBtnSignIn_CallsOnUserSignInInView() {
        mLoginPresenter.wantToSignIn("baldomero@gmail.com", "quilloque");
        verify(mLoginView).onLoadingStarted();
        verify(mLoginView).showUserHasSignedIn();
        verify(mLoginView).onLoadingFinished();
    }

    @Test
    public void clickOnBtnSignUp_CallsOnUserSignInInView() {
        mLoginPresenter.wantToSignUp("baldomero@gmail.com", "quilloque");
        verify(mLoginView).onLoadingStarted();
        verify(mLoginView).showUserHasSignedUp();
        verify(mLoginView).onLoadingFinished();
    }

}
