package pedrojoya.iessaladillo.es.pr105;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "opcion";

    private String mOpcion;
    private TextView lblTexto;
    private NestedScrollView nsvScroll;

    public static MainFragment newInstance(String param1) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOpcion = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getView() != null) {
            initVistas(getView());
        }
    }

    private void initVistas(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity actividad = ((AppCompatActivity) getActivity());
        actividad.setSupportActionBar(toolbar);
        actividad.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actividad.getSupportActionBar().setHomeButtonEnabled(true);
        actividad.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        lblTexto = (TextView) view.findViewById(R.id.lblTexto);
        nsvScroll = (NestedScrollView) view.findViewById(R.id.nsvScroll);
        //lblTexto.setText(mOpcion);
    }
}
