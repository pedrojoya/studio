package es.iessaladillo.pedrojoya.pr184.utils.managers.uiprogress;

import android.view.View;

import com.github.silvestrpredko.dotprogressbar.DotProgressBar;

public class DotProgressBarManager implements UIProgressManager {

    DotProgressBar progressBar;

    public DotProgressBarManager(DotProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void showProgress(int progress) {
        //
    }

    @Override
    public void showIndeterminateProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
