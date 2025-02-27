package bada_proj;

public class Krewny {
	private int Id_Krewnego;
	private String Imie;
	private String Nazwisko;
	private String Relacja;
	private char Zgoda_Na_Kontakt;
	private String Telefon;
	private String Email;
	
	public Krewny() {
		super();
	}
	
	public Krewny(int id_Krewnego, String imie, String nazwisko, String relacja, char zgoda_Na_Kontakt, String telefon,
			String email) {
		super();
		Id_Krewnego = id_Krewnego;
		Imie = imie;
		Nazwisko = nazwisko;
		Relacja = relacja;
		Zgoda_Na_Kontakt = zgoda_Na_Kontakt;
		Telefon = telefon;
		Email = email;
	}

	public int getId_Krewnego() {
		return Id_Krewnego;
	}

	public void setId_Krewnego(int id_Krewnego) {
		Id_Krewnego = id_Krewnego;
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

	public String getRelacja() {
		return Relacja;
	}

	public void setRelacja(String relacja) {
		Relacja = relacja;
	}

	public char getZgoda_Na_Kontakt() {
		return Zgoda_Na_Kontakt;
	}

	public void setZgoda_Na_Kontakt(char zgoda_Na_Kontakt) {
		Zgoda_Na_Kontakt = zgoda_Na_Kontakt;
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
		return "Krewni [Id_Krewnego=" + Id_Krewnego + ", Imie=" + Imie + ", Nazwisko=" + Nazwisko + ", Relacja="
				+ Relacja + ", Zgoda_Na_Kontakt=" + Zgoda_Na_Kontakt + ", Telefon=" + Telefon + ", Email=" + Email
				+ "]";
	}

	
	
}
