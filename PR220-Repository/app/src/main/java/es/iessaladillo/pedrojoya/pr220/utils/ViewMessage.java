package es.iessaladillo.pedrojoya.pr220.utils;

import android.support.annotation.StringRes;

import java.util.concurrent.atomic.AtomicBoolean;

public class ViewMessage {

    @StringRes
    private final int messageResId;
    private final boolean finishActivityNeeded;
    private AtomicBoolean shown = new AtomicBoolean(false);


    public ViewMessage(int messageResId, boolean finishActivityNeeded) {
        this.messageResId = messageResId;
        this.finishActivityNeeded = finishActivityNeeded;
    }

    public int getMessageResId() {
        return messageResId;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean askIsShownAndMarkAsShown() {
        return shown.getAndSet(true);
    }

    public boolean isFinishActivityNeeded() {
        return finishActivityNeeded;
    }

}
