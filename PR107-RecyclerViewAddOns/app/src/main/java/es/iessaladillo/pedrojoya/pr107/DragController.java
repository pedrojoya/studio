package es.iessaladillo.pedrojoya.pr107;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class DragController implements RecyclerView.OnItemTouchListener {
    public static final int ANIMATION_DURATION = 100;
    private RecyclerView recyclerView;
    private ImageView overlay;
    private final GestureDetectorCompat gestureDetector;

    private boolean isDragging = false;
    private View draggingView;
    private boolean isFirst = true;
    private int draggingItem = -1;
    private float startY = 0f;
    private Rect startBounds = null;

    public DragController(RecyclerView recyclerView, ImageView overlay) {
        this.recyclerView = recyclerView;
        this.overlay = overlay;
        // Se usa un detector de gestor para detectar el click largo.
        GestureDetector.SimpleOnGestureListener longClickGestureListener =
                new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                // Se inicia el arrastre (se pasan las coordeandas de
                // pulsación).
                isDragging = true;
                dragStart(e.getX(), e.getY());
            }
        };
        this.gestureDetector = new GestureDetectorCompat(
                recyclerView.getContext(), longClickGestureListener);
    }

    // Se llama antes de que sea procesado el evento Touch
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        // Si estamos arrastrando, procesaremos nosotros el evento.
        if (isDragging) {
            return true;
        }
        // Si NO estamos arrastrando, le pasamos el evento al detector de
        // gestos.
        gestureDetector.onTouchEvent(e);
        return false;
    }

    // Cuando se produce un evento Touch.
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        // Obtenemos la vista que está siendo arrastrada
        int x = (int) e.getX();
        int y = (int) e.getY();
        View view = recyclerView.findChildViewUnder(x, y);
        if (e.getAction() == MotionEvent.ACTION_UP) {
            // Si se suelta, se finaliza el arrastre.
            dragEnd(view);
            isDragging = false;
        } else {
            // Se sigue realizando el arrastre.
            drag(y, view);
        }
    }

    // Inicia el arrastre.
    private void dragStart(float x, float y) {
        // Se obtiene la vista sobre la que se ha hecho el long click a
        // partir de las coordenadas de pulsación.
        draggingView = recyclerView.findChildViewUnder(x, y);
        // Se obtiene la primera vista visible de la lista.
        View first = recyclerView.getChildAt(0);
        // Se obtiene si la vista sobra la que se ha pulsado es la primera
        // vista visible de la lista.
        isFirst = draggingView == first;
        // Se obtiene el desplazamiento vertical de donde se ha pulsado respecto
        // a donde empieza la vista sobre la que se ha pulsado.
        startY = y - draggingView.getTop();
        // Se crea una imagen de la vista pulsado y se muestra en la vista
        // overlay (que es la que verdaderamente se arrastrará)
        paintViewToOverlay(draggingView);
        // Se desplaza la vista overlay para que quede justo encima de la
        // vista sobre la que se ha pulsado.
        overlay.setTranslationY(y - startY);
        // Se oculta la vista sobre la que se ha pulsado.
        draggingView.setVisibility(View.INVISIBLE);
        draggingItem = recyclerView.indexOfChild(draggingView);
        // Se almacenan los límites de la vista sobre la que se ha pulsado,
        // para poder saber si el overlay es movido fuera de dichos límites.
        startBounds = new Rect(draggingView.getLeft(), draggingView.getTop(),
                draggingView.getRight(), draggingView.getBottom());
    }

    // Durante el arrastre
    private void drag(int y, View view) {
        // Se arrastra verticalmente la vista overlay respecto al posición de
        // la pulsación inicial
        overlay.setTranslationY(y - startY);
    }

    // Cuando finaliza el arrastre.
    private void dragEnd(View view) {
        // Se limpia la vista overlay.
        overlay.setImageBitmap(null);
        // Se hace visible la vista sobre la que se había hecho long click.
        draggingView.setVisibility(View.VISIBLE);
        // Se anima el movimiento de la vista original desde donde ha quedado
        // la vista overlay hasta su posición original.
        float translationY = overlay.getTranslationY();
        draggingView.setTranslationY(translationY - startBounds.top);
        draggingView.animate().translationY(0f).setDuration(ANIMATION_DURATION).start();
    }

    // Muestra en la vista overlay un bitmap copia de la vista recibida.
    private void paintViewToOverlay(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        overlay.setImageBitmap(bitmap);
        overlay.setTop(0);
    }

}