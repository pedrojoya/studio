package es.iessaladillo.pedrojoya.pr110;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private ImageView mImgOrigen;
    private TextView mLblLeyenda;
    private FrameLayout mFrlCuadro;
    private FrameLayout mFrlLeyenda;
    private RelativeLayout mRlRaiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        mImgOrigen = (ImageView) findViewById(R.id.imgOrigen);
        mLblLeyenda = (TextView) findViewById(R.id.lblTitulo);
        mFrlCuadro = (FrameLayout) findViewById(R.id.frlCuadro);
        mFrlLeyenda = (FrameLayout) findViewById(R.id.frlLeyenda);
        mRlRaiz = (RelativeLayout) findViewById(R.id.rlRaiz);

        mImgOrigen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                v.startDrag(null,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        v,      // local data
                        0          // flags (not currently used, set to 0)
                );
                // Se oculta la vista.
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        mLblLeyenda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                v.startDrag(ClipData.newPlainText("Leyenda", mLblLeyenda.getText().toString()),  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        v,      // local data
                        0          // flags (not currently used, set to 0)
                );
                // Se oculta la vista.
                v.setVisibility(View.INVISIBLE);
                return true;
            }
        });

        mFrlCuadro.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    // Se ha iniciado la operación.
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragStarted");
                        // Si no es una imagen no
                        ImageView imagen;
                        try {
                            imagen = (ImageView) event.getLocalState();
                        } catch (Exception e) {
                            return false;
                        }
                        if (imagen == null) {
                            return false;
                        }
                        break;
                    // La sombra de arrastre ha tocado los límites de la vista.
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragEntered");
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroCandidatoBackground));
                        break;
                    // La sombra de arrastre ha salido de los límites de la vista.
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragExited");
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroBackground));
                        break;
                    // Si se deja caer sobre otra vista.
                    case DragEvent.ACTION_DROP:
                        Log.d(getString(R.string.app_name), "frlCuadro: Drop");
                        // Se obtiene la vista que se ha dejado caer.
                        View original = (View) event.getLocalState();
                        // Se quita de su padre.
                        ((ViewGroup) original.getParent()).removeView(original);
                        // Se agrega al destino.
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER;
                        original.setLayoutParams(lp);
                        ((ViewGroup) v).addView(original);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(getString(R.string.app_name), "frlCuadro: DragEnded");
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroBackground));
                        final View arrastrada = (View) event.getLocalState();
                        if (arrastrada != null) {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    arrastrada.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return true;
                }
                return true;
            }
        });

        mFrlLeyenda.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    // Se ha iniciado la operación.
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragStarted");
                        // Si la vista arrastrada no es un TextView, no es candidata.
                        TextView leyenda;
                        try {
                            leyenda = (TextView) event.getLocalState();
                        } catch (Exception e) {
                            return false;
                        }
                        if (leyenda == null) {
                            return false;
                        }
                        break;
                    // La sombra de arrastre ha tocado los límites de la vista.
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragEntered");
                        v.setBackgroundColor(getResources().getColor(R.color.cuadroCandidatoBackground));
                        break;
                    // La sombra de arrastre ha salido de los límites de la vista.
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragExited");
                        v.setBackgroundColor(getResources().getColor(R.color.leyendaBackground));
                        break;
                    // Si se deja caer sobre otra vista.
                    case DragEvent.ACTION_DROP:
                        Log.d(getString(R.string.app_name), "frlLeyenda: Drop");
                        // Se obtiene la vista que se ha dejado caer.
                        View original = (View) event.getLocalState();
                        // Se quita de su padre.
                        ((ViewGroup) original.getParent()).removeView(original);
                        // Se agrega al destino.
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                        lp.gravity = Gravity.CENTER;
                        original.setLayoutParams(lp);
                        ((ViewGroup) v).addView(original);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragEnded");
                        v.setBackgroundColor(getResources().getColor(R.color.leyendaBackground));
                        final View arrastrada = (View) event.getLocalState();
                        if (arrastrada != null) {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    arrastrada.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return true;
                }
                return true;
            }
        });

        mRlRaiz.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    // Si se deja caer sobre otra vista.
                    case DragEvent.ACTION_DROP:
                        Log.d(getString(R.string.app_name), "rlRaiz: Drop");
                        // Se obtiene la vista que se ha dejado caer.
                        View original = (View) event.getLocalState();
                        // Se quita de su padre.
                        ((ViewGroup) original.getParent()).removeView(original);
                        // Se agrega al destino.
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(Math.round(event.getX() - original.getWidth() / 2), Math.round(event.getY() - original.getHeight() / 2), 0, 0);
                        original.setLayoutParams(lp);
                        ((ViewGroup) v).addView(original);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d(getString(R.string.app_name), "frlLeyenda: DragEnded");
                        v.setBackgroundColor(getResources().getColor(R.color.leyendaBackground));
                        final View arrastrada = (View) event.getLocalState();
                        if (arrastrada != null) {
                            v.post(new Runnable() {
                                @Override
                                public void run() {
                                    arrastrada.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                        return true;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
