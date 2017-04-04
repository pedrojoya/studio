package es.iessaldillo.pedrojoya.pr205;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

class PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NUM_PAGINAS = 2;
    List<Page> mPaginas;

    public PageAdapter(RecyclerView pager, LayoutInflater layoutInflater) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return NUM_PAGINAS;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



}
