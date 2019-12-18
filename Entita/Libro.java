package app.projectwork.simulazione.Entita;

public class Libro {
    private static int counter = 0;

    private int id;
    private String titolo;
    private String autore;
//    private int utente_id;
    private String casaEditrice;
    private boolean isRead = false;

    public Libro() {
    }

    public Libro(String titolo, String autore, /*int utente_id,*/ String casaEditrice, boolean letto) {
        counter++;
        this.id = counter;
        this.titolo = titolo;
        this.autore = autore;
//        this.utente_id = utente_id;
        this.casaEditrice = casaEditrice;
        this.isRead = letto;
    }

    public Libro(int id, String titolo, String autore, /*int utente_id,*/ String casaEditrice, boolean letto) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
//        this.utente_id = utente_id;
        this.casaEditrice = casaEditrice;
        this.isRead = letto;
    }

    public int getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

//    public int getUtente_id() {
//        return utente_id;
//    }
//
//    public void setUtente_id(int utente_id) {
//        this.utente_id = utente_id;
//    }

    public String getCasaEditrice() {
        return casaEditrice;
    }

    public void setCasaEditrice(String casaEditrice) {
        this.casaEditrice = casaEditrice;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                //", utente_id=" + utente_id +
                ", casaEditrice='" + casaEditrice + '\'' +
                ", isRead=" + isRead +
                '}';
    }

}
