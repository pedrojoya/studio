package pedrojoya.iessaladillo.es.pr181;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

@SuppressWarnings({"unchecked", "EmptyMethod"})
public abstract class BasePresenterActivity<P extends BasePresenter<V>, V> extends
        AppCompatActivity {

    private static final int LOADER_ID = 101;
    private BasePresenter<V> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // LoaderCallbacks as an object, so no hint regarding Loader will be leak to the subclasses.
        getSupportLoaderManager().initLoader(LOADER_ID, null,
                new LoaderManager.LoaderCallbacks<P>() {
                    @Override
                    public final Loader<P> onCreateLoader(int id, Bundle args) {
                        return new PresenterLoader<>(BasePresenterActivity.this,
                                getPresenterFactory());
                    }

                    @Override
                    public final void onLoadFinished(Loader<P> loader, P presenter) {
                        BasePresenterActivity.this.presenter = presenter;
                        onPresenterPrepared(presenter);
                    }

                    @Override
                    public final void onLoaderReset(Loader<P> loader) {
                        BasePresenterActivity.this.presenter = null;
                        onPresenterDestroyed();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewAttach(getPresenterView());
    }

    @Override
    protected void onStop() {
        presenter.onViewDetach();
        super.onStop();
    }

    protected abstract PresenterFactory<P> getPresenterFactory();

    protected abstract void onPresenterPrepared(P presenter);

    protected void onPresenterDestroyed() {
    }

    // Override in case of Activity not implementing Presenter<View> interface
    protected V getPresenterView() {
        return (V) this;
    }

}
