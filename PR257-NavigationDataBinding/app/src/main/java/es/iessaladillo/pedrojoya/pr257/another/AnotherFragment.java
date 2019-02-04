package es.iessaladillo.pedrojoya.pr257.another;

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
import es.iessaladillo.pedrojoya.pr257.databinding.FragmentAnotherBinding;

@SuppressWarnings("WeakerAccess")
public class AnotherFragment extends Fragment {

    private FragmentAnotherBinding b;

    public AnotherFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_another,
            container, false);
        return b.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(requireActivity().getIntent().getExtras());
        AnotherFragmentViewModel viewModel = ViewModelProviders.of(this,
            new AnotherFragmentViewModelFactory(requireActivity().getIntent().getExtras())).get(
            AnotherFragmentViewModel.class);
        b.setVm(viewModel);
    }

}
