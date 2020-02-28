package ro.upb.etti.stud.BulaiRadu_P3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Consultatii;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Medicamente;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Medici;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Pacienti;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Sectii;
import ro.upb.etti.stud.BulaiRadu_P3.Model_Classes.Utilizatori;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "spital_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // SQL Query: CREATE TABLES
        db.execSQL(Medici.CREATE_TABLE_Medici);
        db.execSQL(Pacienti.CREATE_TABLE_Pacienti);
        db.execSQL(Medicamente.CREATE_TABLE_Medicamente);
        db.execSQL(Sectii.CREATE_TABLE_Sectii);
        db.execSQL(Consultatii.CREATE_TABLE_Consultatii);
        db.execSQL(Utilizatori.CREATE_TABLE_Utilizatori);

        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + Medici.TABLE_NAME_Medici);
        db.execSQL("DROP TABLE IF EXISTS " + Pacienti.TABLE_NAME_Pacienti);
        db.execSQL("DROP TABLE IF EXISTS " + Medicamente.TABLE_NAME_Medicamente);
        db.execSQL("DROP TABLE IF EXISTS " + Sectii.TABLE_NAME_Sectii);
        db.execSQL("DROP TABLE IF EXISTS " + Consultatii.TABLE_NAME_Consultatii);
        db.execSQL("DROP TABLE IF EXISTS " + Utilizatori.TABLE_NAME_Utilizatori);

        db.execSQL("PRAGMA foreign_keys=ON");

        // Create tables again
        onCreate(db);
    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
        // De fiecare data cand ma conectez la baza de date, foreign_keys este setat OFF by default
    }

    /////////////////////////////////////////////
    // ~~~ CRUD METHODS for MEDICI ~~~
    /////////////////////////////////////////////

    // Insert:
    public long insertMedic(String nume,
                            String prenume,
                            String specializare) {
        SQLiteDatabase db = this.getWritableDatabase(); // get writable database as we want to write data

        ContentValues values = new ContentValues(); // used to define the column name and its data to be stored
        // `id` will be inserted automatically.
        values.put(Medici.COLUMN_MEDICI_Nume, nume);
        values.put(Medici.COLUMN_MEDICI_Prenume, prenume);
        values.put(Medici.COLUMN_MEDICI_Specializare, specializare);

        // insert row
        long id = db.insert(Medici.TABLE_NAME_Medici, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    // Read:
    public Medici getMedic(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // get readable database as we are not inserting anything

        Cursor cursor = db.query(Medici.TABLE_NAME_Medici,
                new String[]{Medici.COLUMN_MEDICI_Id,
                        Medici.COLUMN_MEDICI_Nume,
                        Medici.COLUMN_MEDICI_Prenume,
                        Medici.COLUMN_MEDICI_Specializare},
                Medici.COLUMN_MEDICI_Id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare Medici object
        Medici medici = new Medici(
                cursor.getInt(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Id)),
                cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Nume)),
                cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Prenume)),
                cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Specializare))
        );

        cursor.close();
        return medici;
    }

