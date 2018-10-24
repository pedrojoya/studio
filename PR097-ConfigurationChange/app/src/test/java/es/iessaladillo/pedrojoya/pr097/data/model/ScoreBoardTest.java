package es.iessaladillo.pedrojoya.pr097.data.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @Before
    public void setup() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    public void shouldScoreBeZeroInitially() {
        assertEquals(0, scoreBoard.getScore());
    }

    @Test
    public void shouldIncrementScore() {
        scoreBoard.incrementScore();

        assertEquals(1, scoreBoard.getScore());
    }

}
