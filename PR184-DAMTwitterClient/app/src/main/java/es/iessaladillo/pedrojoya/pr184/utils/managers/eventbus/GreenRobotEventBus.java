package es.iessaladillo.pedrojoya.pr184.utils.managers.eventbus;

import org.greenrobot.eventbus.EventBus;

public class GreenRobotEventBus implements EventBusManager {

    EventBus eventBus;

    public GreenRobotEventBus(){
        eventBus = EventBus.getDefault();
    }

    public void register(Object subscriber){
        eventBus.register(subscriber);
    }

    public void unregister(Object subscriber){
        eventBus.unregister(subscriber);
    }

    public void post(Object event){
        eventBus.post(event);
    }

}