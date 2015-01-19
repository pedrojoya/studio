package es.iessaladillo.pedrojoya.pr109;

import retrofit.http.GET;

public interface EjemploRestInterface {
    @GET("/classes/Tareas")
    Resultado<Tarea> listarTareas();
}
