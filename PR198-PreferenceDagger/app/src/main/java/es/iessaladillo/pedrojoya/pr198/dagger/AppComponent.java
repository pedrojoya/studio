package es.iessaladillo.pedrojoya.pr198.dagger;

import dagger.Component;
import es.iessaladillo.pedrojoya.pr198.actividades.MainActivity;

// Dagger generará automáticamente la clase DaggerAppComponent en base a esta interfaz.
// Dicha clase proporciona un builder que permite construir un objeto que implemente AppComponent.
// El builder incluirá un método para asignarle cada módulo requerido por el componente.
@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainActivity activity);
}
