package es.iessaladillo.pedrojoya.pr222.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr222.R;
import es.iessaladillo.pedrojoya.pr222.utils.ConfigurationUtils;

public class MainFragment extends Fragment {

    private static final String STATE_SELECTED_ITEM = "STATE_SELECTED_ITEM";
    public static final int NO_ITEM_SELECTED = -1;

    // Comunication interface with activity.
    public interface Callback {
        void onItemSelected(String item, int position);
    }

    private ListView lstItems;

    private Callback mListener;
    private MainActivityViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement fragment callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        restoreInstanceState(savedInstanceState);
        initViews(getView());
        // If item selected.
        if (mViewModel.getSelectedItem() >= 0) {
            if (ConfigurationUtils.hasLandscapeOrientation(getActivity())) {
                showItem(mViewModel.getSelectedItem());
            } else {
                selectItem(mViewModel.getSelectedItem());
            }
        }
    }

    private void initViews(View view) {
        lstItems = view.findViewById(R.id.lstItems);

        int itemLayout = ConfigurationUtils.hasLandscapeOrientation(
                getActivity()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout
                                 .simple_list_item_1;
        lstItems.setAdapter(new ArrayAdapter<>(getActivity(), itemLayout, mViewModel.getItems()));
        lstItems.setOnItemClickListener((adapterView, v, position, id) -> showItem(position));
    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mViewModel.setSelectedItem(savedInstanceState.getInt(STATE_SELECTED_ITEM));
        }
    }

    private void showItem(int position) {
        selectItem(position);
        mListener.onItemSelected((String) lstItems.getItemAtPosition(position), position);
    }

    public void selectItem(int position) {
        if (position >= 0) {
            lstItems.setItemChecked(position, true);
            lstItems.setSelection(position);
        }
        else {
            lstItems.setItemChecked(mViewModel.getSelectedItem(), false);
            lstItems.clearChoices();
        }
        mViewModel.setSelectedItem(position);
    }

    // Needed in case activity is destroy because of low memory.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_ITEM, mViewModel.getSelectedItem());
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

}