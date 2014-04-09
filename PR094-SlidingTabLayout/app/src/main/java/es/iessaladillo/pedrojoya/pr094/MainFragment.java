package es.iessaladillo.pedrojoya.pr094;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.common.view.SlidingTabLayout;

public class MainFragment extends Fragment {

    private ViewPager vpPaginador;
    private SlidingTabLayout stTabs;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Se obtiene la vista para el fragmento.
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Se configura el ViewPager.
        vpPaginador = (ViewPager) rootView.findViewById(R.id.vpPaginador);
        vpPaginador.setAdapter(new PaginasAdapter(getActivity(), getChildFragmentManager()));
        // Se configura el SlidingTabLayout.
        stTabs = (SlidingTabLayout) rootView.findViewById(R.id.stTabs);
        stTabs.setCustomTabView(R.layout.tab_header, R.id.lblTab);
        stTabs.setViewPager(vpPaginador);
        stTabs.setSelectedIndicatorColors(
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5)
        );
        stTabs.setDividerColors(getResources().getColor(R.color.color2));
        return rootView;
    }
}