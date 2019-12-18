package app.projectwork.simulazione;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //istanzio il singleton contenenti i dati di partenza
        DatiMock.getInstance().init();

        //vado alla pagina di login
        Intent go_to_main = new Intent(MainActivity.this, Login.class);
        startActivity(go_to_main);
        finish();
    }
}
