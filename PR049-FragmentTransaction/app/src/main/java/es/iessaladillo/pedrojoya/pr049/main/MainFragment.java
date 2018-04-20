package es.iessaladillo.pedrojoya.pr049.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import es.iessaladillo.pedrojoya.pr049.R;
import es.iessaladillo.pedrojoya.pr049.utils.ConfigurationUtils;

public class MainFragment extends Fragment {

    // Comunication interface with activity.
    public interface Callback {
        void onItemSelected(String item);
    }

    private ListView lstItems;

    private Callback mListener;
    private MainActivityViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        mViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        lstItems = ViewCompat.requireViewById(view, R.id.lstItems);

        int itemLayout = ConfigurationUtils.hasLandscapeOrientation(
                requireActivity()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout
                                 .simple_list_item_1;
        List<String> data = mViewModel.getItems();
        lstItems.setAdapter(new ArrayAdapter<>(requireActivity(), itemLayout, data));
        if (ConfigurationUtils.hasLandscapeOrientation(requireActivity())) {
            int selectedIndex = data.indexOf(mViewModel.getSelectedItem());
            if (selectedIndex >= 0) {
                selectItem(selectedIndex);
            }
        }
        lstItems.setOnItemClickListener((adapterView, v, position, id) -> showItem(position));
    }

    private void showItem(int position) {
        selectItem(position);
        mListener.onItemSelected((String) lstItems.getItemAtPosition(position));
    }

    private void selectItem(int position) {
        if (position >= 0) {
            lstItems.setItemChecked(position, true);
            lstItems.setSelection(position);
        }
        else {
            lstItems.clearChoices();
        }
    }

}