package es.iessaladillo.pedrojoya.pr210.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
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
        mViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        lstItems = ViewCompat.requireViewById(view, R.id.lstItems);

        int itemLayout = ConfigurationUtils.hasLandscapeOrientation(
                requireActivity()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        lstItems.setAdapter(new ArrayAdapter<>(requireActivity(), itemLayout, mViewModel.getItems()));
        lstItems.setOnItemClickListener((adapterView, v, position, id) -> markItem(position));
    }

    private void markItem(int position) {
        if (position >= 0) {
            lstItems.setItemChecked(position, true);
            lstItems.setSelection(position);
            mViewModel.setCurrentItem((String) lstItems.getItemAtPosition(position));
        } else {
            lstItems.clearChoices();
        }
    }

}