package rodrigojosuetec.garage_finder;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity  extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    final Button logLogInBtn = (Button) findViewById(R.id.logLogInBtn);
    EditText logCorreo;
    EditText logPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        logCorreo = findViewById(R.id.logCorreo);
        logPassword = findViewById(R.id.logPassword);

        logLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.logLogInBtn){
                    EditText email = (EditText)findViewById(R.id.logCorreo);
                    String str = email.getText().toString();

                    EditText password = (EditText)findViewById(R.id.logPassword);
                    String pass = password.getText().toString();


                    String passwrd = helper.searchPass(str);
                    if(pass.equals(passwrd)){
                        Intent intent = new Intent(LoginActivity.this, DisplayActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast temp = Toast.makeText(LoginActivity.this , "Email o Contraseña no son correctos" , Toast.LENGTH_SHORT);
                        temp.show();
                    }




                }
            }
        });

    }




}
