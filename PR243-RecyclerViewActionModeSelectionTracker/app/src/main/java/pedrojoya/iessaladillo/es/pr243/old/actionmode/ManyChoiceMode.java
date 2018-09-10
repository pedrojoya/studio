package pedrojoya.iessaladillo.es.pr243.old.actionmode;

import android.os.Bundle;

@SuppressWarnings("unused")
public interface ManyChoiceMode {
    void setChecked(int position, boolean isChecked);
    boolean isChecked(int position);
    void onSaveInstanceState(Bundle state);
    void onRestoreInstanceState(Bundle state);
    int getCheckedCount();
    void clearChecks();
    void visitChecks(Visitor v);
    interface Visitor {
        void onCheckedPosition(int position);
    }
}