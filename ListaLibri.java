package app.projectwork.simulazione;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.List;
import java.util.Map;
import app.projectwork.simulazione.Entita.Libro;

public class ListaLibri extends AppCompatActivity {

    private Map<Integer, List<Libro>> mappa;
    private int id;
    List<Libro> arrayList;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_libri);

        //mostro un semplice titolo nella toolbar di defaul
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.Titolo_Lista);

        //recupero l'id dell'utente dalla schermata precedente
        Intent intent = getIntent();
        id = intent.getIntExtra("idUser", 0);

        //prendo la mappa contenente la lista dei libri associata ad ogni utente
        mappa = DatiMock.getInstance().getMappaLibri();

        //se l'id passato è corretto prendo la lista di libri associata a quell'utente e ne creo una lista
        if (id != 0) {
            arrayList = mappa.get(id);

            //dichiaro l'adapter della lista del tipo appartenente alla classe creata a parte
            ListaAdapter adapter = new ListaAdapter(getApplicationContext(), arrayList, id);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.List);
            recyclerView.setAdapter(adapter);
        }
    }

    //creo un semplice pulsante salva nella toolbar di default per completare l'operazione di selezione di libri
    // già letti
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //alla pressione del tasto salva ritorno alla schermata di login
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.salva) {

            Intent return_to_log = new Intent(ListaLibri.this, Login.class);
            startActivity(return_to_log);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //METODO CHE ALLA PRESSIONE DEL TASTO BACK NON CHIUDE L'APP MA CHIEDE DI PREMERE NUOVAMENTE PER CONFERMA
    @Override
    public void onBackPressed() {

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit or click Salva to continue", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
