package es.iessaladillo.pedrojoya.pr257.detail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaladillo.pedrojoya.pr257.R;
import es.iessaladillo.pedrojoya.pr257.databinding.FragmentDetailBinding;

@SuppressWarnings("WeakerAccess")
public class DetailFragment extends Fragment {

    private FragmentDetailBinding b;

    public DetailFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
            container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getArguments());
        DetailFragmentViewModel viewModel = ViewModelProviders.of(this,
            new DetailFragmentViewModelFactory(getArguments())).get(
            DetailFragmentViewModel.class);
        b.setVm(viewModel);
    }

}