//    public String return_NumeMedic_from_id(int id) {
//        String NumeMedic = "";
//        String PrenumeMedic = "";
//
//        String selectQuery = "";
//        selectQuery = "SELECT " + Medici.COLUMN_MEDICI_Id + " FROM "
//                + Medici.TABLE_NAME_Medici + " WHERE "
//                + Medici.COLUMN_MEDICI_Id + " =" + String.valueOf(id)
//                + ";";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                NumeMedic = cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Nume));
//                PrenumeMedic = cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Prenume));
//            } while (cursor.moveToNext());
//        }
//
//        return NumeMedic + " " + PrenumeMedic;
//    }

    public List<Integer> getListaIds_Medici() {
        List<Integer> lista_ids = new ArrayList<Integer>();

        String selectQuery = "";
        selectQuery = "SELECT * FROM " + Medici.TABLE_NAME_Medici;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Integer int_id = cursor.getInt(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Id));
                lista_ids.add(int_id);

            } while (cursor.moveToNext());
        }

        db.close();

        return lista_ids;
    }


    public List<Medici> getAllMedici(String tipSortare) {
        List<Medici> medici = new ArrayList<>();

        // Select All Query
        String selectQuery = "";
        if (tipSortare.equals("nume")) {
            selectQuery = "SELECT  * FROM " + Medici.TABLE_NAME_Medici + " ORDER BY " +
                    Medici.COLUMN_MEDICI_Nume + " ASC";
        } else if (tipSortare.equals("specializare")) {
            selectQuery = "SELECT  * FROM " + Medici.TABLE_NAME_Medici + " ORDER BY " +
                    Medici.COLUMN_MEDICI_Specializare + " ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medici medic = new Medici();
                medic.setId(cursor.getInt(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Id)));
                medic.setNume(cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Nume)));
                medic.setPrenume(cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Prenume)));
                medic.setSpecializare(cursor.getString(cursor.getColumnIndex(Medici.COLUMN_MEDICI_Specializare)));

                medici.add(medic);
            } while (cursor.moveToNext());
        }

        db.close();
        return medici; // return medici list
    }

    public int getMediciCount() {
        String countQuery = "SELECT  * FROM " + Medici.TABLE_NAME_Medici;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Update:
    public int updateMedici(Medici medic) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Medici.COLUMN_MEDICI_Nume, medic.getNume());
        values.put(Medici.COLUMN_MEDICI_Prenume, medic.getPrenume());
        values.put(Medici.COLUMN_MEDICI_Specializare, medic.getSpecializare());

        // updating row
        return db.update(Medici.TABLE_NAME_Medici, values, Medici.COLUMN_MEDICI_Id + " = ?",
                new String[]{String.valueOf(medic.getId())});
    }

    // Delete:
    public void deleteMedici(Medici medic) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Medici.TABLE_NAME_Medici, Medici.COLUMN_MEDICI_Id + " = ?",
                new String[]{String.valueOf(medic.getId())});
        db.close();
    }

    /////////////////////////////////////////////
    // ~~~ CRUD METHODS for PACIENTI ~~~
    /////////////////////////////////////////////

    // Insert Pacient:
    public long insertPacient(String cnp,
                            String nume,
                            String prenume,
                            String adresa,
                            String asigurare) {
        SQLiteDatabase db = this.getWritableDatabase(); // get writable database as we want to write data

        ContentValues values = new ContentValues(); // used to define the column name and its data to be stored
        // `id` will be inserted automatically.
        values.put(Pacienti.COLUMN_PACIENTI_CNP, cnp);
        values.put(Pacienti.COLUMN_PACIENTI_Nume, nume);
        values.put(Pacienti.COLUMN_PACIENTI_Prenume, prenume);
        values.put(Pacienti.COLUMN_PACIENTI_Adresa, adresa);
        values.put(Pacienti.COLUMN_PACIENTI_Asigurare, asigurare);

        // insert row
        long id = db.insert(Pacienti.TABLE_NAME_Pacienti, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    // Read Pacienti:
    public Pacienti getPacient(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // get readable database as we are not inserting anything

        Cursor cursor = db.query(Pacienti.TABLE_NAME_Pacienti,
                new String[]{Pacienti.COLUMN_PACIENTI_Id,
                        Pacienti.COLUMN_PACIENTI_CNP,
                        Pacienti.COLUMN_PACIENTI_Nume,
                        Pacienti.COLUMN_PACIENTI_Prenume,
                        Pacienti.COLUMN_PACIENTI_Adresa,
                        Pacienti.COLUMN_PACIENTI_Asigurare},
                Pacienti.COLUMN_PACIENTI_Id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare Pacienti object
        Pacienti pacienti = new Pacienti(
                cursor.getInt(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Id)),
                cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_CNP)),
                cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Nume)),
                cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Prenume)),
                cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Adresa)),
                cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Asigurare))
        );

        cursor.close();
        return pacienti;
    }

    public List<Integer> getListaIds_Pacienti() {
        List<Integer> lista_ids = new ArrayList<Integer>();

        String selectQuery = "";
        selectQuery = "SELECT * FROM " + Pacienti.TABLE_NAME_Pacienti;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Integer int_id = cursor.getInt(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Id));
                lista_ids.add(int_id);

            } while (cursor.moveToNext());
        }

        db.close();

        return lista_ids;
    }

    public List<Pacienti> getAllPacienti(String tipSortare) {
        List<Pacienti> pacienti = new ArrayList<>();

        // Select All Query
        String selectQuery = "";
        if (tipSortare.equals("nume")) {
            selectQuery = "SELECT  * FROM " + Pacienti.TABLE_NAME_Pacienti + " ORDER BY " +
                    Pacienti.COLUMN_PACIENTI_Nume + " ASC";
        } else if (tipSortare.equals("asigurare")) {
            selectQuery = "SELECT  * FROM " + Pacienti.TABLE_NAME_Pacienti + " ORDER BY " +
                    Pacienti.COLUMN_PACIENTI_Asigurare + " ASC";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pacienti pacient = new Pacienti();
                pacient.setId(cursor.getInt(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Id)));
                pacient.setCnp(cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_CNP)));
                pacient.setNume(cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Nume)));
                pacient.setPrenume(cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Prenume)));
                pacient.setAdresa(cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Adresa)));
                pacient.setAsigurare(cursor.getString(cursor.getColumnIndex(Pacienti.COLUMN_PACIENTI_Asigurare)));

                pacienti.add(pacient);
            } while (cursor.moveToNext());
        }

        db.close();
        return pacienti; // return pacienti list
    }

    public int getPacientiCount() {
        String countQuery = "SELECT  * FROM " + Pacienti.TABLE_NAME_Pacienti;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Update Pacienti:
    public int updatePacienti(Pacienti pacient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Pacienti.COLUMN_PACIENTI_CNP, pacient.getCnp());
        values.put(Pacienti.COLUMN_PACIENTI_Nume, pacient.getNume());
        values.put(Pacienti.COLUMN_PACIENTI_Prenume, pacient.getPrenume());
        values.put(Pacienti.COLUMN_PACIENTI_Adresa, pacient.getAdresa());
        values.put(Pacienti.COLUMN_PACIENTI_Asigurare, pacient.getAsigurare());

        // updating row
        return db.update(Pacienti.TABLE_NAME_Pacienti, values, Pacienti.COLUMN_PACIENTI_Id + " = ?",
                new String[]{String.valueOf(pacient.getId())});
    }

    // Delete Pacienti:
    public void deletePacienti(Pacienti pacient) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Pacienti.TABLE_NAME_Pacienti, Pacienti.COLUMN_PACIENTI_Id + " = ?",
                new String[]{String.valueOf(pacient.getId())});
        db.close();
    }

    /////////////////////////////////////////////
    // ~~~ CRUD METHODS for Sectii ~~~
    /////////////////////////////////////////////

    // Insert Sectie:
    public long insertSectie(Integer idpacient,
                              Integer idmedic,
                              String nume,
                              Float buget) {
        SQLiteDatabase db = this.getWritableDatabase(); // get writable database as we want to write data

        ContentValues values = new ContentValues(); // used to define the column name and its data to be stored
        // `id` will be inserted automatically.
        values.put(Sectii.COLUMN_SECTII_IdPacient, idpacient);
        values.put(Sectii.COLUMN_SECTII_IdMedic, idmedic);
        values.put(Sectii.COLUMN_SECTII_Nume, nume);
        values.put(Sectii.COLUMN_SECTII_Buget, buget);

        // insert row
        long id = db.insert(Sectii.TABLE_NAME_Sectii, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    // Read obiect sectie:
    public Sectii getSectie(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // get readable database as we are not inserting anything

        Cursor cursor = db.query(Sectii.TABLE_NAME_Sectii,
                new String[]{Sectii.COLUMN_SECTII_IdSectie,
                        Sectii.COLUMN_SECTII_IdPacient,
                        Sectii.COLUMN_SECTII_IdMedic,
                        Sectii.COLUMN_SECTII_Nume,
                        Sectii.COLUMN_SECTII_Buget},
                Sectii.COLUMN_SECTII_IdSectie + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare Sectii object
        Sectii sectii = new Sectii(
                cursor.getInt(cursor.getColumnIndex(Sectii.COLUMN_SECTII_IdSectie)),
                cursor.getInt(cursor.getColumnIndex(Sectii.COLUMN_SECTII_IdPacient)),
                cursor.getInt(cursor.getColumnIndex(Sectii.COLUMN_SECTII_IdMedic)),
                cursor.getString(cursor.getColumnIndex(Sectii.COLUMN_SECTII_Nume)),
                cursor.getFloat(cursor.getColumnIndex(Sectii.COLUMN_SECTII_Buget))
        );

        cursor.close();
        return sectii;
    }

    public List<Sectii> getAllSectii() {
        List<Sectii> sectii = new ArrayList<>();

        // Select All Query
        String selectQuery = "";
        selectQuery = "SELECT * FROM " + Sectii.TABLE_NAME_Sectii
                + " INNER JOIN " + Medici.TABLE_NAME_Medici
                + " ON " + "Medici.IdMedic = Sectii.IdMedic"
                + " INNER JOIN " + Pacienti.TABLE_NAME_Pacienti
                + " ON " + "Pacienti.IdPacient = Sectii.IdPacient";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Sectii sectie = new Sectii();
                Medici medic = new Medici();
                Pacienti pacient = new Pacienti();
                sectie.setIdSectie(cursor.getInt(cursor.getColumnIndex(sectie.COLUMN_SECTII_IdSectie)));
                sectie.setIdPacient(cursor.getInt(cursor.getColumnIndex(sectie.COLUMN_SECTII_IdPacient)));
                sectie.setIdMedic(cursor.getInt(cursor.getColumnIndex(sectie.COLUMN_SECTII_IdMedic)));
                sectie.setNume(cursor.getString(cursor.getColumnIndex(sectie.COLUMN_SECTII_Nume)));
                sectie.setBuget(cursor.getFloat(cursor.getColumnIndex(sectie.COLUMN_SECTII_Buget)));
                sectie.setNumeMedic(cursor.getString(cursor.getColumnIndex(medic.COLUMN_MEDICI_Nume)));
                sectie.setPrenumeMedic(cursor.getString(cursor.getColumnIndex(medic.COLUMN_MEDICI_Prenume)));
                sectie.setNumePacient(cursor.getString(cursor.getColumnIndex(pacient.COLUMN_PACIENTI_Nume)));
                sectie.setPrenumePacient(cursor.getString(cursor.getColumnIndex(pacient.COLUMN_PACIENTI_Prenume)));

                sectii.add(sectie);
            } while (cursor.moveToNext());
        }

        db.close();
        return sectii; // return sectii list
    }


    public int getSectiiCount() {
        String countQuery = "SELECT  * FROM " + Sectii.TABLE_NAME_Sectii;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Update Sectii:
    public int updateSectii(Sectii sectie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Sectii.COLUMN_SECTII_IdPacient, sectie.getIdPacient());
        values.put(Sectii.COLUMN_SECTII_IdMedic, sectie.getIdMedic());
        values.put(Sectii.COLUMN_SECTII_Nume, sectie.getNume());
        values.put(Sectii.COLUMN_SECTII_Buget, sectie.getBuget());

        // updating row
        return db.update(Sectii.TABLE_NAME_Sectii, values, Sectii.COLUMN_SECTII_IdSectie + " = ?",
                new String[]{String.valueOf(sectie.getIdSectie())});
    }

    // Delete Sectii:
    public void deleteSectii(Sectii sectie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Sectii.TABLE_NAME_Sectii, Sectii.COLUMN_SECTII_IdSectie + " = ?",
                new String[]{String.valueOf(sectie.getIdSectie())});
        db.close();
    }


    /////////////////////////////////////////////
    // ~~~ CRUD METHODS for MEDICAMENTE ~~~
    /////////////////////////////////////////////

    // Insert:
    public long insertMedicament(String denumire) {
        SQLiteDatabase db = this.getWritableDatabase(); // get writable database as we want to write data

        ContentValues values = new ContentValues(); // used to define the column name and its data to be stored
        // `id` will be inserted automatically.
        values.put(Medicamente.COLUMN_MEDICAMENTE_Denumire, denumire);
        // insert row
        long id = db.insert(Medicamente.TABLE_NAME_Medicamente, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    // Read:
    public Medicamente getMedicament(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // get readable database as we are not inserting anything

        Cursor cursor = db.query(Medicamente.TABLE_NAME_Medicamente,
                new String[]{Medicamente.COLUMN_MEDICAMENTE_Id,
                        Medicamente.COLUMN_MEDICAMENTE_Denumire},
                Medicamente.COLUMN_MEDICAMENTE_Id + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare Medici object
        Medicamente medicamente = new Medicamente(
                cursor.getInt(cursor.getColumnIndex(Medicamente.COLUMN_MEDICAMENTE_Id)),
                cursor.getString(cursor.getColumnIndex(Medicamente.COLUMN_MEDICAMENTE_Denumire))
        );

        cursor.close();
        return medicamente;
    }

    public List<Medicamente> getAllMedicamente() {
        List<Medicamente> medicamente = new ArrayList<>();

        // Select All Query
        String selectQuery = "";
        selectQuery = "SELECT  * FROM " + Medicamente.TABLE_NAME_Medicamente + " ORDER BY " +
                Medicamente.COLUMN_MEDICAMENTE_Denumire + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Medicamente medicament = new Medicamente();
                medicament.setId(cursor.getInt(cursor.getColumnIndex(medicament.COLUMN_MEDICAMENTE_Id)));
                medicament.setDenumire(cursor.getString(cursor.getColumnIndex(medicament.COLUMN_MEDICAMENTE_Denumire)));

                medicamente.add(medicament);
            } while (cursor.moveToNext());
        }

        db.close();
        return medicamente; // return medicament list
    }

    public List<Integer> getListaIds_Medicamente() {
        List<Integer> lista_ids = new ArrayList<Integer>();

        String selectQuery = "";
        selectQuery = "SELECT * FROM " + Medicamente.TABLE_NAME_Medicamente;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Integer int_id = cursor.getInt(cursor.getColumnIndex(Medicamente.COLUMN_MEDICAMENTE_Id));
                lista_ids.add(int_id);

            } while (cursor.moveToNext());
        }

        db.close();

        return lista_ids;
    }

    public int getMedicamenteCount() {
        String countQuery = "SELECT  * FROM " + Medicamente.TABLE_NAME_Medicamente;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Update Medicamente:
    public int updateMedicamente(Medicamente medicament) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Medicamente.COLUMN_MEDICAMENTE_Denumire, medicament.getDenumire());

        // updating row
        return db.update(Medicamente.TABLE_NAME_Medicamente, values, Medicamente.COLUMN_MEDICAMENTE_Id + " = ?",
                new String[]{String.valueOf(medicament.getId())});
    }

    // Delete Medicamente:
    public void deleteMedicamente(Medicamente medicament) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Medicamente.TABLE_NAME_Medicamente, Medicamente.COLUMN_MEDICAMENTE_Id + " = ?",
                new String[]{String.valueOf(medicament.getId())});
        db.close();
    }

    /////////////////////////////////////////////
    // ~~~ CRUD METHODS for Consultatii ~~~
    /////////////////////////////////////////////

    // Insert Consultatie:
    public long insertConsultatie(Integer idpacient,
                             Integer idmedicament,
                             String dataconsultatie,
                             String diagnostic,
                             Float dozamedicament) {
        SQLiteDatabase db = this.getWritableDatabase(); // get writable database as we want to write data

        ContentValues values = new ContentValues(); // used to define the column name and its data to be stored
        // `id` will be inserted automatically.
        values.put(Consultatii.COLUMN_CONSULTATII_IdPacient, idpacient);
        values.put(Consultatii.COLUMN_CONSULTATII_IdMedicament, idmedicament);
        values.put(Consultatii.COLUMN_CONSULTATII_DataConsultatie, dataconsultatie);
        values.put(Consultatii.COLUMN_CONSULTATII_Diagnostic, diagnostic);
        values.put(Consultatii.COLUMN_CONSULTATII_DozaMedicament, dozamedicament);

        // insert row
        long id = db.insert(Consultatii.TABLE_NAME_Consultatii, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    // Read Consultatii:
    public Consultatii getConsultatie(long id) {
        SQLiteDatabase db = this.getReadableDatabase(); // get readable database as we are not inserting anything

        Cursor cursor = db.query(Consultatii.TABLE_NAME_Consultatii,
                new String[]{Consultatii.COLUMN_CONSULTATII_IdConsultatie,
                        Consultatii.COLUMN_CONSULTATII_IdPacient,
                        Consultatii.COLUMN_CONSULTATII_IdMedicament,
                        Consultatii.COLUMN_CONSULTATII_DataConsultatie,
                        Consultatii.COLUMN_CONSULTATII_Diagnostic,
                        Consultatii.COLUMN_CONSULTATII_DozaMedicament},
                Consultatii.COLUMN_CONSULTATII_IdConsultatie + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare Consulatii object
        Consultatii consultatii = new Consultatii(
                cursor.getInt(cursor.getColumnIndex(Consultatii.COLUMN_CONSULTATII_IdConsultatie)),
                cursor.getInt(cursor.getColumnIndex(Consultatii.COLUMN_CONSULTATII_IdPacient)),
                cursor.getInt(cursor.getColumnIndex(Consultatii.COLUMN_CONSULTATII_IdMedicament)),
                cursor.getString(cursor.getColumnIndex(Consultatii.COLUMN_CONSULTATII_DataConsultatie)),
                cursor.getString(cursor.getColumnIndex(Consultatii.COLUMN_CONSULTATII_Diagnostic)),
                cursor.getFloat(cursor.getColumnIndex(Consultatii.COLUMN_CONSULTATII_DozaMedicament))
        );

        cursor.close();
        return consultatii;
    }

    public List<Consultatii> getAllConsultatii() {
        List<Consultatii> consultatii = new ArrayList<>();

        // Select All Query
        String selectQuery = "";
        selectQuery =
                "SELECT * FROM " + Consultatii.TABLE_NAME_Consultatii
                + " INNER JOIN " + Medicamente.TABLE_NAME_Medicamente
                + " ON " + "consultatii.IdMedicament = medicamente.IdMedicament"
                + " INNER JOIN " + Pacienti.TABLE_NAME_Pacienti
                + " ON " + "consultatii.IdPacient = pacienti.IdPacient"
                + " INNER JOIN " + Sectii.TABLE_NAME_Sectii
                + " ON " + "pacienti.IdPacient = sectii.IdPacient"
                + " INNER JOIN " + Medici.TABLE_NAME_Medici
                + " ON " + "sectii.IdMedic = medici.IdMedic";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Consultatii consultatie = new Consultatii();
                Medicamente medicament = new Medicamente();
                Pacienti pacient = new Pacienti();
                Medici medic = new Medici();

                consultatie.setIdConsultatie(cursor.getInt(cursor.getColumnIndex(consultatie.COLUMN_CONSULTATII_IdConsultatie)));
                consultatie.setIdPacient(cursor.getInt(cursor.getColumnIndex(consultatie.COLUMN_CONSULTATII_IdPacient)));
                consultatie.setIdMedicament(cursor.getInt(cursor.getColumnIndex(consultatie.COLUMN_CONSULTATII_IdMedicament)));
                consultatie.setDataconsultatie(cursor.getString(cursor.getColumnIndex(consultatie.COLUMN_CONSULTATII_DataConsultatie)));
                consultatie.setDiagnostic(cursor.getString(cursor.getColumnIndex(consultatie.COLUMN_CONSULTATII_Diagnostic)));
                consultatie.setDozamedicament(cursor.getFloat(cursor.getColumnIndex(consultatie.COLUMN_CONSULTATII_DozaMedicament)));

                // Atributele apartinand celorlalte clase/tabele

                consultatie.setDenumireMedicament(cursor.getString(cursor.getColumnIndex(medicament.COLUMN_MEDICAMENTE_Denumire)));
                consultatie.setNumeMedic(cursor.getString(cursor.getColumnIndex(medic.COLUMN_MEDICI_Nume)));
                consultatie.setPrenumeMedic(cursor.getString(cursor.getColumnIndex(medic.COLUMN_MEDICI_Prenume)));
                consultatie.setNumePacient(cursor.getString(cursor.getColumnIndex(pacient.COLUMN_PACIENTI_Nume)));
                consultatie.setPrenumePacient(cursor.getString(cursor.getColumnIndex(pacient.COLUMN_PACIENTI_Prenume)));

                consultatii.add(consultatie);
            } while (cursor.moveToNext());
        }

        db.close();
        return consultatii; // return consultatii list
    }

    public int getConsultatiiCount() {
        String countQuery = "SELECT  * FROM " + Consultatii.TABLE_NAME_Consultatii;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    // Update Consultatii:
    public int updateConsultatii(Consultatii consultatie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Consultatii.COLUMN_CONSULTATII_IdPacient, consultatie.getIdPacient());
        values.put(Consultatii.COLUMN_CONSULTATII_IdMedicament, consultatie.getIdMedicament());
        values.put(Consultatii.COLUMN_CONSULTATII_DataConsultatie, consultatie.getDataconsultatie());
        values.put(Consultatii.COLUMN_CONSULTATII_Diagnostic, consultatie.getDiagnostic());
        values.put(Consultatii.COLUMN_CONSULTATII_DozaMedicament, consultatie.getDozamedicament());

        // updating row
        return db.update(Consultatii.TABLE_NAME_Consultatii, values, Consultatii.COLUMN_CONSULTATII_IdConsultatie + " = ?",
                new String[]{String.valueOf(consultatie.getIdConsultatie())});
    }

    // Delete Consultatii:
    public void deleteConsultatii(Consultatii consultatie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Consultatii.TABLE_NAME_Consultatii, Consultatii.COLUMN_CONSULTATII_IdConsultatie + " = ?",
                new String[]{String.valueOf(consultatie.getIdConsultatie())});
        db.close();
    }


    //////////////////////////////
    // Methods for Utilizatori //
    /////////////////////////////
    public long InsertUtilizator(String username, String password, String TipUtilizator) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilizatori.COLUMN_UTILIZATORI_Username, username);
        values.put(Utilizatori.COLUMN_UTILIZATORI_Password, password);
        values.put(Utilizatori.COLUMN_UTILIZATORI_TipUtilizator, TipUtilizator);

        long id = db.insert(Utilizatori.TABLE_NAME_Utilizatori, null, values);

        db.close();

        return id;
    }


    public boolean checkUtilizatorAdmin(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = { Utilizatori.COLUMN_UTILIZATORI_IdUtilizator };
        String selection = Utilizatori.COLUMN_UTILIZATORI_Username + "=?"
                + " and " + Utilizatori.COLUMN_UTILIZATORI_Password + "=?"
                + " and " + Utilizatori.COLUMN_UTILIZATORI_TipUtilizator + "='Admin'";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(Utilizatori.TABLE_NAME_Utilizatori, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUtilizator(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = { Utilizatori.COLUMN_UTILIZATORI_IdUtilizator };
        String selection = Utilizatori.COLUMN_UTILIZATORI_Username + "=?" + " and " + Utilizatori.COLUMN_UTILIZATORI_Password + "=?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(Utilizatori.TABLE_NAME_Utilizatori, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
