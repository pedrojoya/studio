package es.iessaladillo.pedrojoya.pr239.data.remote;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.util.ArrayList;
import java.util.Random;

class StockManager {

    private static final int STOCK_LOOKUP = 1;
    private final ArrayList<Listener> clients = new ArrayList<>();
    private boolean running;

    interface Listener {
        void onPriceChanged(int price);
    }

    private HandlerThread workerThread;
    private Handler handler;
    private final Random random = new Random();
    private int price;

    public void setUp() {
        workerThread = new HandlerThread("stockmanagerworker");
        workerThread.start();
        // Create handler for the worker thread.
        handler = new Handler(workerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == STOCK_LOOKUP) {
                    updatePriceFromServer();
                    for (Listener listener : clients) {
                        listener.onPriceChanged(price);
                    }
                    // Enqueue update in 1 second.
                    sendEmptyMessageDelayed(STOCK_LOOKUP, 1000);
                }
            }
        };
    }

    private void updatePriceFromServer() {
        // We simulate.
        price += random.nextInt(10);
    }

    public void addListener(Listener listener) {
        if (!running) {
            setUp();
        }
        clients.add(listener);
        handler.sendEmptyMessage(STOCK_LOOKUP);
    }

    public void removeListener(Listener listener) {
        clients.remove(listener);
        handler.postDelayed(() -> {
            if (clients.isEmpty()) {
                stopAllWork();
            }
        }, 5000);
    }

    private void stopAllWork() {
        workerThread.quitSafely();
        running = false;
    }

}
