package es.iessaladillo.pedrojoya.pr216;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SecundaryFragment extends Fragment {

    public SecundaryFragment() {
    }

    public static SecundaryFragment newInstance() {
        return new SecundaryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_secundary, container, false);
    }

}
