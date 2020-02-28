package ro.upb.etti.stud.BulaiRadu_P3.Model_Classes;

public class Medicamente {
    // Class variables/properties
    public static final String TABLE_NAME_Medicamente = "medicamente";

    public static final String COLUMN_MEDICAMENTE_Id = "IdMedicament";
    public static final String COLUMN_MEDICAMENTE_Denumire = "Denumire";

    private int id;
    private String denumire;

    // SQL Create Table String:
    public static final String CREATE_TABLE_Medicamente =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Medicamente + "("
                    + COLUMN_MEDICAMENTE_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_MEDICAMENTE_Denumire + " TEXT"
                    + ")";

    // Class methods
    public Medicamente() {
    }

    public Medicamente(int id, String denumire) {
        this.id = id;
        this.denumire = denumire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
}
