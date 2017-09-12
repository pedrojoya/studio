package es.iessaladillo.pedrojoya.pr095.ui.main;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr095.R;
import es.iessaladillo.pedrojoya.pr095.data.model.Song;
import es.iessaladillo.pedrojoya.pr095.services.MusicService;

public class MainFragment extends Fragment implements
        AdapterView.OnItemClickListener {

    public interface Listener {
        void onReproduciendo();
        void onParado();
    }

    public static final String EXTENSION_ARCHIVO = ".mp3";

    private ListView lstCanciones;
    private DownloadManager mGestorDescargas;
    private BroadcastReceiver mReceptorDescargaFinalizada;
    private MainFragmentAdapter mAdaptador;
    private Intent intentServicio;
    private LocalBroadcastManager mGestorLocal;
    private BroadcastReceiver mReceptorCancionFinalizada;


    private Listener mListener;

    public MainFragment() {}

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVistas(getView());
        intentServicio = new Intent(getActivity().getApplicationContext(),
                MusicService.class);
        // Se obtiene el mGestorLocal de receptores locales.
        mGestorLocal = LocalBroadcastManager.getInstance(getActivity());
        // Se obtiene el gestor de descargas
        mGestorDescargas = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        // Se crea el mReceptorCancionFinalizada de mensajes desde el servicio.
        mReceptorCancionFinalizada = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se reproduce la siguiente canción.
                int siguiente = (lstCanciones.getCheckedItemPosition() + 1)
                        % lstCanciones.getCount();
                reproducirCancion(siguiente);
            }
        };
        // Se crea el mReceptorCancionFinalizada de eventos relacionados con la descarga.
        mReceptorDescargaFinalizada = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // Se comprueba el estado de la descarga.
                comprobarDescarga(intent);
            }

        };
    }

    private void initVistas(View view) {
        lstCanciones = (ListView) view.findViewById(R.id.lstCanciones);
        mAdaptador = new MainFragmentAdapter(getActivity(), getListaCanciones(), lstCanciones);
        if (lstCanciones != null) {
            lstCanciones.setAdapter(mAdaptador);
            lstCanciones.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lstCanciones.setOnItemClickListener(this);
            lstCanciones.setEmptyView(view.findViewById(R.id.rlListaVacia));
            // API 21+ funcionará con CoordinatorLayout.
            ViewCompat.setNestedScrollingEnabled(lstCanciones, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Se registra en el gestor de receptores locales el receptor de canción finalizada.
        IntentFilter filtroCompletada = new IntentFilter(MusicService.ACTION_COMPLETADA);
        mGestorLocal.registerReceiver(mReceptorCancionFinalizada, filtroCompletada);
        // Se registra en el gestor general de receptores el receptor de descarga finalizada.
        IntentFilter filtroDescargas = new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        getActivity().registerReceiver(mReceptorDescargaFinalizada, filtroDescargas);
        // Se notifica a la actividad el estado.
        if (lstCanciones.getCheckedItemPosition() ==
                AdapterView.INVALID_POSITION) {
            mListener.onParado();
        }
        else {
            mListener.onReproduciendo();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Se quita el registro del receptor de canción finalizada.
        mGestorLocal.unregisterReceiver(mReceptorCancionFinalizada);
        // Se quita el registro del receptor de descarga finalizada.
        getActivity().unregisterReceiver(mReceptorDescargaFinalizada);
    }

    // Comprueba el estado de la descarga que se ha completado.
    private void comprobarDescarga(Intent intent) {
        // Se obtiene el id de la descarga.
        long downloadId = intent.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        // Se realiza la consulta para obtener los datos de la descarga.
        DownloadManager.Query consulta = new DownloadManager.Query();
        consulta.setFilterById(downloadId);
        Cursor c = mGestorDescargas.query(consulta);
        // Se comprueba el registro resultante.
        if (c != null) {
            if (c.moveToFirst()) {
                // Dependiendo del estado de la descarga.
                int estado = c.getInt(c
                        .getColumnIndex(DownloadManager.COLUMN_STATUS));
                switch (estado) {
                    // Si la descarga se ha realizado correctamente.
                    case DownloadManager.STATUS_SUCCESSFUL:
                        // Se informa de la descarga permitiendo la reproducción.
                        String sUri = c.getString(c
                                .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        // Se invalidan los datos para que se actualice el icono en el elemento.
                        mAdaptador.notifyDataSetInvalidated();
                        // Se reproduce la canción.
                        reproducir(Uri.parse(sUri));
                        break;
                    // Si se ha producido un error en la descarga.
                    case DownloadManager.STATUS_FAILED:
                        // Se informa al usuario del error.
                        String motivo = c.getString(c
                                .getColumnIndex(DownloadManager.COLUMN_REASON));
                        mostrarToast(getString(R.string.error, motivo));
                        break;
                }
            }
            // Se cierra el cursor.
            c.close();
        }
    }

    // Cuando se pulsa en una canción de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se reproduce la canción, descargándola si es necesario.
        reproducirCancion(position);
    }

    // Reproduce una canción, descargándola si es necesario.
    private void reproducirCancion(int position) {
        lstCanciones.setItemChecked(position, true);
        // Se invalidan los datos para que se actualice el icono en el elemento que deja de
        // estar reproduciendose y el que pasa a reproducirse.
        mAdaptador.notifyDataSetInvalidated();
        // Se comprueba si la canción está disponible en local.
        Song song = (Song) lstCanciones.getItemAtPosition(position);
        if (song != null) {
            File directory = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            File fichero = new File(directory, song.getNombre()
                    + EXTENSION_ARCHIVO);
            if (fichero.exists()) {
                // Se reproduce.
                reproducir(Uri.fromFile(fichero));
            } else {
                // Se descarga.
                descargar(song);
            }
        }
    }

    // Descarga una canción.
    private void descargar(Song song) {
        // Se crea la solicitud de descarga.
        DownloadManager.Request solicitud = new DownloadManager.Request(
                Uri.parse(song.getUrl()));
        solicitud.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        solicitud.setAllowedOverRoaming(false);
        solicitud.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_MUSIC, song.getNombre()
                        + EXTENSION_ARCHIVO);
        // solicitud.setDestinationInExternalFilesDir(this,
        // Environment.DIRECTORY_DOWNLOADS,
        // song.getNombre() + EXTENSION_ARCHIVO);
        solicitud.setTitle(song.getNombre());
        solicitud.setDescription(getString(R.string.descripcion, song.getNombre(), song.getDuracion()));
        solicitud.allowScanningByMediaScanner();
        solicitud
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // Se encola la solicitud.
        mGestorDescargas.enqueue(solicitud);
        // Se informa al usuario.
        mostrarToast(getString(R.string.descargando, song.getNombre()));
    }

    // Muestra un toast con el mensaje.
    private void mostrarToast(String mensaje) {
        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG).show();
    }

    private void reproducir(Uri uri) {
        // Se invalidan los datos para que se actualice el icono en el elemento.
        mAdaptador.notifyDataSetInvalidated();
        File directory = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        File fichero = new File(directory, uri.getLastPathSegment());
        // Se inicia el servicio pasándole como extra la URL de la canción a reproducir.
        intentServicio.putExtra(MusicService.EXTRA_PATH_CANCION,
                fichero.getAbsolutePath());
        getActivity().startService(intentServicio);
        // Se notifica a la actividad.
        mListener.onReproduciendo();
    }

    public void playstop() {
        if (lstCanciones.getCheckedItemPosition() == AdapterView.INVALID_POSITION) {
            reproducirCancion(0);
        } else {
            pararServicio();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            mListener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Para el servicio y cambia el aspecto visual.
    private void pararServicio() {
        getActivity().stopService(intentServicio);
        lstCanciones.setItemChecked(lstCanciones.getCheckedItemPosition(), false);
        mListener.onParado();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuDownloads) {
            // Se muesta la actividad estándar de descargas.
            mostrarDescargas();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Muestra la actividad estándar de descargas.
    private void mostrarDescargas() {
        Intent i = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(i);
    }

    // Crea y retorna el ArrayList de canciones.
    private ArrayList<Song> getListaCanciones() {
        ArrayList<Song> canciones = new ArrayList<>();
        canciones
                .add(new Song("Morning Mood", "3:43", "Grieg",
                        "https://www.youtube.com/audiolibrary_download?vid=036500ffbf472dcc"));
        canciones
                .add(new Song("Brahms Lullaby", "1:46", "Ron Meixsell",
                        "https://www.youtube.com/audiolibrary_download?vid=9894a50b486c6136"));
        canciones
                .add(new Song("Triangles", "3:05", "Silent Partner",
                        "https://www.youtube.com/audiolibrary_download?vid=8c9219f54213cb4f"));
        canciones
                .add(new Song("From Russia With Love", "2:26", "Huma-Huma",
                        "https://www.youtube.com/audiolibrary_download?vid=4e8d1a0fdb3bbe12"));
        canciones
                .add(new Song("Les Toreadors from Carmen",
                        "2:21", "Bizet",
                        "https://www.youtube.com/audiolibrary_download?vid=fafb35a907cd6e73"));
        canciones
                .add(new Song("Funeral March", "9:25", "Chopin",
                        "https://www.youtube.com/audiolibrary_download?vid=4a7d058f20d31cc4"));
        canciones
                .add(new Song("Dancing on Green Grass", "1:54", "The Green Orbs",
                        "https://www.youtube.com/audiolibrary_download?vid=81cb790358aa232c"));
        canciones
                .add(new Song("Roller Blades", "2:10", "Otis McDonald",
                        "https://www.youtube.com/audiolibrary_download?vid=42b9cb1799a7110f"));
        canciones
                .add(new Song("Aurora Borealis", "1:40", "Bird Creek",
                        "https://www.youtube.com/audiolibrary_download?vid=71e7af02e3fde394"));
        canciones
                .add(new Song("Sour Tennessee Red", "2:11", "John Deley and the 41",
                        "https://www.youtube.com/audiolibrary_download?vid=f24590587cad9a9b"));
        canciones
                .add(new Song("Water Lily", "2:09", "The 126ers",
                        "https://www.youtube.com/audiolibrary_download?vid=5875315a21edd73b"));
        canciones
                .add(new Song("Redhead From Mars", "3:29", "Silent Partner",
                        "https://www.youtube.com/audiolibrary_download?vid=7b17c89cc371a1bc"));
        canciones
                .add(new Song("Destructoid", "1:34", "MK2",
                        "https://www.youtube.com/audiolibrary_download?vid=5ad1f342b4676fc1"));
        return canciones;
    }
}
