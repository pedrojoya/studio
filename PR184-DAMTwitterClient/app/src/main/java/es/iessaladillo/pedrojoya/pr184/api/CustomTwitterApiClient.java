package es.iessaladillo.pedrojoya.pr184.api;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

// Extiende del cliente de twitter proporcionado por Fabric.
// Internamente hace uso de Retrofit.
public class CustomTwitterApiClient extends TwitterApiClient {

    // El constructor recibe una sesión activa con Twitter.
    public CustomTwitterApiClient(Session session) {
        super(session);
    }

    // Retorna el servicio en sí.
    public TimelineService getTimelineService() {
        return getService(TimelineService.class);
    }
}