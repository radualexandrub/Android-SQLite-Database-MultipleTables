# Android-SQLite-Database-MultipleTables
Database management application created with SQLite, using Android interface.

The figure below shows the tables corresponding to the database diagram for the application.
<img src="Images/Diagram.jpg">
The database is composed of the following tables:
- Doctor (MedicID, Medical Name, Medical First Name, Specialization);
- Patient (PatientID, CNP, Patient Name, Patient First Name, Address, Insurance);
- Medicines (MedicineID, Name).

The associations between the tables are as follows:
- between the Doctor table and the Patient table there is an association of multiplicity M:N, which will be
decomposed by a link table called Sections (with PatientID and MedicID as foreign keys).
- between the Patient table and the Medication table there is an association of multiplicity M:N. In the
in this case, the connection table will be called Consultations (with PatientID and MedicineID as foreign keys).

This diagram was made in MySQL Workbench environment, writing the following SQL code:
```
CREATE SCHEMA IF NOT EXISTS `SQLiteProject` DEFAULT CHARACTER SET latini;
USE `SQLiteProject`;

--- -------------------------------------------------------------
--- Table `SQLiteProject`.`pacienti`
----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`pacienti` (
  `IdPacient` BIGINT(20) UNSIGNED NOT NULL AUTO_ICREMENT,
  `CNP` VARCHAR(45) NULL DEFAULT NULL,
  `NumePacient` VARCHAR(45) NULL DEFAULT NULL,
  `PrenumePacient` VARCHAR(45) NULL DEFAULT NULL,
  `Adresa` VARCHAR(45) NULL DEFAULT NULL,
  `Asigurare` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`IdPacient`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latini;


--- -------------------------------------------------------------
--- Table `SQLiteProject`.`medicamente`
----------------------------------------------------------------


```



The interface and functionality of the application made in Android Studio will allow the user to perform operations: view, add, edit, delete. Viewing the connection tables will mean viewing the data referenced in the other tables.
