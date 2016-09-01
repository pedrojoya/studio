package es.iessaladillo.pedrojoya.pr184.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.iessaladillo.pedrojoya.pr184.App;
import es.iessaladillo.pedrojoya.pr184.R;
import es.iessaladillo.pedrojoya.pr184.main.MainActivity;
import es.iessaladillo.pedrojoya.pr184.utils.managers.uimessage.UIMessageManager;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    TwitterLoginButton btnLogin;

    @Inject
    UIMessageManager uiMessageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ((App) getApplication()).getManagersComponent().inject(this);

        if (Twitter.getSessionManager().getActiveSession() != null) {
            navigateToMainScreen();
        }

        Callback<TwitterSession> callback = new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                navigateToMainScreen();
            }

            @Override
            public void failure(TwitterException exception) {
                String msgError = String.format(getString(R.string.login_btnLogin_error), exception.getMessage());
                uiMessageManager.showMessage(btnLogin, msgError);
            }
        };
        btnLogin.setCallback(callback);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btnLogin.onActivityResult(requestCode, resultCode, data);
    }

    public void navigateToMainScreen() {
        //uiMessageManager.showMessage(btnLogin, "Conectado");
        MainActivity.start(this);
    }

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

}
