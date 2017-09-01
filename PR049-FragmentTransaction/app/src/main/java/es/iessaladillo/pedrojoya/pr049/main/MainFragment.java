package es.iessaladillo.pedrojoya.pr049.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.utils.ConfigurationUtils;

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
    private int mSelectedItem = NO_ITEM_SELECTED;

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
        if (mSelectedItem >= 0) {
            if (ConfigurationUtils.hasLandscapeOrientation(getActivity())) {
                showItem(mSelectedItem);
            } else {
                selectItem(mSelectedItem);
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
            mSelectedItem = savedInstanceState.getInt(STATE_SELECTED_ITEM);
        }
        // Default mSelectedItem = 0;
    }

    private void showItem(int position) {
        selectItem(position);
        mListener.onItemSelected((String) lstItems.getItemAtPosition(position), position);
    }

    public void selectItem(int position) {
        if (position >= 0) {
            mSelectedItem = position;
            lstItems.setItemChecked(mSelectedItem, true);
            lstItems.setSelection(mSelectedItem);
        }
        else {
            lstItems.setItemChecked(mSelectedItem, false);
            lstItems.clearChoices();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_ITEM, mSelectedItem);
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

}