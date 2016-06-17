package es.iessaladillo.pedrojoya.pr184.utils.dagger;

import javax.inject.Singleton;

import dagger.Component;
import es.iessaladillo.pedrojoya.pr184.LoginActivity;

// Componente de inyección de Dagger2 para el módulo de Managers.
@Singleton
@Component(modules = {ManagersDaggerModule.class})
public interface ManagersDaggerComponent {
    void inject(LoginActivity activity);
}
