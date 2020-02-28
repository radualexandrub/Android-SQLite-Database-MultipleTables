package ro.upb.etti.stud.BulaiRadu_P3.Model_Classes;

public class Sectii {
    public static final String TABLE_NAME_Sectii = "sectii";

    public static final String COLUMN_SECTII_IdSectie = "IdSectie";
    public static final String COLUMN_SECTII_IdPacient = "IdPacient";
    public static final String COLUMN_SECTII_IdMedic = "IdMedic";
    public static final String COLUMN_SECTII_Nume = "NumeSectie";
    public static final String COLUMN_SECTII_Buget = "Buget";

    private int idSectie, idMedic, idPacient;
    private String nume;
    private Float buget;

    private String NumeMedic, PrenumeMedic, NumePacient, PrenumePacient;

    // SQL Create table String:
    public static final String CREATE_TABLE_Sectii =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Sectii + "("
            + COLUMN_SECTII_IdSectie + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SECTII_IdPacient + " INTEGER,"
            + COLUMN_SECTII_IdMedic + " INTEGER,"
            + COLUMN_SECTII_Nume + " TEXT,"
            + COLUMN_SECTII_Buget + " REAL,"
            + "FOREIGN KEY(IdPacient) REFERENCES pacienti(IdPacient) ON DELETE CASCADE, "
            + "FOREIGN KEY(IdMedic) REFERENCES medici(IdMedic) ON DELETE CASCADE "
            + ")";

    // Class Methods
    public Sectii() {
    }

    public Sectii(int idSectie, int idMedic, int idPacient, String nume, Float buget) {
        this.idSectie = idSectie;
        this.idMedic = idMedic;
        this.idPacient = idPacient;
        this.nume = nume;
        this.buget = buget;
    }

    public int getIdSectie() {
        return idSectie;
    }

    public void setIdSectie(int idSectie) {
        this.idSectie = idSectie;
    }

    public int getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(int idMedic) {
        this.idMedic = idMedic;
    }

    public int getIdPacient() {
        return idPacient;
    }

    public void setIdPacient(int idPacient) {
        this.idPacient = idPacient;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Float getBuget() {
        return buget;
    }

    public void setBuget(Float buget) {
        this.buget = buget;
    }








    public String getNumeMedic() {
        return NumeMedic;
    }

    public String getPrenumeMedic() {
        return PrenumeMedic;
    }

    public String getNumePacient() {
        return NumePacient;
    }

    public String getPrenumePacient() {
        return PrenumePacient;
    }

    public void setNumeMedic(String numeMedic) {
        NumeMedic = numeMedic;
    }

    public void setPrenumeMedic(String prenumeMedic) {
        PrenumeMedic = prenumeMedic;
    }

    public void setNumePacient(String numePacient) {
        NumePacient = numePacient;
    }

    public void setPrenumePacient(String prenumePacient) {
        PrenumePacient = prenumePacient;
    }
}
