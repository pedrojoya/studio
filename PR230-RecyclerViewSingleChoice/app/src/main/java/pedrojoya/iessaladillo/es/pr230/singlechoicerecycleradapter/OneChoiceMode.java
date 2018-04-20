package pedrojoya.iessaladillo.es.pr230.singlechoicerecycleradapter;

import android.os.Bundle;

@SuppressWarnings("unused")
interface OneChoiceMode {
    int getCheckedPosition();
    void setChecked(int position, boolean isChecked);
    boolean isChecked(int position);
    void onSaveInstanceState(Bundle state);
    void onRestoreInstanceState(Bundle state);
}
