package rodrigojosuetec.garage_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class GestionarAuto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_auto);

        final ImageButton BtnBack = (ImageButton) findViewById(R.id.BtnBack);
        final ImageButton BtnCar = (ImageButton) findViewById(R.id.BtnCar);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(GestionarAuto.this, DisplayActivity.class );
                GestionarAuto.this.startActivity(backIntent);


            }
        });

        BtnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carIntent = new Intent(GestionarAuto.this, AgregaAutoActivity.class);
                GestionarAuto.this.startActivity(carIntent);
            }
        });

        //Falta que guarde en lista los vehiculos creados en agregarauto

    }
}
