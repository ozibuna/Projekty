package bada_proj;

public class Adres {
	private int Id_Adresu;
	private String Miasto;
	private String Ulica;
	private String Nr_Lokalu;
	private String Nr_Budynku;
	
	public Adres() {
		super();
	}

	public Adres(int id_Adresu, String miasto, String ulica, String nr_Lokalu, String nr_Budynku) {
		super();
		Id_Adresu = id_Adresu;
		Miasto = miasto;
		Ulica = ulica;
		Nr_Lokalu = nr_Lokalu;
		Nr_Budynku = nr_Budynku;
	}

	public int getId_Adresu() {
		return Id_Adresu;
	}

	public void setId_Adresu(int id_Adresu) {
		Id_Adresu = id_Adresu;
	}

	public String getMiasto() {
		return Miasto;
	}

	public void setMiasto(String miasto) {
		Miasto = miasto;
	}

	public String getUlica() {
		return Ulica;
	}

	public void setUlica(String ulica) {
		Ulica = ulica;
	}

	public String getNr_Lokalu() {
		return Nr_Lokalu;
	}

	public void setNr_Lokalu(String nr_Lokalu) {
		Nr_Lokalu = nr_Lokalu;
	}

	public String getNr_Budynku() {
		return Nr_Budynku;
	}

	public void setNr_Budynku(String nr_Budynku) {
		Nr_Budynku = nr_Budynku;
	}

	@Override
	public String toString() {
		return "Adresy [Id_Adresu=" + Id_Adresu + ", Miasto=" + Miasto + ", Ulica=" + Ulica + ", Nr_Lokalu="
				+ Nr_Lokalu + ", Nr_Budynku=" + Nr_Budynku + "]";
	}
	
	
}
