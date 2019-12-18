package app.projectwork.simulazione;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.regex.Pattern;

import app.projectwork.simulazione.Entita.Utente;

public class Registrazione extends AppCompatActivity {

    private Button resButton;
    private EditText pass, repetpass, nome, cognome, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);

        //mostro un semplice titolo nella toolbar di defaul
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.Titolo_Registrazione);

        //collego i vari elementi al loro layout
        resButton = (Button) findViewById(R.id.button3);
        nome = (EditText) findViewById(R.id.nome2);
        cognome = (EditText) findViewById(R.id.cognome2);
        mail = (EditText) findViewById(R.id.mail2);
        pass = (EditText) findViewById(R.id.password2);
        repetpass = (EditText) findViewById(R.id.Repetpass2);

        //ricordo all'utente di inserire una mail di formato corretto
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkMail(mail.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //ricordo all'utente di inserire una password di range tra 6 e 10
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPassword(pass.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //ricordo all'utente di ripetere correttamente la password
        repetpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkConfPassword(repetpass.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkConfirmPassword(pass.getText().toString().trim(), repetpass.getText().toString().trim());
            }
        });

        //click del pulsante registrati
        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //controllo che tutti i campi siano stati riempiti correttamente
                if (checkNome(nome.getText().toString().trim()) &&
                        checkCognome(cognome.getText().toString().trim()) &&
                        checkMail(mail.getText().toString().trim()) &&
                        checkPassword(pass.getText().toString().trim()) &&
                        checkConfirmPassword(pass.getText().toString().trim(), repetpass.getText().toString().trim())) {

                    //prendo i dati inseriti dall'utente
                    String m = mail.getText().toString().trim();
                    String n = nome.getText().toString().trim();
                    String c = cognome.getText().toString().trim();
                    String p = pass.getText().toString().trim();

                    //creo un nuovo utente con i dati inseriti
                    Utente u = new Utente(m, n, c, p);

                    //prendo la lista di utenti già registrati
                    List<Utente> list = DatiMock.getInstance().getUtenti();

                    //eseguo il metodo che controlla che la mail non sia già registrata e in caso contrario aggiunge
                    //l'utente nell'elenco degli utenti registrati
                    checkUtente(list, u);
                }
            }
        });
    }

    private void checkUtente(List<Utente> l, Utente u) {

        //se non ci sono utenti registrati crea l'utente
        if (l.size() == 0) {
            DatiMock.getInstance().addUtente(u); //creo l'utente
            DatiMock.getInstance().creaValoreMappa(1);//creo la lista di libri per l'utente
            //ritorno alla pagina di login
            Intent go_to_Login = new Intent(Registrazione.this, Login.class);
            startActivity(go_to_Login);
            finish();
        } else {
            //se ci sono utenti registrati ne scorre l'elenco
            for (int i = 0; i < l.size(); i++) {

                String temp = l.get(i).getEmail();

                //se trova una mail corrispondente all'inserita lancia un dialog di errore
                if (u.getEmail().equals(temp)) {

                    //dialog di errore se la mail corrisponde con una di quelle registrate
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Registrazione.this);
                    alert.setTitle("Attenzione!")
                            .setMessage("Email già registrata")
                            .setPositiveButton(R.string.continue_Button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setCancelable(false);

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                } else {
                    //se non trova una mail corrispondente allora l'utente non esiste e lo crea
                    if ((!u.getEmail().equals(temp)) && i == l.size() - 1) {
                        DatiMock.getInstance().addUtente(l.size(), u);//creo l'utente
                        DatiMock.getInstance().creaValoreMappa(l.size());//creo la lista di libri per quell'utente
                        //ritorno alla pagina di login
                        Intent go_to_Login = new Intent(Registrazione.this, Login.class);
                        startActivity(go_to_Login);
                        finish();
                        break;
                    }
                }
            }
        }
    }

    //METODI DI CONTROLLO DELL'INSERIMENTO DEI DATI

    //controlla che il nome non sia vuoto
    private boolean checkNome(String n) {
        if (TextUtils.isEmpty(n)) {
            nome.setError("Please enter a name");
            return false;
        }
        return true;
    }

    //controlla che il cognome non sia vuoto
    private boolean checkCognome(String c) {
        if (TextUtils.isEmpty(c)) {
            cognome.setError("Please enter a surname");
            return false;
        }
        return true;
    }

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

    //controlla che la password ripetuta non sia vuota e in caso contrario sia tra i 6 e 10 caratteri
    private boolean checkConfPassword(String password) {

        if (TextUtils.isEmpty(password)) {
            repetpass.setError("Please enter a password");
            return false;
        } else if (password.length() < 6 || password.length() > 10) {
            repetpass.setError("Password should be between 6 to 10 characters");
            return false;
        }
        return true;
    }

    //controlla che le due password inserite coincidano
    private boolean checkConfirmPassword(String pw, String conf_pw) {

        if (!pw.equals(conf_pw)) {
            repetpass.setError("Error! Password doesn't match");
            return false;
        }
        return true;
    }
}
