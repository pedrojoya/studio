package es.iessaladillo.pedrojoya.pr097.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr097.data.model.ScoreBoard;

@SuppressWarnings("WeakerAccess")
public class ViewModelActivityViewModel extends ViewModel {

    @NonNull
    private final ScoreBoard scoreBoard = new ScoreBoard();

    @NonNull
    ScoreBoard getScoreBoard() {
        return scoreBoard;
    }

    void increment() {
        scoreBoard.incrementScore();
    }

}
