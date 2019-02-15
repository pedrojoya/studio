package pedrojoya.iessaladillo.es.pr104.ui.settings;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import pedrojoya.iessaladillo.es.pr104.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
