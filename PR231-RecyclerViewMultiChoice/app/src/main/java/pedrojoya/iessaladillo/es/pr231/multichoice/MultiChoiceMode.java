package pedrojoya.iessaladillo.es.pr231.multichoice;

import android.os.Bundle;
import android.util.SparseBooleanArray;

public class MultiChoiceMode implements ManyChoiceMode {

    private static final String STATE_CHECK_STATES="checkStates";
    private ParcelableSparseBooleanArray checkStates=new ParcelableSparseBooleanArray();

    @Override
    public void setChecked(int position, boolean isChecked) {
        if (isChecked) {
            checkStates.put(position, true);
        }
        else {
            checkStates.delete(position);
        }
    }

    @Override
    public boolean isChecked(int position) {
        return(checkStates.get(position, false));
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putParcelable(STATE_CHECK_STATES, checkStates);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        checkStates=state.getParcelable(STATE_CHECK_STATES);
    }

    @Override
    public int getCheckedCount() {
        return(checkStates.size());
    }

    @Override
    public void clearChecks() {
        checkStates.clear();
    }

    @Override
    public void visitChecks(Visitor v) {
        SparseBooleanArray copy=checkStates.clone();

        for (int i=0;i<copy.size();i++) {
            v.onCheckedPosition(copy.keyAt(i));
        }
    }

}