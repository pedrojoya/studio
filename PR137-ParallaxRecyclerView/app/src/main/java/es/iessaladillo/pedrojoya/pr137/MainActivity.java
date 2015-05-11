package es.iessaladillo.pedrojoya.pr137;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;


public class MainActivity extends AppCompatActivity implements AlumnosAdapter.OnItemClickListener, AlumnosAdapter.OnItemLongClickListener, AlumnosAdapter.OnEmptyStateChangedListener {

    private ImageView imgFoto;
    private ScrollView svScroll;
    private Toolbar toolbar;
    private TextView lblTitulo;
    private View vOverlay;
    private RecyclerView lstAlumnos;
    private AlumnosAdapter mAdaptador;
    private ActionButton btnAgregar;
    private TextView lblNoHayAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        btnAgregar = (ActionButton) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarAlumno(DB.getNextAlumno());
            }
        });
        lblNoHayAlumnos = (TextView) findViewById(R.id.lblNoHayAlumnos);
        lstAlumnos = (RecyclerView) findViewById(R.id.lstAlumnos);
        lstAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnosAdapter(DB.getAlumnos());
        mAdaptador.setOnItemClickListener(this);
        mAdaptador.setOnItemLongClickListener(this);
        mAdaptador.setOnEmptyStateChangedListener(this);
        lstAlumnos.setAdapter(mAdaptador);
        lstAlumnos.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lstAlumnos.setItemAnimator(new DefaultItemAnimator());
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        lblTitulo = (TextView) findViewById(R.id.lblTitulo);
        vOverlay = findViewById(R.id.vOverlay);
        // Cuando se hace scroll se realiza el efecto parallax.
        parallax(0, imgFoto);
        lstAlumnos.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                btnAgregar.hide();
            }

            @Override
            public void onShow() {
                btnAgregar.show();
            }
        });
        lstAlumnos.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                parallax(recyclerView.computeVerticalScrollOffset(), imgFoto);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    // Realiza el efecto parallax sobre la imagen a partir del scroll realizado.
    private void parallax(int scrollY, ImageView image) {

        // Datos básicos.
        int imageHeight = getResources().getDimensionPixelSize(R.dimen.image_height);
        // En el onCreate la action bar aún no tiene altura.
        int actionBarHeight = getResources().getDimensionPixelSize(R.dimen
                        .abc_action_bar_default_height_material);
        if (getSupportActionBar() != null) {
            actionBarHeight = Math.max(getSupportActionBar().getHeight(),
                    getResources().getDimensionPixelSize(R.dimen
                            .abc_action_bar_default_height_material));
        }
        // En el onCreate lblTitulo aún no tiene altura.
        int lblTituloHeight = Math.max(lblTitulo.getHeight(), actionBarHeight);
        int baseColor = getResources().getColor(R.color.primary);
        Log.d(getString(R.string.app_name), "ScrollY: " + scrollY);

        // La imagen se traslada hacia arriba la mitad de la cantidad de scroll
        // que hayamos realizado. El overlay se traslada con la imagen.
        image.setTranslationY(-scrollY / 2);
        vOverlay.setTranslationY(-scrollY / 2);
        Log.d(getString(R.string.app_name), "Traslación imagen: " +
                -scrollY);

        /* Si la imagen no tuviera título, simplemente haríamos que la toolbar
           se volviera más opaca conforme se hiciera scroll (máximo 1).
        float alpha = Math.min(1, (float) scrollY / imageHeight);
        int alphaColor = getColorWithAlpha(alpha, baseColor);
        toolbar.setBackgroundColor(alphaColor);
        */

        // Escalado del título. Cuanto más scroll más pequeño será la
        // escala. Como mínimo 1. Como máximo 1,3.
        float flexibleRange = imageHeight - actionBarHeight;
        float scale = 1 + ((flexibleRange - scrollY) / flexibleRange);
        scale = Math.max(1, scale);
        scale = Math.min(1.3f, scale);
        lblTitulo.setPivotX(0);
        lblTitulo.setPivotY(0);
        lblTitulo.setScaleX(scale);
        lblTitulo.setScaleY(scale);
        Log.d(getString(R.string.app_name), "Escala: " + scale);

        // El overlay se vuelve más opaca conforme se hace scroll.
        float maxOverlayDistance = imageHeight - actionBarHeight +
                (lblTituloHeight * scale);
        float overlayAlpha = Math.min(maxOverlayDistance, scrollY)
                / maxOverlayDistance;
        vOverlay.setAlpha(overlayAlpha);
        Log.d(getString(R.string.app_name), "Alpha overlay: " + overlayAlpha);

        // Translación hacia abajo del título. Caunto más scroll menos
        // traslación. Como máximo la altura de la imagen menos
        // la altura del TextView (en la escala actual).
        // Como minimo 0 (situándose debajo de la toolbar).
        int maxTitleTranslationY = (int) (imageHeight - lblTituloHeight *
                scale);
        int titleTranslationY = maxTitleTranslationY - scrollY;
        titleTranslationY = Math.max(0, titleTranslationY);
        lblTitulo.setTranslationY(titleTranslationY);

        // El título de la toolbar sólo debe mostrarse si el título se ha
        // trasladado por completo.
        if (titleTranslationY == 0) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
            toolbar.setBackgroundColor(getResources().getColor(R.color.primary));
        } else {
            getSupportActionBar().setTitle("");
            toolbar.setBackgroundColor(getResources().getColor(android.R
                    .color.transparent));
        }
        Log.d(getString(R.string.app_name), "Traslación título: " + titleTranslationY);
    }

    // Retorna el color base aplicandole una determinada transaparencia alpha.
    private int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    // Cuando se hace click sobre un elemento de la lista.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Toast.makeText(this, getString(R.string.ha_pulsado_sobre) + " " + alumno.getNombre(),
                Toast.LENGTH_SHORT).show();
    }

    // Cuando se hace long click sobre un elemento de la lista.
    @Override
    public void onItemLongClick(View view, Alumno alumno, int position) {
        // Se elimina el alumno.
        mAdaptador.removeItem(position);

    }

    // Agrega un alumno a la lista.
    private void agregarAlumno(Alumno alumno) {
        // Se agrega el alumno.
        mAdaptador.addItem(alumno, mAdaptador.getItemCount());
        lstAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
    }

    @Override
    public void onEmptyStateChanged(boolean isEmpty) {
        lblNoHayAlumnos.setVisibility(isEmpty ? View.VISIBLE : View.INVISIBLE);
    }
}
