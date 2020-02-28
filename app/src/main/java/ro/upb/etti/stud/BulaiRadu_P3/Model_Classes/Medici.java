package ro.upb.etti.stud.BulaiRadu_P3.Model_Classes;

public class Medici {
    // Class variables/properties
    public static final String TABLE_NAME_Medici = "medici";

    public static final String COLUMN_MEDICI_Id = "IdMedic";
    public static final String COLUMN_MEDICI_Nume = "NumeMedic";
    public static final String COLUMN_MEDICI_Prenume = "PrenumeMedic";
    public static final String COLUMN_MEDICI_Specializare = "Specializare";

    private int id;
    private String nume, prenume, specializare;

    // SQL Create Table String:
    public static final String CREATE_TABLE_Medici =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Medici + "("
            + COLUMN_MEDICI_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MEDICI_Nume + " TEXT,"
            + COLUMN_MEDICI_Prenume + " TEXT,"
            + COLUMN_MEDICI_Specializare + " TEXT"
            + ")";

    // Class methods
    public Medici() {
    }

    public Medici(int id, String nume, String prenume, String specializare) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.specializare = specializare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }
}
