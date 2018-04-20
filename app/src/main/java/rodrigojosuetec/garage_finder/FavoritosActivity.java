package rodrigojosuetec.garage_finder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class FavoritosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        final ImageButton BtnBack = (ImageButton) findViewById(R.id.BtnBack);

        BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(FavoritosActivity.this, DisplayActivity.class );
                FavoritosActivity.this.startActivity(backIntent);


            }
        });




    }
}
