package pedrojoya.iessaladillo.es.pr181;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

@SuppressWarnings({"unchecked", "EmptyMethod", "unused"})
public abstract class BasePresenterFragment<P extends BasePresenter<V>, V> extends Fragment {

    private static final int LOADER_ID = 101;

    // boolean flag to avoid delivering the result twice. Calling initLoader in onActivityCreated
    // makes
    // onLoadFinished will be called twice during configuration change.
    private boolean delivered = false;
    private BasePresenter<V> presenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // LoaderCallbacks as an object, so no hint regarding loader will be leak to the subclasses.
        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<P>() {
            @Override
            public final Loader<P> onCreateLoader(int id, Bundle args) {
                return new PresenterLoader<>(getContext(), getPresenterFactory());
            }

            @Override
            public final void onLoadFinished(Loader<P> loader, P presenter) {
                if (!delivered) {
                    BasePresenterFragment.this.presenter = presenter;
                    delivered = true;
                    onPresenterPrepared(presenter);
                }
            }

            @Override
            public final void onLoaderReset(Loader<P> loader) {
                BasePresenterFragment.this.presenter = null;
                onPresenterDestroyed();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onViewAttach(getPresenterView());
    }

    @Override
    public void onPause() {
        presenter.onViewDetach();
        super.onPause();
    }

    protected abstract PresenterFactory<P> getPresenterFactory();

    protected abstract void onPresenterPrepared(P presenter);

    protected void onPresenterDestroyed() {
        // hook for subclasses
    }

    // Override in case of fragment not implementing Presenter<View> interface
    protected V getPresenterView() {
        return (V) this;
    }
}
