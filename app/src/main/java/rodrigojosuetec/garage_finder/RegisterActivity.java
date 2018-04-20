package rodrigojosuetec.garage_finder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity  extends AppCompatActivity {


    DatabaseHelper helper = new DatabaseHelper(this);

    EditText ReNombre;
    EditText ReCorreo;
    EditText RePassword;
    EditText ReTelefono;
    Button RegistrarBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        ReNombre = findViewById(R.id.ReNombre);
        ReCorreo = findViewById(R.id.ReCorreo);
        RePassword = findViewById(R.id.RePassword);
        ReTelefono = findViewById(R.id.ReTelefono);

        RegistrarBtn = findViewById(R.id.RegistrarBtn);

        RegistrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()){
                    String user_nombre = ReNombre.getText().toString().trim();
                    String user_correo = ReCorreo.getText().toString().trim();
                    String user_password = RePassword.getText().toString().trim();
                    String user_telefono = ReTelefono.getText().toString().trim();

                    UserSingleton us = new UserSingleton();
                    us.setNombre(user_nombre);
                    us.setEmail(user_correo);
                    us.setPass(user_password);
                    us.setTelefono(user_telefono);

                    helper.insertUserSingleton(us);

                    Intent intent = new Intent(RegisterActivity.this, LoginMainActivity.class );
                    RegisterActivity.this.startActivity(intent);


                }

            }
        });
    }

    private Boolean validar(){
        Boolean result = false;

        String nombre = ReNombre.getText().toString();
        String correo = ReCorreo.getText().toString();
        String password = RePassword.getText().toString();
        String telefono = ReTelefono.getText().toString();

        if(nombre.isEmpty() && correo.isEmpty() && password.isEmpty() && telefono.isEmpty()){
            Toast.makeText(this, "Porfavor ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return true;
    }




}




