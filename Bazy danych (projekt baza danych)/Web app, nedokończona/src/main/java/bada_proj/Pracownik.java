package bada_proj;

public class Pracownik {
	private int Id_Pracownika;
	private String Imie;
	private String Nazwisko;
	private String Data_Zatrudnienia;
	private String Data_Urodzenia;
	
	public Pracownik() {
		super();
	}

	public Pracownik(int id_Pracownika, String imie, String nazwisko, String data_Zatrudnienia,
			String data_Urodzenia) {
		super();
		Id_Pracownika = id_Pracownika;
		Imie = imie;
		Nazwisko = nazwisko;
		Data_Zatrudnienia = data_Zatrudnienia;
		Data_Urodzenia = data_Urodzenia;
	}

	public int getId_Pracownika() {
		return Id_Pracownika;
	}

	public void setId_Pracownika(int id_Pracownika) {
		Id_Pracownika = id_Pracownika;
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

	public String getData_Zatrudnienia() {
		return Data_Zatrudnienia;
	}

	public void setData_Zatrudnienia(String data_Zatrudnienia) {
		Data_Zatrudnienia = data_Zatrudnienia;
	}

	public String getData_Urodzenia() {
		return Data_Urodzenia;
	}

	public void setData_Urodzenia(String data_Urodzenia) {
		Data_Urodzenia = data_Urodzenia;
	}

	@Override
	public String toString() {
		return "Pracownicy [Id_Pracownika=" + Id_Pracownika + ", Imie=" + Imie + ", Nazwisko=" + Nazwisko
				+ ", Data_Zatrudnienia=" + Data_Zatrudnienia + ", Data_Urodzenia=" + Data_Urodzenia + "]";
	}

	
}
