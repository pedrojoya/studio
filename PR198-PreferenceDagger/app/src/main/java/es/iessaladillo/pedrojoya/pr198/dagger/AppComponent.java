package es.iessaladillo.pedrojoya.pr198.dagger;

import javax.inject.Singleton;

import dagger.Component;
import es.iessaladillo.pedrojoya.pr198.actividades.MainActivity;

// Dagger generará automáticamente la clase DaggerAppComponent en base a esta interfaz.
// Dicha clase proporciona un builder que permite construir un objeto que implemente AppComponent.
// El builder incluirá un método para asignarle cada módulo requerido por el componente.
@SuppressWarnings("unused")
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // Por cada clase donde se vaya a inyectar algo proporcionado por los módulos del
    // componente.
    void inject(MainActivity activity);
}
