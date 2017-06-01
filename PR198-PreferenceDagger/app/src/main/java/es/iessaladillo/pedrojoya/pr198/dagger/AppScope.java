package es.iessaladillo.pedrojoya.pr198.dagger;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

// Se define un ámbito durante el cuál debe inyectarse la misma instancia de cada objeto.
// En este caso el ámbito corresponde a mientras dure la aplicación.
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope { }
