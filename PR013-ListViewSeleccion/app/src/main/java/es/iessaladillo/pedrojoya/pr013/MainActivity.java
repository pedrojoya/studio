package es.iessaladillo.pedrojoya.pr013;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
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
    private ObjectAnimator mObjectAnimator;
    private ArrayAdapter<String> mAdapter;
    private int mScore = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        vTimeout = findViewById(R.id.vTimeout);
        lblScore = findViewById(R.id.lblScore);
        btnCheck = findViewById(R.id.btnCheck);
        lblTimeout = findViewById(R.id.lblTimeout);
        lstAnswers = findViewById(R.id.lstAnswers);

        btnCheck.setOnClickListener(v -> checkAnswer());
        mAdapter = new ArrayAdapter<>(this, R.layout.activity_main_item, R.id.lblAnswer,
                getAnswers());
        lstAnswers.setAdapter(mAdapter);
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
                mObjectAnimator.end();
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
        mObjectAnimator = ObjectAnimator.ofFloat(vTimeout, View.ROTATION, 0.0f,
                (TIMEOUT_INITIAL / 1000) * 360.0f);
        mObjectAnimator.setDuration(TIMEOUT_INITIAL);
        mObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        mObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.start();
    }

    private void checkAnswer() {
        int selectedPosition = lstAnswers.getCheckedItemPosition();
        String selectedAnswer = (String) lstAnswers.getItemAtPosition(selectedPosition);
        if (TextUtils.equals(selectedAnswer, "Blanco")) {
            lblScore.setText(getString(R.string.main_activity_score, "+", mScore));
            animateScore(true);
        } else {
            int reduction = mScore == 100 ? 50 : 25;
            mScore -= reduction;
            lblScore.setText(getString(R.string.main_activity_score, "-", reduction));
            animateScore(false);
            lstAnswers.setItemChecked(selectedPosition, false);
            mAdapter.remove(selectedAnswer);
            mAdapter.notifyDataSetChanged();
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
