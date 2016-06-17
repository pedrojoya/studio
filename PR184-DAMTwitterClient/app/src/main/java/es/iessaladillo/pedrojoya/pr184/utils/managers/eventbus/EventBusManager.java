package es.iessaladillo.pedrojoya.pr184.utils.managers.eventbus;

public interface EventBusManager {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
