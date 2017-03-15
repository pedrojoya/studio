package info.devexchanges.snaprecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ReyclerViewHolder> {

    private final int resIdLayout;
    private final LayoutInflater layoutInflater;
    private final ArrayList<App> items;

    public AppsAdapter(Context context, int resIdLayout, ArrayList<App> items) {
        this.layoutInflater = LayoutInflater.from(context);
        this.resIdLayout = resIdLayout;
        this.items = items;
    }

    @Override
    public ReyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReyclerViewHolder(layoutInflater.inflate(resIdLayout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ReyclerViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ReyclerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgLogo;
        private final TextView lblNombre;

        private ReyclerViewHolder(final View v) {
            super(v);
            imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
            lblNombre = (TextView) v.findViewById(R.id.lblNombre);
        }

        private void bind(App app) {
            imgLogo.setImageResource(app.getResIdLogo());
            lblNombre.setText(app.getNombre());
        }
    }

}
