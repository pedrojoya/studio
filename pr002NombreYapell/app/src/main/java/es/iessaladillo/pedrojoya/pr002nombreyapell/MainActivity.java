package es.iessaladillo.pedrojoya.pr002nombreyapell;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView lblMensaje;
    private EditText lblNombre;
    private EditText lblApellidos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
    }

    protected void inicializar(){
        lblMensaje=(TextView)findViewById(R.id.lblMensaje);
        lblNombre= (EditText)findViewById(R.id.lblNombre);
        lblApellidos= (EditText)findViewById(R.id.lblApellidos);
    }

    public void ApellidosNombre(View v){
        lblMensaje.setText(lblApellidos.getText().toString()+","+lblNombre
                .getText().toString());
    }

    public void NombreApellidos(View v){
        lblMensaje.setText(lblNombre.getText().toString()+","+lblApellidos
                .getText().toString());
    }








}
