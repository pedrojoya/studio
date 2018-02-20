package es.iessaladillo.pedrojoya.pr210.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import es.iessaladillo.pedrojoya.pr210.R;
import es.iessaladillo.pedrojoya.pr210.utils.ConfigurationUtils;

public class MainFragment extends Fragment {

    private ListView lstItems;

    private MainActivityViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //noinspection ConstantConditions
        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        initViews(getView());
        //mViewModel.getCurrentItem().observe(getActivity(), item -> markItem(item));
    }

    private void initViews(View view) {
        lstItems = view.findViewById(R.id.lstItems);

        int itemLayout = ConfigurationUtils.hasLandscapeOrientation(
                getActivity()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        //noinspection ConstantConditions
        lstItems.setAdapter(new ArrayAdapter<>(getActivity(), itemLayout, mViewModel.getItems()));
        lstItems.setOnItemClickListener((adapterView, v, position, id) -> navigateToItem(position));
    }

    private void navigateToItem(int position) {
        markItem(position);
        mViewModel.setCurrentItem((String) lstItems.getItemAtPosition(position));
    }

    private void markItem(int position) {
        if (position >= 0) {
            lstItems.setItemChecked(position, true);
            lstItems.setSelection(position);
        } else {
            //lstItems.setItemChecked(mViewModel.getSelectedItem().getValue(), false);
            lstItems.clearChoices();
        }
    }

}