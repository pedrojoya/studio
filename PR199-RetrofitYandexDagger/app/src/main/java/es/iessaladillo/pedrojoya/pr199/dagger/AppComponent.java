package es.iessaladillo.pedrojoya.pr199.dagger;

import javax.inject.Singleton;

import dagger.Component;
import es.iessaladillo.pedrojoya.pr199.MainActivity;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
