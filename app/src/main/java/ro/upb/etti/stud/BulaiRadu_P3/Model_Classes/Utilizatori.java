package ro.upb.etti.stud.BulaiRadu_P3.Model_Classes;

public class Utilizatori {
    public static final String TABLE_NAME_Utilizatori = "utilizatori";
    public static final String COLUMN_UTILIZATORI_IdUtilizator = "IdUtilizator";
    public static final String COLUMN_UTILIZATORI_Username = "Username";
    public static final String COLUMN_UTILIZATORI_Password = "Password";
    public static final String COLUMN_UTILIZATORI_TipUtilizator = "TipUtilizator";

    public static final String CREATE_TABLE_Utilizatori =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Utilizatori + "("
                    + COLUMN_UTILIZATORI_IdUtilizator + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_UTILIZATORI_Username + " TEXT, "
                    + COLUMN_UTILIZATORI_Password + " TEXT, "
                    + COLUMN_UTILIZATORI_TipUtilizator + " TEXT"
                    + ")";
}
