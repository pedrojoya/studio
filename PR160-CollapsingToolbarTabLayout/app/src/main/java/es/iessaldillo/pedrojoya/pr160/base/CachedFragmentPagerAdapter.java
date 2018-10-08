package es.iessaldillo.pedrojoya.pr160.base;

import android.util.SparseArray;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public abstract class CachedFragmentPagerAdapter extends FragmentPagerAdapter {

    // SparseArray de referencias débiles a los fragmentos gestionados
    // por el adaptador.
    private final SparseArray<WeakReference<Fragment>> fragments = new SparseArray<>();

    @SuppressWarnings("WeakerAccess")
    public CachedFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    // Al instanciar el fragmento, se añade al SparseArray la referencia débil
    // al mismo.
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        final Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, new WeakReference<>(fragment));
        return fragment;
    }

    // Al destruir el fragmento, se elimina su referencia débil del SparseArray.
    @Override
    public void destroyItem(@NonNull final ViewGroup container, final int position, @NonNull final Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Retorna el fragmento correspondiente a dicha posición.
    @Nullable
    public Fragment getFragment(final int position) {
        // Se obtiene la referencia débil desde SparseArray y a partir
        // de la referencia débil se obtiene el fragmento en sí.
        final WeakReference<Fragment> wr = fragments.get(position);
        if (wr != null) {
            return wr.get();
        } else {
            return null;
        }
    }

}
