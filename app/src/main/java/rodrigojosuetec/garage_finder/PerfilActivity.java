package rodrigojosuetec.garage_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        final Button BtnGestionarVehiculor = (Button) findViewById(R.id.BtnGestionarVehiculo);
        final Button BtnFavoritos = (Button) findViewById(R.id.BtnFavoritos);
        final Button BtnHistorial = (Button) findViewById(R.id.BtnHistorial);

        /*

        BtnGestionarVehiculor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gesautoIntent = new Intent(PerfilActivity.this, GestionarAuto.class );
                GestionarAuto.this.startActivity(gesautoIntent);
            }
        });

        BtnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favIntent = new Intent(PerfilActivity.this, FavoritosActivity.class );
                FavoritosActivity.this.startActivity(favIntent);
            }
        });

        BtnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent histIntent = new Intent(PerfilActivity.this, HistorialViajesActivity.class );
                HistorialViajesActivity.this.startActivity(histIntent);
            }
        });

        */





    }
}
