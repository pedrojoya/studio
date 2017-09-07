package es.iessaladillo.pedrojoya.pr107.oldutils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import es.iessaladillo.pedrojoya.pr107.ui.main.MainActivityAdapter;

// Basado en http://blog.stylingandroid.com/material-part-6/#more-2907
@SuppressWarnings("unused")
public abstract class DragController implements RecyclerView.OnItemTouchListener {

    private static final int ANIMATION_DURATION = 100;

    private final RecyclerView recyclerView;
    private final ImageView overlay;
    private final GestureDetectorCompat gestureDetector;
    private boolean isDragging = false;
    private View draggingView;
    private boolean isFirst = true;
    private long draggingId = -1;
    private float startY = 0f;
    private Rect startBounds = null;
    private boolean isLongClick = false;

    public DragController(final RecyclerView recyclerView, ImageView overlay) {
        this.recyclerView = recyclerView;
        this.overlay = overlay;
        // Se usa un detector de gestor para detectar el click largo.
        GestureDetector.SimpleOnGestureListener longClickGestureListener = new GestureDetector
                .SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                // Si está habilitado y la vista es aún válida
                // (problemas con swipeToDismiss)
                if (recyclerView.findChildViewUnder(e.getX(), e.getY()) != null) {
                    // Ya se ha producido el long click.
                    // Para iniciar el drag es necesario que se haga
                    // click largo y además se mueva (es necesario
                    // hacerlo así ya que el click largo se reserva
                    // para el modo de acción contextual).
                    isLongClick = true;
                }
            }
        };
        this.gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                longClickGestureListener);
    }

    // Se llama antes de que sea procesado el evento Touch
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        // Si ya estábamos arrastrando, procesaremos nosotros el evento.
        if (isDragging) {
            return true;
        }
        // Si ya se ha producido el long click y ahora nos movemos
        // iniciamos el arrastre.
        if (isLongClick && e.getAction() == MotionEvent.ACTION_MOVE) {
            // Se inicia el arrastre (se pasan las coordeandas de
            // pulsación).
            isDragging = true;
            dragStart(e.getX(), e.getY());
            return true;
        }
        // Si NO estamos arrastrando, le pasamos el evento al detector de
        // gestos.
        gestureDetector.onTouchEvent(e);
        return false;
    }

    // Cuando se produce un evento Touch.
    @SuppressWarnings("UnusedAssignment")
    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        // Obtenemos la vista que está siendo arrastrada
        int x = (int) e.getX();
        int y = (int) e.getY();
        if (e.getAction() == MotionEvent.ACTION_UP) {
            // Si se suelta, se finaliza el arrastre.
            dragEnd();
            isDragging = false;
            isLongClick = false;
        } else {
            // Se sigue realizando el arrastre.
            drag(y);
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
        // Se almacena el id del item que se pretende arrastrar. Necesario
        // para el posterior intercambio.
        draggingId = recyclerView.getChildItemId(draggingView);
        // Se almacenan los límites de la vista sobre la que se ha pulsado,
        // para poder saber si el overlay es movido fuera de dichos límites.
        startBounds = new Rect(draggingView.getLeft(), draggingView.getTop(),
                draggingView.getRight(), draggingView.getBottom());
        // Se oculta la vista sobre la que se ha pulsado.
        draggingView.setVisibility(View.INVISIBLE);
        onDragStarted();
    }

    abstract void onDragStarted();

    // Durante el arrastre
    private void drag(int y) {
        // Se arrastra verticalmente la vista overlay respecto al posición de
        // la pulsación inicial
        overlay.setTranslationY(y - startY);
        // Si ha salido de los límites almacenados.
        if (!isInPreviousBounds()) {
            // Se obtiene la vista sobre la que se encuentra y se intercambia
            //  con la vista original.
            View view = recyclerView.findChildViewUnder(0, y);
            if (recyclerView.getChildAdapterPosition(view) != 0 && view != null) {
                swapViews(view);
            }
        }
    }

    // Intercambia el item correspondiente a la vista recibida con el item
    // correspondiente a la vista arrastrada.
    private void swapViews(View current) {
        // Se obtiene el id del elemento correspondiente a la vista sobre la
        // que se encuentra.
        long replacementId = recyclerView.getChildItemId(current);
        // Se obtienen del adaptador las posiciones en la que se encuentran
        // la  vista sobre la que se encuentra y la vista original.
        MainActivityAdapter adapter = (MainActivityAdapter) recyclerView.getAdapter();
        int start = adapter.getPositionForId(replacementId);
        int end = adapter.getPositionForId(draggingId);
        // Se intercambian los elementos.
        adapter.moveItem(start, end);
        // Si el elemento original era el primero visible,
        // se hace scroll a su nueva posición.
        if (isFirst) {
            recyclerView.scrollToPosition(end);
            isFirst = false;
        }
        // Se actualizan los límites de la vista, con su nueva posición.
        startBounds.top = current.getTop();
        startBounds.bottom = current.getBottom();
        onSwapDone();
    }

    abstract void onSwapDone();

    // Comprueba si la vista overlay sigue en en los límites almacenados.
    public boolean isInPreviousBounds() {
        float overlayTop = overlay.getTop() + overlay.getTranslationY();
        float overlayBottom = overlay.getBottom() + overlay.getTranslationY();
        return overlayTop < startBounds.bottom && overlayBottom > startBounds.top;
    }

    // Cuando finaliza el arrastre.
    private void dragEnd() {
        // Se limpia la vista overlay.
        overlay.setImageBitmap(null);
        // Se hace visible la vista sobre la que se había hecho long click.
        draggingView.setVisibility(View.VISIBLE);
        // Se anima el movimiento de la vista original desde donde ha quedado
        // la vista overlay hasta su posición original.
        float translationY = overlay.getTranslationY();
        draggingView.setTranslationY(translationY - startBounds.top);
        draggingView.animate().translationY(0f).setDuration(ANIMATION_DURATION).start();
        onDragEnded();
    }

    abstract void onDragEnded();

    // Muestra en la vista overlay un bitmap copia de la vista recibida.
    private void paintViewToOverlay(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        overlay.setImageBitmap(bitmap);
        overlay.setTop(0);
    }

}
