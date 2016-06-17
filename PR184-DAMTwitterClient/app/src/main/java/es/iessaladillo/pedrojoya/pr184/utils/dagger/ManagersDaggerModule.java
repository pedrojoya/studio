package es.iessaladillo.pedrojoya.pr184.utils.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.iessaladillo.pedrojoya.pr184.utils.managers.eventbus.EventBusManager;
import es.iessaladillo.pedrojoya.pr184.utils.managers.eventbus.GreenRobotEventBus;
import es.iessaladillo.pedrojoya.pr184.utils.managers.imageloaders.GlideImageLoader;
import es.iessaladillo.pedrojoya.pr184.utils.managers.imageloaders.ImageLoader;
import es.iessaladillo.pedrojoya.pr184.utils.managers.uimessage.ToastManager;
import es.iessaladillo.pedrojoya.pr184.utils.managers.uimessage.UIMessageManager;

// Módulo de Dagger 2 para proporcionar los objetos Manager
@Module
public class ManagersDaggerModule {

    // Proporciona un EventBusManager
    @Singleton
    @Provides
    EventBusManager providesEventBusManager() {
        return new GreenRobotEventBus();
    }

    // Proporciona un ImageLoader
    @Singleton
    @Provides
    ImageLoader provideImageLoader() {
        return new GlideImageLoader();
    }

    // Proporciona un UIMessageManager.
    @Singleton
    @Provides
    UIMessageManager provideUIMessageManager()   {
        return new ToastManager();
    }

}
