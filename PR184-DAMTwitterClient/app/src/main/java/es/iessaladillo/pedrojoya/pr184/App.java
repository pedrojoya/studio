package es.iessaladillo.pedrojoya.pr184;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import es.iessaladillo.pedrojoya.pr184.utils.dagger.DaggerManagersDaggerComponent;
import es.iessaladillo.pedrojoya.pr184.utils.dagger.ManagersDaggerComponent;
import es.iessaladillo.pedrojoya.pr184.utils.dagger.ManagersDaggerModule;
import io.fabric.sdk.android.Fabric;

public class App extends Application{

    private ManagersDaggerComponent mManagersComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initFabric();
        createManagersComponent();
    }

    private void createManagersComponent() {
        mManagersComponent = DaggerManagersDaggerComponent
                .builder()
                .managersDaggerModule(new ManagersDaggerModule())
                .build();
    }

    private void initFabric() {
        // Para Fabric.
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        // ****
    }

    public ManagersDaggerComponent getManagersComponent() {
        return mManagersComponent;
    }

}
