package pedrojoya.iessaladillo.es.pr105.fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pedrojoya.iessaladillo.es.pr105.R;

public class Tab2Fragment extends Fragment {

    // Retorna una nueva intancia del fragmento.
    public static Tab2Fragment newInstance() {
        return new Tab2Fragment();
    }

    public Tab2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

}
