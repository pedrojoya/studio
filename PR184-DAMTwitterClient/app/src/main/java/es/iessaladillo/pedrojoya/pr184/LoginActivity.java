package es.iessaladillo.pedrojoya.pr184;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.iessaladillo.pedrojoya.pr184.utils.dagger.DaggerManagersDaggerComponent;
import es.iessaladillo.pedrojoya.pr184.utils.dagger.ManagersDaggerComponent;
import es.iessaladillo.pedrojoya.pr184.utils.dagger.ManagersDaggerModule;
import es.iessaladillo.pedrojoya.pr184.utils.managers.uimessage.UIMessageManager;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btnLogin)
    TwitterLoginButton btnLogin;

    @Inject
    UIMessageManager uiMessageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Para Fabric.
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        // ****
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        DaggerManagersDaggerComponent
                .builder()
                .managersDaggerModule(new ManagersDaggerModule())
                .build()
                .inject(this);

        if (Twitter.getSessionManager().getActiveSession() != null) {
            navigateToMainScreen();
        }

        btnLogin.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                navigateToMainScreen();
            }

            @Override
            public void failure(TwitterException exception) {
                String msgError = String.format(getString(R.string.login_btnLogin_error), exception.getMessage());
                uiMessageManager.showMessage(btnLogin, msgError);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        btnLogin.onActivityResult(requestCode, resultCode, data);
    }

    public void navigateToMainScreen() {
        uiMessageManager.showMessage(btnLogin, "Conectado");

        //startActivity(new Intent(this, MainActivity.class));
    }

}
