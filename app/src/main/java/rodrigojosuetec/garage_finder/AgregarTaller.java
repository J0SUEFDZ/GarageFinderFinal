package rodrigojosuetec.garage_finder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class AgregarTaller extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    EditText ReNombre;
    EditText ReTelefono;
    EditText ReCorreo;
    EditText ReDescripcion;
    EditText RePosicion;
    Button AgregarBtn;
    Button Map;
    String returnString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_taller);

        ReNombre = findViewById(R.id.txtNombre);
        ReCorreo = findViewById(R.id.txtEmail);
        ReDescripcion = findViewById(R.id.txtDescripcion);
        ReTelefono = findViewById(R.id.txtTelefono);
        RePosicion = findViewById(R.id.txtPosicion);

        AgregarBtn = findViewById(R.id.BtnAgregar);
        Map = findViewById(R.id.BtnMapa);
        AgregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    register();
            }
        });

    }

    public void AbrirMapa(View v){
        Intent intent = new Intent(this, Mapa.class);
        intent.putExtra("OPCION","1");
        startActivityForResult(intent, 1);
    }
    // This method is called when the second activity finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // get String data from Intent
                String returnString = data.getStringExtra("keyName");
                // set text view with string
                RePosicion.setText(returnString);
            }
        }
    }
    public boolean register(){
        String nombre = ReNombre.getText().toString();
        String correo = ReCorreo.getText().toString();
        String descripcion = ReDescripcion.getText().toString();
        String telefono = ReTelefono.getText().toString();
        String posicion = RePosicion.getText().toString();
        String[] latlong =  posicion.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);

        if(nombre.isEmpty() && correo.isEmpty() && descripcion.isEmpty() && telefono.isEmpty() && posicion.isEmpty()){
            Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            Taller nuevo = new Taller(nombre, telefono, correo, descripcion, latitude,longitude,"This");
            helper.InsertaTaller(nuevo);
            Toast.makeText(this, "Datos del taller insertados con el ID", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
