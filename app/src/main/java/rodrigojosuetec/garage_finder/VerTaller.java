package rodrigojosuetec.garage_finder;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VerTaller extends AppCompatActivity {
    DatabaseHelper helper = new DatabaseHelper(this);
    EditText ReNombre;
    EditText ReTelefono;
    EditText ReCorreo;
    EditText ReDescripcion;
    Button AgregarBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_taller);

        ReNombre = findViewById(R.id.txtNombre);
        ReCorreo = findViewById(R.id.txtEmail);
        ReDescripcion = findViewById(R.id.txtDescripcion);
        ReTelefono = findViewById(R.id.txtTelefono);

        AgregarBtn = findViewById(R.id.BtnAgregar);
        AgregarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getIntent().getStringExtra("id")!=null) {
                    int id = Integer.parseInt(getIntent().getStringExtra("id"));
                    Taller nuevo = helper.ConsultarTaller(id);
                    if (nuevo != null) {
                        ReNombre.setText(nuevo.getNombre());
                        ReCorreo.setText(nuevo.getEmail());
                        ReDescripcion.setText(nuevo.getDescripcion());
                        ReTelefono.setText(nuevo.getTelefono());
                    }
                    else{
                        Toast.makeText(getBaseContext(), "No se pudo encontrar el taller", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



    }

}
