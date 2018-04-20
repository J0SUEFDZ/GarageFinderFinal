package rodrigojosuetec.garage_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgregaAutoActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    EditText txtMarca;
    EditText txtModelo;
    EditText txtEstilo;
    EditText txtCombustible;
    Button BtnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agrega_auto);

        txtMarca = findViewById(R.id.txtMarca);
        txtModelo = findViewById(R.id.txtModelo);
        // txtEstilo = findViewById(R.id.txtEstilo);
        //txtCombustible = findViewById(R.id.txtCombustible);

        BtnAgregar = findViewById(R.id.BtnAgregar);

        BtnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    String auto_marca = txtMarca.getText().toString().trim();
                    String auto_modelo = txtModelo.getText().toString().trim();
                    String auto_estilo = txtEstilo.getText().toString().trim();
                    String auto_combustible = txtCombustible.getText().toString().trim();

                    Intent intent = new Intent(AgregaAutoActivity.this, GestionarAuto.class);
                    AgregaAutoActivity.this.startActivity(intent);


                }
            }
        });
    }

        private Boolean validar(){
            Boolean result = false;

            String marca = txtMarca.getText().toString();
            String modelo = txtModelo.getText().toString();
            String estilo = txtEstilo.getText().toString();
            String combustible = txtCombustible.getText().toString();

            if(marca.isEmpty() && modelo.isEmpty() && estilo.isEmpty() && combustible.isEmpty()){
                Toast.makeText(this, "Porfavor ingrese todos los datos", Toast.LENGTH_SHORT).show();
            }else{
                result = true;
            }
            return true;
        }




    }

