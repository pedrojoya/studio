package es.iessaladillo.pedrojoya.pr123.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import es.iessaladillo.pedrojoya.pr123.R;

public class InfoFragment extends Fragment {

    private TextView lblLikes;
    @SuppressWarnings("FieldCanBeLocal")
    private ImageView imgLike;

    private int mLikes = 0;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews(getView());
    }

    private void initViews(View view) {
        lblLikes = ViewCompat.requireViewById(view, R.id.lblLikes);
        imgLike = ViewCompat.requireViewById(view, R.id.imgLike);

        lblLikes.setText(
                getResources().getQuantityString(R.plurals.info_fragment_likes, mLikes, mLikes));
        imgLike.setOnClickListener(v -> incrementLikes());
    }

    private void incrementLikes() {
        mLikes++;
        lblLikes.setText(
                getResources().getQuantityString(R.plurals.info_fragment_likes, mLikes, mLikes));
    }

}
