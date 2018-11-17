package es.iessaladillo.pedrojoya.pr249.ui.main;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr249.R;
import es.iessaladillo.pedrojoya.pr249.base.PositionalDetailsLookup;
import es.iessaladillo.pedrojoya.pr249.base.PositionalItemKeyProvider;
import es.iessaladillo.pedrojoya.pr249.utils.ConfigurationUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    public interface Callback {
        void onItemSelected(String item);
    }

    private RecyclerView lstItems;
    private Callback listener;
    private MainActivityViewModel viewModel;
    private MainFragmentAdapter listAdapter;
    private SelectionTracker<Long> selectionTracker;

    static MainFragment newInstance() {
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
        viewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        setupViews(getView());
        listAdapter.submitList(viewModel.getStudents(false));
        // Can't restore selectionTracker state on onRestoreInstanceState, because
        // selectionTracker isn't created yet.
        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
        // Scroll to selected item
        if (viewModel.getSelectedItemKey() != null) {
            lstItems.smoothScrollToPosition((int) (long) viewModel.getSelectedItemKey());
        }
    }

    private void setupViews(View view) {
        setupRecyclerView(view);
    }

    private void setupRecyclerView(View view) {
        lstItems = ViewCompat.requireViewById(view, R.id.lstItems);

        TextView lblEmpty = ViewCompat.requireViewById(view, R.id.lblEmpty);
        int itemLayout = ConfigurationUtils.inLandscape(
                requireContext()) ? android.R.layout.simple_list_item_activated_1 : android.R.layout
                                 .simple_list_item_1;
        listAdapter = new MainFragmentAdapter(itemLayout);
        listAdapter.setEmptyView(lblEmpty);
        listAdapter.setOnItemClickListener((v, position) -> {
            if (!selectionTracker.hasSelection()) {
                selectionTracker.select((long) position);
            }
        });
        lstItems.setHasFixedSize(true);
        lstItems.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        lstItems.setItemAnimator(new DefaultItemAnimator());
        lstItems.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        lstItems.setAdapter(listAdapter);
        setupSelectionTracker();
        listAdapter.setSelectionTracker(selectionTracker);
    }

    private void setupSelectionTracker() {
        selectionTracker = new SelectionTracker.Builder<>("lstItemsSelection", lstItems,
                new PositionalItemKeyProvider(), new PositionalDetailsLookup(lstItems),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectSingleAnything()).build();
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {
            @Override
            public void onSelectionChanged() {
                if (selectionTracker.hasSelection()) {
                    for (Long key : selectionTracker.getSelection()) {
                        listener.onItemSelected(listAdapter.getItem((int) (long) key));
                        viewModel.setSelectedItemKey(key);
                    }
                } else {
                    // So you can't deselect current item
                    selectionTracker.select(viewModel.getSelectedItemKey());
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        selectionTracker.onSaveInstanceState(outState);
    }

}