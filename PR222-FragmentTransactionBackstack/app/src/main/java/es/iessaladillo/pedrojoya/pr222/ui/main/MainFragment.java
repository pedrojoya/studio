package es.iessaladillo.pedrojoya.pr222.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr222.R;
import es.iessaladillo.pedrojoya.pr222.base.PositionalDetailsLookup;
import es.iessaladillo.pedrojoya.pr222.base.PositionalItemKeyProvider;
import es.iessaladillo.pedrojoya.pr222.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr222.data.local.Database;
import es.iessaladillo.pedrojoya.pr222.utils.ConfigurationUtils;

public class MainFragment extends Fragment {

    public static final long NO_ITEM_SELECTED = -1;
    private static final String STATE_SELECTED_ITEM_KEY = "STATE_SELECTED_ITEM_KEY";

    private MainFragmentAdapter listAdapter;
    private SelectionTracker<Long> selectionTracker;

    // Comunication interface with activity.
    public interface Callback {
        void onItemSelected(String item, long key);
    }

    private RecyclerView lstItems;

    private Callback listener;
    private MainActivityViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            listener = (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement fragment callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity(),
                new MainActivityViewModelFactory(new RepositoryImpl(Database.getInstance()),
                        getString(R.string.main_activity_no_item))).get(
                MainActivityViewModel.class);
        restoreInstanceState(savedInstanceState);
        initViews(getView());
        listAdapter.submitList(viewModel.getStudents(false));
        // Can't restore selectionTracker state on onRestoreInstanceState, because
        // selectionTracker isn't created yet.
        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
        // Scroll to selected item
        if (viewModel.getSelectedItemKey() != MainFragment.NO_ITEM_SELECTED) {
            lstItems.smoothScrollToPosition((int) (long) viewModel.getSelectedItemKey());
        }
        //selectItem(viewModel.getSelectedItemKey());
        //            if (ConfigurationUtils.inLandscape(requireContext())) {
        //                showItem(viewModel.getSelectedItemKey());
        //            } else {
        //                selectItem(viewModel.getSelectedItemKey());
        //            }
    }

    private void initViews(View view) {
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        lstItems = ViewCompat.requireViewById(view, R.id.lstItems);

        TextView lblEmpty = ViewCompat.requireViewById(view, R.id.lblEmpty);
        int itemLayout = ConfigurationUtils.inLandscape(
                requireActivity()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        listAdapter = new MainFragmentAdapter(itemLayout);
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener((v, position) -> selectItem((long) position));
        lstItems.setHasFixedSize(true);
        lstItems.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        lstItems.setItemAnimator(new DefaultItemAnimator());
        lstItems.setAdapter(listAdapter);
        setupSelectionTracker();
        listAdapter.setSelectionTracker(selectionTracker);
    }

    private void setupSelectionTracker() {
        selectionTracker = new SelectionTracker.Builder<>("lstItemsSelection", lstItems,
                new PositionalItemKeyProvider(), new PositionalDetailsLookup(lstItems),
                StorageStrategy.createLongStorage()).withSelectionPredicate(
                SelectionPredicates.createSelectSingleAnything()).build();
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {
            @Override
            public void onSelectionChanged() {
                if (selectionTracker.hasSelection()) {
                    for (Long key : selectionTracker.getSelection()) {
                        listener.onItemSelected(listAdapter.getItem((int) (long) key),
                                (int) (long) key);
                    }
                } else {
                    // So you can't deselect current item
                    selectItem(viewModel.getSelectedItemKey());
                }
            }
        });
    }


    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            viewModel.setSelectedItemKey(savedInstanceState.getLong(STATE_SELECTED_ITEM_KEY));
        }
    }

    //    private void showItem(int position) {
    //        selectItem(position);
    //        listener.onItemSelected((String) lstItems.getItemAtPosition(position), position);
    //    }
    //
    public void selectItem(long key) {
        if (key != MainFragment.NO_ITEM_SELECTED) {
            selectionTracker.select(key);
        } else {
            selectionTracker.clearSelection();
        }
    }

    // Needed in case activity is destroy because of low memory.
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(STATE_SELECTED_ITEM_KEY, viewModel.getSelectedItemKey());
        selectionTracker.onSaveInstanceState(outState);
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

}