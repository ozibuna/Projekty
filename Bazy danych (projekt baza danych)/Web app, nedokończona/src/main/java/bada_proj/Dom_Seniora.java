package bada_proj;

public class Dom_Seniora {

    private int Id_Domu_Seniora;
    private String Nazwa;
    private String Data_Zalozenia;
    private int Liczba_Pokoi;
    private int Max_Liczba_Mieszkancow;
    private String Opis_Placowki;
    private char Czynny_Calodobowo;
    private float Srednia_Cena_Mieszkania;


    public int getId_Domu_Seniora() {
        return Id_Domu_Seniora;
    }

    public void setId_Domu_Seniora(int id_Domu_Seniora) {
        Id_Domu_Seniora = id_Domu_Seniora;
    }

    public String getNazwa() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        Nazwa = nazwa;
    }

    public String getData_Zalozenia() {
        return Data_Zalozenia;
    }

    public void setData_Zalozenia(String data_Zalozenia) {
        Data_Zalozenia = data_Zalozenia;
    }

    public int getLiczba_Pokoi() {
        return Liczba_Pokoi;
    }

    public void setLiczba_Pokoi(int liczba_Pokoi) {
        Liczba_Pokoi = liczba_Pokoi;
    }

    public int getMax_Liczba_Mieszkancow() {
        return Max_Liczba_Mieszkancow;
    }

    public void setMax_Liczba_Mieszkancow(int max_Liczba_Mieszkancow) {
        Max_Liczba_Mieszkancow = max_Liczba_Mieszkancow;
    }

    public String getOpis_Placowki() {
        return Opis_Placowki;
    }

    public void setOpis_Placowki(String opis_Placowki) {
        Opis_Placowki = opis_Placowki;
    }

    public char getCzynny_Calodobowo() {
        return Czynny_Calodobowo;
    }

    public void setCzynny_Calodobowo(char czynny_Calodobowo) {
        Czynny_Calodobowo = czynny_Calodobowo;
    }

    public float getSrednia_Cena_Mieszkania() {
        return Srednia_Cena_Mieszkania;
    }

    public void setSrednia_Cena_Mieszkania(float srednia_Cena_Mieszkania) {
        Srednia_Cena_Mieszkania = srednia_Cena_Mieszkania;
    }

    @Override
    public String toString() {
        return "Domy_Seniora{" +
                "Id_Domu_Seniora=" + Id_Domu_Seniora +
                ", Nazwa='" + Nazwa + '\'' +
                ", Data_Zalozenia='" + Data_Zalozenia + '\'' +
                ", Liczba_pokoi=" + Liczba_Pokoi +
                ", Max_Liczba_Mieszkancow=" + Max_Liczba_Mieszkancow +
                ", Opis_Placowki='" + Opis_Placowki + '\'' +
                ", Czynny_Calodobowo=" + Czynny_Calodobowo +
                ", Srednia_cena_Mieszkania=" + Srednia_Cena_Mieszkania +
                '}';
    }

    public Dom_Seniora(){
    super();
}
    public Dom_Seniora(int id_Domu_Seniora, String nazwa, String data_Zalozenia, int liczba_Pokoi, int max_Liczba_Mieszkancow, String opis_Placowki, char czynny_Calodobowo, float srednia_Cena_Mieszkania) {
        this.Id_Domu_Seniora = id_Domu_Seniora;
        Nazwa = nazwa;
        Data_Zalozenia = data_Zalozenia;
        Liczba_Pokoi = liczba_Pokoi;
        Max_Liczba_Mieszkancow = max_Liczba_Mieszkancow;
        Opis_Placowki = opis_Placowki;
        Czynny_Calodobowo = czynny_Calodobowo;
        Srednia_Cena_Mieszkania = srednia_Cena_Mieszkania;
    }
}
