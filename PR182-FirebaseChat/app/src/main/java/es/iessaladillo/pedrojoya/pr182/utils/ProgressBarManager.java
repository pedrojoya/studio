package es.iessaladillo.pedrojoya.pr182.utils;

import android.view.View;
import android.widget.ProgressBar;

public class ProgressBarManager implements UIProgressManager {

    ProgressBar progressBar;

    public ProgressBarManager(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void showProgress(int progress) {
        progressBar.setIndeterminate(false);
        progressBar.setProgress(progress);
    }

    @Override
    public void showIndeterminateProgress() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
