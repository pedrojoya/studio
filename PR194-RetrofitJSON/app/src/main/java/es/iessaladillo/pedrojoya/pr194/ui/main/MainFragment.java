package es.iessaladillo.pedrojoya.pr194.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import es.iessaladillo.pedrojoya.pr194.R;
import es.iessaladillo.pedrojoya.pr194.base.Event;
import es.iessaladillo.pedrojoya.pr194.base.RequestState;
import es.iessaladillo.pedrojoya.pr194.data.model.Student;
import es.iessaladillo.pedrojoya.pr194.data.remote.ApiService;
import es.iessaladillo.pedrojoya.pr194.utils.ToastUtils;

@SuppressWarnings("WeakerAccess")
public class MainFragment extends Fragment {

    private SwipeRefreshLayout swlPanel;

    private MainFragmentAdapter listAdapter;
    private MainFragmentViewModel viewModel;

    static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                new MainFragmentViewModelFactory(ApiService.getInstance(requireContext()).getApi()))
                .get(MainFragmentViewModel.class);
        setupViews(view);
        observeStudentsLiveData();

    }

    private void setupViews(View view) {
        setupListView(view);
        setupPanel(view);
    }

    private void setupListView(View view) {
        RecyclerView lstStudents = ViewCompat.requireViewById(view, R.id.lstStudents);
        TextView lblEmptyView = ViewCompat.requireViewById(view, R.id.lblEmptyView);

        listAdapter = new MainFragmentAdapter();
        listAdapter.setEmptyView(lblEmptyView);
        lstStudents.setHasFixedSize(true);
        lstStudents.setLayoutManager(
                new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        lstStudents.setItemAnimator(new DefaultItemAnimator());
        lstStudents.setAdapter(listAdapter);
    }

    private void setupPanel(View view) {
        swlPanel = ViewCompat.requireViewById(view, R.id.swlPanel);

        swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swlPanel.setOnRefreshListener(() -> viewModel.forceLoadStudents());
    }

    private void showStudents(List<Student> students) {
        listAdapter.submitList(students);
        swlPanel.post(() -> swlPanel.setRefreshing(false));
    }

    private void showErrorLoadingStudents(Event<Exception> event) {
        Exception exception = event.getContentIfNotHandled();
        if (exception != null) {
            ToastUtils.toast(requireContext(), exception.getMessage());
        }
    }

    private void observeStudentsLiveData() {
        viewModel.getStudents().observe(this, request -> {
            if (request != null) {
                if (request instanceof RequestState.Loading) {
                    swlPanel.post(() -> swlPanel.setRefreshing(
                            ((RequestState.Loading) request).isLoading()));
                } else if (request instanceof RequestState.Error) {
                    showErrorLoadingStudents(((RequestState.Error) request).getException());
                } else if (request instanceof RequestState.Result) {
                    //noinspection unchecked
                    showStudents(((RequestState.Result<List<Student>>) request).getData());
                }
            }
        });
    }

}
