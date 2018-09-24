package es.iessaladillo.pedrojoya.pr249.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr249.R;
import es.iessaladillo.pedrojoya.pr249.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr249.data.local.Database;
import es.iessaladillo.pedrojoya.pr249.ui.main.MainActivityViewModel;
import es.iessaladillo.pedrojoya.pr249.ui.main.MainActivityViewModelFactory;
import es.iessaladillo.pedrojoya.pr249.utils.ConfigurationUtils;

public class ListFragment extends Fragment {

    public interface onItemSelectedListener {
        void onItemSelected(String item);
    }

    private ListFragmentAdapter listAdapter;
    private ListFragment.onItemSelectedListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listener = (onItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement ListFragment.onItemSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivityViewModel viewModel = ViewModelProviders.of(requireActivity(),
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()))).get(
                MainActivityViewModel.class);
        initViews(getView());
        listAdapter.submitList(viewModel.getStudents(false));
    }

    private void initViews(View view) {
        setupToolbar();
        setupRecyclerView(view);
    }

    private void setupToolbar() {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.list_title));
        }
    }

    private void setupRecyclerView(View view) {
        RecyclerView lstItems = ViewCompat.requireViewById(view, R.id.lstItems);

        TextView lblEmpty = ViewCompat.requireViewById(view, R.id.lblEmpty);
        int itemLayout = ConfigurationUtils.inLandscape(
                requireActivity()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        listAdapter = new ListFragmentAdapter(itemLayout);
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener(
                (v, position) -> listener.onItemSelected(listAdapter.getItem(position)));
        lstItems.setHasFixedSize(true);
        lstItems.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        lstItems.setItemAnimator(new DefaultItemAnimator());
        lstItems.setAdapter(listAdapter);
    }

    public static ListFragment newInstance() {
        return new ListFragment();
    }

}