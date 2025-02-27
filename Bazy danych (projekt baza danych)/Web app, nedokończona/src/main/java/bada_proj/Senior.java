package bada_proj;

public class Senior {
    private int Id_Seniora;
    private String Imie;
    private String Nazwisko;
    private String Data_Urodzenia;
    private char PESEL;
    private char Plec;
    private String Data_Przyjecia;
    private String Stan_Cywilny;
    private String Data_Wypisu;
    private String Telefon;
    private String Email;

    public int getId_Seniora() {
        return Id_Seniora;
    }

    public void setId_Seniora(int id_Seniora) {
        Id_Seniora = id_Seniora;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public String getData_Urodzenia() {
        return Data_Urodzenia;
    }

    public void setData_Urodzenia(String data_Urodzenia) {
        Data_Urodzenia = data_Urodzenia;
    }

    public char getPESEL() {
        return PESEL;
    }

    public void setPESEL(char PESEL) {
        this.PESEL = PESEL;
    }

    public char getPlec() {
        return Plec;
    }

    public void setPlec(char plec) {
        Plec = plec;
    }

    public String getData_Przyjecia() {
        return Data_Przyjecia;
    }

    public void setData_Przyjecia(String data_Przyjecia) {
        Data_Przyjecia = data_Przyjecia;
    }

    public String getStan_Cywilny() {
        return Stan_Cywilny;
    }

    public void setStan_Cywilny(String stan_Cywilny) {
        Stan_Cywilny = stan_Cywilny;
    }

    public String getData_Wypisu() {
        return Data_Wypisu;
    }

    public void setData_Wypisu(String data_Wypisu) {
        Data_Wypisu = data_Wypisu;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Seniorzy{" +
                "Id_Seniora=" + Id_Seniora +
                ", Imie='" + Imie + '\'' +
                ", Nazwisko='" + Nazwisko + '\'' +
                ", Data_Urodzenia='" + Data_Urodzenia + '\'' +
                ", PESEL=" + PESEL +
                ", Plec=" + Plec +
                ", Data_Przyjecia='" + Data_Przyjecia + '\'' +
                ", Stan_Cywilny='" + Stan_Cywilny + '\'' +
                ", Data_Wypisu='" + Data_Wypisu + '\'' +
                ", Telefon='" + Telefon + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }

    public Senior(){
    super();
}
    public Senior(int id_Seniora, String imie, String nazwisko, String data_Urodzenia, char PESEL, char plec, String data_Przyjecia, String stan_Cywilny, String data_Wypisu, String telefon, String email) {
        Id_Seniora = id_Seniora;
        Imie = imie;
        Nazwisko = nazwisko;
        Data_Urodzenia = data_Urodzenia;
        this.PESEL = PESEL;
        Plec = plec;
        Data_Przyjecia = data_Przyjecia;
        Stan_Cywilny = stan_Cywilny;
        Data_Wypisu = data_Wypisu;
        Telefon = telefon;
        Email = email;
    }

}
