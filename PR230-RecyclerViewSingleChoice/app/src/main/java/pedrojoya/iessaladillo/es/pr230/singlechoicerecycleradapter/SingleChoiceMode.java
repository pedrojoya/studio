package pedrojoya.iessaladillo.es.pr230.singlechoicerecycleradapter;

import android.os.Bundle;

public class SingleChoiceMode implements OneChoiceMode {

    private static final String STATE_CHECKED = "checkedPosition";
    private int checkedPosition = -1;

    @Override
    public int getCheckedPosition() {
        return (checkedPosition);
    }

    @Override
    public void setChecked(int position, boolean isChecked) {
        if (isChecked) {
            checkedPosition = position;
        } else if (isChecked(position)) {
            checkedPosition = -1;
        }
    }

    @Override
    public boolean isChecked(int position) {
        return (checkedPosition == position);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putInt(STATE_CHECKED, checkedPosition);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        checkedPosition = state.getInt(STATE_CHECKED, -1);
    }

}