package es.iessaladillo.pedrojoya.pr122;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.VideoView;

// VideoView con listener para onVideoPlay y onVideoPause.
// Necesitamos conocer el inicio de la reproducción para poder ocultar el thumbnail.
// Necesitamos conocer la pausa de la reproducción para guardar la posición actual por si
// se cambia la orientación, ya que getCurrentPosition() retorna un valor erróneo cuando ha
// finalizado la reproducción.
public class CustomVideoView extends VideoView {

    // Constructores.
    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // Interfaz para eventos onVideoPlay y onVideoPause.
    public static interface PlayPauseListener {
        void onVideoPlay();
        void onVideoPause();
    }

    private PlayPauseListener mListener;

    // Establece el listener para eventos onVideoPlay y onVideoPause.
    public void setPlayPauseListener(PlayPauseListener listener) {
        mListener = listener;
    }

    // Cuando se pausa, se llama al listener.
    @Override
    public void pause() {
        super.pause();
        if (mListener != null) {
            mListener.onVideoPause();
        }
    }

    // Cuando se inicia / continua la reproducción, se llama al listener.
    @Override
    public void start() {
        super.start();
        if (mListener != null) {
            mListener.onVideoPlay();
        }
    }

}
