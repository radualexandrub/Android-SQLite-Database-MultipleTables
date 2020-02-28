package ro.upb.etti.stud.BulaiRadu_P3.Model_Classes;

public class Pacienti {
    public static final String TABLE_NAME_Pacienti = "pacienti";

    public static final String COLUMN_PACIENTI_Id = "IdPacient";
    public static final String COLUMN_PACIENTI_CNP = "CNP_Pacient";
    public static final String COLUMN_PACIENTI_Nume = "NumePacient";
    public static final String COLUMN_PACIENTI_Prenume = "PrenumePacient";
    public static final String COLUMN_PACIENTI_Adresa = "Adresa";
    public static final String COLUMN_PACIENTI_Asigurare = "Asigurare";

    private int id;
    private String cnp, nume, prenume, adresa, asigurare;

    // SQL Create Table String:
    public static final String CREATE_TABLE_Pacienti =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Pacienti + "("
                    + COLUMN_PACIENTI_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PACIENTI_CNP + " TEXT,"
                    + COLUMN_PACIENTI_Nume + " TEXT,"
                    + COLUMN_PACIENTI_Prenume + " TEXT,"
                    + COLUMN_PACIENTI_Adresa + " TEXT,"
                    + COLUMN_PACIENTI_Asigurare + " TEXT"
                    + ")";

    // Class methods
    public Pacienti() {
    }

    public Pacienti(int id, String cnp, String nume, String prenume, String adresa, String asigurare) {
        this.id = id;
        this.cnp = cnp;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.asigurare = asigurare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getAsigurare() {
        return asigurare;
    }

    public void setAsigurare(String asigurare) {
        this.asigurare = asigurare;
    }
}
