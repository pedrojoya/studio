package es.iessaladillo.pedrojoya.pr220.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr220.BR;
import es.iessaladillo.pedrojoya.pr220.R;
import es.iessaladillo.pedrojoya.pr220.base.BaseFragment;
import es.iessaladillo.pedrojoya.pr220.data.RepositoryImpl;
import es.iessaladillo.pedrojoya.pr220.databinding.FragmentMainBinding;
import es.iessaladillo.pedrojoya.pr220.utils.ViewMessage;

@SuppressWarnings("unchecked")
public class MainFragment extends BaseFragment<MainActivityViewModel, FragmentMainBinding> {

    private MainFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setVm(viewModel);
        setupFab();
        setupRecyclerView();
//        viewModel.getRefreshing().observe(getActivity(), refreshing -> binding.swipeRefreshLayout
//                .setRefreshing(refreshing));
        viewModel.getStudents().observe(getActivity(), students -> adapter.setList(students));
//        viewModel.getMessage().observe(getActivity(), this::showMessage);
        viewModel.getRevertSwipe().observe(getActivity(), position -> adapter.notifyItemChanged(position));
    }

    @Override
    protected Class<MainActivityViewModel> getViewModelClass() {
        return MainActivityViewModel.class;
    }

    @Override
    protected MainActivityViewModel createViewModel() {
        return new MainActivityViewModel(RepositoryImpl.getInstance(getActivity()));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @SuppressWarnings("ConstantConditions")
    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(v -> viewModel.onFabClick(v));
    }

    private void setupRecyclerView() {
        adapter = new MainFragmentAdapter(BR.item);
        binding.lstStudents.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuRefresh) {
            viewModel.refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ConstantConditions")
    private void showMessage(ViewMessage viewMessage) {
        if (!viewMessage.askIsShownAndMarkAsShown()) {
            Toast.makeText(getActivity(), viewMessage.getMessageResId(), Toast.LENGTH_SHORT).show();
        }
        if (viewMessage.isFinishActivityNeeded()) {
            getActivity().finish();
        }
    }

}