package es.iessaladillo.pedrojoya.pr210.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr210.R;

public class DetailFragment extends Fragment {

    public static final String EXTRA_ITEM = "EXTRA_ITEM";

    @SuppressWarnings("FieldCanBeLocal")
    private DetailFragmentBaseViewModel mViewModel;

    private TextView lblItem;
    private Class<DetailFragmentBaseViewModel> viewModelClass;

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DetailFragmentBaseActivity activity;
        try {
            // Activity must extend DetailFragmentBaseActivity.
            //noinspection unchecked
            activity = (DetailFragmentBaseActivity<DetailFragmentBaseViewModel>) context;
            //noinspection unchecked
            viewModelClass = activity.getViewModelClass();
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz.
            throw new ClassCastException(context.toString() +
                    " debe heredar de DetailFragmentBaseActivity");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity()).get(viewModelClass);
        initViews(getView());
        mViewModel.getCurrentItem().observe(this, this::showItem);
    }

    private void showItem(String item) {
        lblItem.setText(item);
    }

    private void initViews(View view) {
            lblItem = ViewCompat.requireViewById(view, R.id.lblItem);
    }

}