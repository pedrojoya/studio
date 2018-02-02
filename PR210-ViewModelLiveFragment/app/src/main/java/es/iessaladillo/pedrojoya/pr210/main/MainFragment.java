package es.iessaladillo.pedrojoya.pr210.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr210.R;
import es.iessaladillo.pedrojoya.pr210.utils.ConfigurationUtils;

public class MainFragment extends Fragment {

    private static final String STATE_SELECTED_ITEM = "STATE_SELECTED_ITEM";
    public static final int NO_ITEM_SELECTED = -1;

    private ListView lstItems;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
        initViews(getView());
        mViewModel.getCurrentItem().observe(getActivity(), item -> {
            Toast.makeText(getActivity(), "Ha cambiado", Toast.LENGTH_SHORT).show();
        });
    }

    private void initViews(View view) {
        lstItems = view.findViewById(R.id.lstItems);

        int itemLayout = ConfigurationUtils.hasLandscapeOrientation(
                getActivity()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        lstItems.setAdapter(new ArrayAdapter<>(getActivity(), itemLayout, mViewModel.getItems()));
        lstItems.setOnItemClickListener((adapterView, v, position, id) -> showItem(position));
    }

    private void showItem(int position) {
        selectItem(position);
        mViewModel.setCurrentItem((String) lstItems.getItemAtPosition(position));
    }


    public void selectItem(int position) {
        if (position > 0) {
            lstItems.setItemChecked(position, true);
            lstItems.setSelection(position);
        } else {
            //lstItems.setItemChecked(mViewModel.getSelectedItem().getValue(), false);
            lstItems.clearChoices();
        }
        mViewModel.setCurrentItem((String) lstItems.getItemAtPosition(position));
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }

}