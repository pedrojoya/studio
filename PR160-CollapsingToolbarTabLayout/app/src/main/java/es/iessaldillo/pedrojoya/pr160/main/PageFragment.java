package es.iessaldillo.pedrojoya.pr160.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.iessaldillo.pedrojoya.pr160.R;
import es.iessaldillo.pedrojoya.pr160.base.OnFabClickListener;

public class PageFragment extends Fragment implements OnFabClickListener{

    private static final String ARG_PAGE_NUMBER = "ARG_PAGE_NUMBER";
    private static final String STATE_LIKES = "STATE_LIKES";

    private TextView lblLikes;

    private int mLikes;

    public PageFragment() {
    }

    public static PageFragment newInstance(int pageNumber) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreInstanceState(savedInstanceState);
        if (getView() != null) {
            initViews(getView());
        }

    }

    private void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLikes = savedInstanceState.getInt(STATE_LIKES);
        }
    }

    private void initViews(View view) {
        lblLikes = view.findViewById(R.id.lblLikes);

        lblLikes.setText(String.valueOf(mLikes));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_LIKES, mLikes);
    }

    @Override
    public void onFabClick(View view) {
        mLikes++;
        lblLikes.setText(String.valueOf(mLikes));
    }

}
