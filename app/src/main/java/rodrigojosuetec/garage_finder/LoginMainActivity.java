package rodrigojosuetec.garage_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class LoginMainActivity extends AppCompatActivity {

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


        final Button ButtonRegistrar = (Button) findViewById(R.id.ButtonRegistrar);
        final Button ButtonLogin = (Button) findViewById(R.id.ButtonLogin);

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginMainActivity.this, LoginActivity.class );
                LoginMainActivity.this.startActivity(loginIntent);


            }
        });

        ButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginMainActivity.this, RegisterActivity.class);
                LoginMainActivity.this.startActivity(registerIntent);
            }
        });




    }


}

