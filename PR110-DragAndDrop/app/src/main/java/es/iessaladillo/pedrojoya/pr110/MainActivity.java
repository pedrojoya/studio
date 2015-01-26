package es.iessaladillo.pedrojoya.pr110;

import android.content.ClipData;
import android.graphics.Color;
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


public class MainActivity extends ActionBarActivity {

    private ImageView mImgOrigen;
    private FrameLayout mFrlDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        mImgOrigen = (ImageView) findViewById(R.id.imgOrigen);
        mFrlDestino = (FrameLayout) findViewById(R.id.frlDestino);
        mImgOrigen.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData dragData = ClipData.newPlainText("bitmap", "bitmap");
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                v.startDrag(dragData,  // the data to be dragged
                        myShadow,  // the drag shadow builder
                        v,      // local data
                        0          // flags (not currently used, set to 0)
                );
                return true;
            }
        });
        mFrlDestino.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    // Si se ha entrado en el ámbito de otra vista.
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.d("Mia", "Dentro");
                        if (v.getId() == R.id.frlDestino) {
                            v.setBackgroundColor(Color.DKGRAY);
                        }
                        break;
                    // Se ha salido del ámbito de otra vista.
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.d("Mia", "Fuera");
                        if (v.getId() == R.id.frlDestino) {
                            v.setBackgroundColor(Color.LTGRAY);
                        }
                        break;
                    // Si se deja caer sobre otra vista.
                    case DragEvent.ACTION_DROP:
                        Log.d("Mia", "Dejar caer");
                        if (v.getId() == R.id.frlDestino) {
                            // Se obtiene la vista que se ha dejado caer.
                            View original = (View) event.getLocalState();
                            // Se quita de su padre.
                            ((ViewGroup) original.getParent()).removeView(original);
                            // Se agrega al destino.
                            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams( FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT );
                            lp.gravity = Gravity.CENTER;
                            original.setLayoutParams(lp);
                            ((ViewGroup) v).addView(original);
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.d("Mia", "Terminado");
                        if (v.getId() == R.id.frlDestino) {
                            v.setBackgroundColor(Color.LTGRAY);
                        }
                        break;
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
