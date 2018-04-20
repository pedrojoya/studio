package es.iessaladillo.pedrojoya.pr013;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final long TIMEOUT_INITIAL = 5000; // milisegundos

    private ListView lstAnswers;
    private TextView lblTimeout;
    private Button btnCheck;
    private View vTimeout;
    private TextView lblScore;

    private ObjectAnimator objectAnimator;
    private ArrayAdapter<String> adapter;
    private int score = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        vTimeout = ActivityCompat.requireViewById(this, R.id.vTimeout);
        lblScore = ActivityCompat.requireViewById(this, R.id.lblScore);
        btnCheck = ActivityCompat.requireViewById(this, R.id.btnCheck);
        lblTimeout = ActivityCompat.requireViewById(this, R.id.lblTimeout);
        lstAnswers = ActivityCompat.requireViewById(this, R.id.lstAnswers);

        btnCheck.setOnClickListener(v -> checkAnswer());
        adapter = new ArrayAdapter<>(this, R.layout.activity_main_item, R.id.lblAnswer,
                getAnswers());
        lstAnswers.setAdapter(adapter);
        lstAnswers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lstAnswers.setOnItemClickListener(
                (adapterView, view, position, id) -> btnCheck.setEnabled(true));
    }

    @NonNull
    private ArrayList<String> getAnswers() {
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("Marrón");
        respuestas.add("Verde");
        respuestas.add("Blanco");
        respuestas.add("Negro");
        return respuestas;
    }

    @Override
    protected void onResume() {
        startTimeout();
        super.onResume();
    }

    private void startTimeout() {
        animateTimeout();
        new CountDownTimer(TIMEOUT_INITIAL, 1000) {

            public void onTick(long millisUntilFinished) {
                lblTimeout.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                objectAnimator.end();
                showCheck();
            }

        }.start();
    }

    private void showCheck() {
        btnCheck.setVisibility(View.VISIBLE);
        lblTimeout.setVisibility(View.GONE);
        vTimeout.setVisibility(View.GONE);
    }

    private void animateTimeout() {
        objectAnimator = ObjectAnimator.ofFloat(vTimeout, View.ROTATION, 0.0f,
                (TIMEOUT_INITIAL / 1000) * 360.0f);
        objectAnimator.setDuration(TIMEOUT_INITIAL);
        objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void checkAnswer() {
        int selectedPosition = lstAnswers.getCheckedItemPosition();
        String selectedAnswer = (String) lstAnswers.getItemAtPosition(selectedPosition);
        if (TextUtils.equals(selectedAnswer, "Blanco")) {
            lblScore.setText(getString(R.string.main_activity_score, "+", score));
            animateScore(true);
        } else {
            int reduction = score == 100 ? 50 : 25;
            score -= reduction;
            lblScore.setText(getString(R.string.main_activity_score, "-", reduction));
            animateScore(false);
            lstAnswers.setItemChecked(selectedPosition, false);
            adapter.remove(selectedAnswer);
            adapter.notifyDataSetChanged();
            btnCheck.setEnabled(false);
        }
    }

    private void animateScore(final boolean right) {
        lblScore.setBackgroundResource(
                right ? R.drawable.activity_main_lblscore_background : R.drawable
                        .activity_main_lblscore_background_wrong);
        lblScore.setVisibility(View.VISIBLE);
        lblScore.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .translationY(30)
                .setDuration(3000)
                .setInterpolator(new BounceInterpolator())
                .setListener(new AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!right) {
                            resetScorePosition();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                });
    }

    private void resetScorePosition() {
        lblScore.setVisibility(View.INVISIBLE);
        lblScore.setScaleX(1);
        lblScore.setScaleY(1);
        lblScore.setTranslationX(0);
        lblScore.setTranslationY(0);
    }

}
