package ro.upb.etti.stud.BulaiRadu_P3.Model_Classes;

public class Consultatii {
    public static final String TABLE_NAME_Consultatii = "consultatii";

    public static final String COLUMN_CONSULTATII_IdConsultatie = "IdConsultatie";
    public static final String COLUMN_CONSULTATII_IdPacient = "IdPacient";
    public static final String COLUMN_CONSULTATII_IdMedicament = "IdMedicament";
    public static final String COLUMN_CONSULTATII_DataConsultatie = "DataConsultatie";
    public static final String COLUMN_CONSULTATII_Diagnostic = "Diagnostic";
    public static final String COLUMN_CONSULTATII_DozaMedicament = "DozaMedicament";

    private int IdConsultatie, IdPacient, IdMedicament;
    private String dataconsultatie, diagnostic;
    private Float dozamedicament;


    private String DenumireMedicament, NumePacient, PrenumePacient, NumeMedic, PrenumeMedic;

    // SQL Create table String:
    public static final String CREATE_TABLE_Consultatii =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Consultatii + "("
                    + COLUMN_CONSULTATII_IdConsultatie + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CONSULTATII_IdPacient + " INTEGER,"
                    + COLUMN_CONSULTATII_IdMedicament + " INTEGER,"
                    + COLUMN_CONSULTATII_DataConsultatie + " TEXT,"
                    + COLUMN_CONSULTATII_Diagnostic + " TEXT,"
                    + COLUMN_CONSULTATII_DozaMedicament + " REAL,"
                    + "FOREIGN KEY(IdPacient) REFERENCES pacienti(IdPacient) ON DELETE CASCADE, "
                    + "FOREIGN KEY(IdMedicament) REFERENCES medicamente(IdMedicament) ON DELETE CASCADE "
                    + ")";

    // Class Methods
    public Consultatii() {
    }

    public Consultatii(int IdConsultatie, int IdPacient, int IdMedicament, String dataconsultatie, String diagnostic, Float dozamedicament) {
        this.IdConsultatie = IdConsultatie;
        this.IdPacient = IdPacient;
        this.IdMedicament = IdMedicament;
        this.dataconsultatie = dataconsultatie;
        this.diagnostic = diagnostic;
        this.dozamedicament = dozamedicament;
    }

    public int getIdConsultatie() {
        return IdConsultatie;
    }

    public void setIdConsultatie(int idConsultatie) {
        IdConsultatie = idConsultatie;
    }

    public int getIdPacient() {
        return IdPacient;
    }

    public void setIdPacient(int idPacient) {
        IdPacient = idPacient;
    }

    public int getIdMedicament() {
        return IdMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        IdMedicament = idMedicament;
    }

    public String getDataconsultatie() {
        return dataconsultatie;
    }

    public void setDataconsultatie(String dataconsultatie) {
        this.dataconsultatie = dataconsultatie;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Float getDozamedicament() {
        return dozamedicament;
    }

    public void setDozamedicament(Float dozamedicament) {
        this.dozamedicament = dozamedicament;
    }



    // Atribute apartinand altor clase (medicamente, pacienti, medici)



    public String getDenumireMedicament() {
        return DenumireMedicament;
    }

    public void setDenumireMedicament(String denumireMedicament) {
        DenumireMedicament = denumireMedicament;
    }

    public String getNumePacient() {
        return NumePacient;
    }

    public void setNumePacient(String numePacient) {
        NumePacient = numePacient;
    }

    public String getPrenumePacient() {
        return PrenumePacient;
    }

    public void setPrenumePacient(String prenumePacient) {
        PrenumePacient = prenumePacient;
    }

    public String getNumeMedic() {
        return NumeMedic;
    }

    public void setNumeMedic(String numeMedic) {
        NumeMedic = numeMedic;
    }

    public String getPrenumeMedic() {
        return PrenumeMedic;
    }

    public void setPrenumeMedic(String prenumeMedic) {
        PrenumeMedic = prenumeMedic;
    }
}
