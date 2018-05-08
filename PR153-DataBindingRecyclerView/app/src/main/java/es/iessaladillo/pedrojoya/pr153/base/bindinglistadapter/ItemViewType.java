package es.iessaladillo.pedrojoya.pr153.base.bindinglistadapter;

import android.support.annotation.LayoutRes;

public interface ItemViewType {
    @SuppressWarnings("SameReturnValue")
    @LayoutRes
    int getLayoutId();
}