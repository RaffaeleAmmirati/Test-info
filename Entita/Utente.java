package app.projectwork.simulazione.Entita;

public class Utente {
    private int id;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private static int counter = 0;

    public Utente() {
    }

    public Utente(int id, String email, String nome, String cognome,String password) {
        counter++;
        this.id = counter;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Utente(String email, String nome, String cognome,String password) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String toString() {
        return  "id='"+ id + '\'' +", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'';
    }
}

