package app.projectwork.simulazione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.projectwork.simulazione.Entita.Libro;
import app.projectwork.simulazione.Entita.Utente;

public class DatiMock {
    private ArrayList<Libro> libri;
    private ArrayList<Utente> utenti;
    private Map<Integer, List<Libro>> mappaLibri;

    private static DatiMock instance;

    private DatiMock() {
    }

    public static DatiMock getInstance() {
        if (instance == null) {
            instance = new DatiMock();
        }
        return instance;
    }

    public void init() {
        libri = new ArrayList<>();
        utenti = new ArrayList<>();
        mappaLibri = new HashMap<>();

        // lista libri
        libri.add(new Libro("Il signore degli anelli", "J. R. Tolkien", /*0,*/  "casa editrice uno", false));
        libri.add(new Libro("I promessi spsosi", "A. Manzoni", /*0,*/  "casa editrice due", true));
        libri.add(new Libro("Corso completo di russo", "V. K.", /*0,*/  "casa editrice tre", false));
        libri.add(new Libro("Design Patterns", "K. K.", /*0,*/  "casa editrice uno", false));
        libri.add(new Libro("Neuromante", "W. Gibson", /*0,*/  "casa editrice due", true));
        libri.add(new Libro("Ubik", "P. K. Dick", /*0,*/  "casa editrice tre", false));

        // lista utente
        utenti.add(new Utente(1, "pippo@pippo.it", "pippo", "goofy", "qwerty"));
        utenti.add(new Utente(2, "paperino@paperino.it", "donald", "duck", "qwerty"));

        /*Ogni utente avrà una copia della lista di libri su cui potrà selezionare i libri che a letto*/

        //creo una copia della lista di default per gli utenti già presenti nell'app
        ArrayList<Libro> libriU1 = new ArrayList<>();
        libriU1.addAll(libri);

        /*aggiungo la copia dei libri per il primo utente in una mappa che associa
        l'id utente con la sua corrispettiva copia*/
        mappaLibri.put(1, libriU1);

        //agli utenti successivi al primo aggiungo un libro in più per mostrare che le liste di libri
        //tra il primo e secondo utente sono differenti
        libri.add(new Libro("Ubik", "P. K. Dick", /*0,*/  "casa editrice tre", false));

        //creo una copia della lista di default per gli utenti già presenti nell'app
        ArrayList<Libro> libriU2 = new ArrayList<>();
        libriU2.addAll(libri);

        /*aggiungo la copia dei libri per il secondo utente nella mappa che associa
        l'id utente con la sua corrispettiva copia*/
        mappaLibri.put(2, libriU2);

    }

    //metodo che verrà chiamato alla creazione dei successivi utenti e che fa una nuova copia della lista di libri
    //e la aggiuge alla mappa
    public void creaValoreMappa(int i) {

        //creo una nuova copia della lista di libri
        ArrayList<Libro> libriUn = new ArrayList<>();
        libriUn.addAll(libri);

        //aggiungo alla mappa la nuova copia e il nuovo id dell'utente appena registrato
        mappaLibri.put(i, libriUn);
    }

    //metodo che aggiorna la lista dei libri associata all'utente in base alle sue selezioni
    public void aggiornaMappa(int key, int idBook, Libro l) {
        mappaLibri.get(key).set(idBook, l);
    }

    //aggiunge un utente alla lista di utenti già registrati
    public void addUtente(Utente utente) {
        utenti.add(utente);
    }

    //overloading del metodo precedente
    public void addUtente(int i, Utente u) {

        String m = u.getEmail();
        String n = u.getNome();
        String c = u.getCognome();
        String p = u.getPassword();

        utenti.add(new Utente(i, m, n, c, p));
    }

    //metodo che restituisce la lista di libri
    public ArrayList<Libro> getLibri() {
        return libri;
    }

    //metodo che restituisce la lista di utenti gia registrati
    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    //metodo che restituisce la mappa dei libri degli utenti
    public Map<Integer, List<Libro>> getMappaLibri() {
        return mappaLibri;
    }
}





