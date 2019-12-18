package app.projectwork.simulazione;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import app.projectwork.simulazione.Entita.Libro;
import app.projectwork.simulazione.Entita.Utente;

public class Login extends AppCompatActivity {

    private Button log, res;
    private EditText mail, pass;
    private TextView user1, user2;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mostro un semplice titolo nella toolbar di defaul
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.Titolo_Login);

        //collego i vari elementi al loro layout
        mail = (EditText) findViewById(R.id.mail2);
        pass = (EditText) findViewById(R.id.password2);
        log = (Button) findViewById(R.id.log);
        res = (Button) findViewById(R.id.res);
        user1 = (TextView) findViewById(R.id.user1);
        user2 = (TextView) findViewById(R.id.user2);

        //prendo la lista di utenti già registrati
        final List<Utente> list = DatiMock.getInstance().getUtenti();

        //prendo la mappa contenente la lista dei libri associata ad ogni utente
        Map<Integer, List<Libro>> mappa = DatiMock.getInstance().getMappaLibri();

        //user per test veloce
        user1.setText(list.get(0).toString());
        user2.setText(list.get(1).toString());

        //toast che mostra tutti gli utenti registrati
        for (int i = 0; i < list.size(); i++) {
            Toast.makeText(Login.this, list.get(i).toString(), Toast.LENGTH_SHORT).show();
        }

        //pressione del tasto login
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //scorro la lista degli utenti correntemente registrati e constrollo l'esistenza dell'utente inserito
                for (int i = 0; i < list.size(); i++) {

                    //assegno a delle variabili pemporane i valori inseriti per poi usarle per fare i controlli
                    String temp = list.get(i).getEmail();
                    String tempP = list.get(i).getPassword();
                    String m = mail.getText().toString().trim();
                    String p = pass.getText().toString().trim();

                    //controllo che mail e passwod rispettino i formati giusti
                    if (checkMail(m) && checkPassword(p)) {
                        if (m.equals(temp)) { //se la mail inserita esiste significa che l'utente è registrato
                            if (p.equals(tempP)) { //quindi controllo se la password inserita corrisponde alla mail

                                // ne prendo l'id per passarlo all'altra activity
                                int id = list.get(i).getId();
                                Intent go_to_main = new Intent(Login.this, ListaLibri.class);
                                go_to_main.putExtra("idUser", id);//passo l'id all'altra activity
                                startActivity(go_to_main);
                                finish();
                                break;
                            } else {//caso in cui mail corretta password sbagliata

                                //dialog di errore se la password non corrisponde a quella al momento della registrazione
                                final AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                                alert.setTitle("Attenzione!")
                                        .setMessage("Password non corretta")
                                        .setPositiveButton(R.string.continue_Button, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setCancelable(false);

                                AlertDialog alertDialog = alert.create();
                                alertDialog.show();
                                break;
                            }
                        } else {// caso in cui la mail è sbagliata

                            //se la mail è sbagliata e abbiamo controllato tutti gli utenti registrati
                            if (i == list.size()-1){

                                //dialog di errore se la mail non corrisponde con nessuna di quelle registrate
                                final AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
                                alert.setTitle("Attenzione!")
                                        .setMessage("Email non registrata")
                                        .setPositiveButton(R.string.continue_Button, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setCancelable(false);

                                AlertDialog alertDialog = alert.create();
                                alertDialog.show();
                            }
                        }
                    }
                }
            }
        });

        //pressione del pulsante registrati si sposta all'activity corrispondente ovvero di registrazione
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_to_book = new Intent(Login.this, Registrazione.class);
                startActivity(go_to_book);
            }
        });

    }

    //qualora avessi inserito dei dati e fossi andato e tornato dalla registrazione tramite il pulsante back
    //svuta i campi precedentemente inseriti
    @Override
    protected void onRestart() {
        super.onRestart();
        mail.setText("");
        pass.setText("");
    }

    //METODI DI CONTROLLO DELL'INSERIMENTO DEI DATI

    //metodo che controlla la forma di un e-mail ovvero sia del tipo testo@testo.testo
    private boolean isValidEmail(String em) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(em).matches();
    }

    //controlla che la mail non sia vuota e nel caso contrario se è del formato corretto chiamando il metodo precedente
    private boolean checkMail(String m) {

        if (TextUtils.isEmpty(m)) {
            mail.setError("Please enter a valid e-mail");
            return false;
        } else {
            if (!isValidEmail(m)) {
                mail.setError("Please enter a valid e-mail");
                return false;
            }
        }
        return true;
    }

    //controlla che la password non sia vuota e in caso contrario sia tra i 6 e 10 caratteri
    private boolean checkPassword(String pw) {

        if (TextUtils.isEmpty(pw)) {
            pass.setError("Please enter a password");
            return false;
        } else if (pw.length() < 6 || pw.length() > 10) {
            pass.setError("Password should be between 6 to 10 characters");
            return false;
        }
        return true;
    }

    //METODO CHE ALLA PRESSIONE DEL TASTO BACK NON CHIUDE L'APP MA CHIEDE DI PREMERE NUOVAMENTE PER CONFERMA
    @Override
    public void onBackPressed() {

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
