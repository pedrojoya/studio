package es.iessaldillo.pedrojoya.pr160.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import es.iessaldillo.pedrojoya.pr160.R;
import es.iessaldillo.pedrojoya.pr160.base.OnFabClickListener;

public class PageFragment extends Fragment implements OnFabClickListener {

    private static final String ARG_PAGE_NUMBER = "ARG_PAGE_NUMBER";

    private TextView lblLikes;
    private PageFragmentViewModel viewModel;

    public PageFragment() {
    }

    static PageFragment newInstance(int pageNumber) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PageFragmentViewModel.class);
        initViews(getView());
    }

    private void initViews(View view) {
        lblLikes = ViewCompat.requireViewById(view, R.id.lblLikes);

        lblLikes.setText(String.valueOf(viewModel.getLikes()));
    }

    @Override
    public void onFabClick(View view) {
        lblLikes.setText(String.valueOf(viewModel.incrementsLikes()));
    }

}
