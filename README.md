# Android-SQLite-Database-MultipleTables
Database management application created with SQLite, using Android interface.

## Screenshots:
<p float="left">
  <img src="Images/Screenshot01_LogIn.jpg" width=200 height=355>
  <img src="Images/Screenshot02_Register.jpg" width=200 height=355>
  <img src="Images/Screenshot03_MainMenu.jpg" width=200 height=355>
  <img src="Images/Screenshot04_Options_as_ADMIN.jpg" width=200 height=355>
</p>
<p float="left">
  <img src="Images/Screenshot05_Options_as_CLIENT.jpg" width=200 height=355>
  <img src="Images/Screenshot06_MedicsTable.jpg" width=200 height=355>
  <img src="Images/Screenshot07_PacientsTable.jpg" width=200 height=355>
  <img src="Images/Screenshot08_PacientsTableEdit.jpg" width=200 height=355>
</p>
<p float="left">
  <img src="Images/Screenshot09_PacientsAdd.jpg" width=200 height=355>
  <img src="Images/Screenshot10_SectionsTable.jpg" width=200 height=355>
  <img src="Images/Screenshot11_SectionsEdit.jpg" width=200 height=355>
  <img src="Images/Screenshot12_MainMenu2.jpg" width=200 height=355>
</p>

## Database used:
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

This diagram was made in MySQL Workbench environment, by writing the following SQL code:
```
-- -----------------------------------------------------
-- Schema SQLiteProject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SQLiteProject`;
USE `SQLiteProject`;

-- -----------------------------------------------------
-- Table `SQLiteProject`.`pacienti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`pacienti` (
  `IdPacient` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `CNP` VARCHAR(45) NULL DEFAULT NULL,
  `NumePacient` VARCHAR(45) NULL DEFAULT NULL,
  `PrenumePacient` VARCHAR(45) NULL DEFAULT NULL,
  `Adresa` VARCHAR(45) NULL DEFAULT NULL,
  `Asigurare` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`IdPacient`))


-- -----------------------------------------------------
-- Table `SQLiteProject`.`medicamente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`medicamente` (
  `IdMedicament` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Denumire` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`IdMedicament`))


-- -----------------------------------------------------
-- Table `SQLiteProject`.`consultatii`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`consultatii` (
  `IdConsultatie` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `IdPacient` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  `IdMedicament` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  `DataConsultatie` DATE NULL DEFAULT NULL,
  `Diagnostic` VARCHAR(45) NULL DEFAULT NULL,
  `DozaMedicament` FLOAT UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`IdConsultatie`),
  INDEX `fk_consultatii_1_idx` (`IdPacient` ASC) VISIBLE,
  INDEX `fk_consultatii_2_idx` (`IdMedicament` ASC) VISIBLE,
  CONSTRAINT `fk_consultatii_1`
    FOREIGN KEY (`IdPacient`)
    REFERENCES `SQLiteProject`.`pacienti` (`IdPacient`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_consultatii_2`
    FOREIGN KEY (`IdMedicament`)
    REFERENCES `SQLiteProject`.`medicamente` (`IdMedicament`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)


-- -----------------------------------------------------
-- Table `SQLiteProject`.`medici`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`medici` (
  `IdMedic` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NumeMedic` VARCHAR(45) NULL DEFAULT NULL,
  `PrenumeMedic` VARCHAR(45) NULL DEFAULT NULL,
  `Specializare` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`IdMedic`))


-- -----------------------------------------------------
-- Table `SQLiteProject`.`sectii`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`sectii` (
  `IdSectie` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `IdPacient` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  `IdMedic` BIGINT(20) UNSIGNED NULL DEFAULT NULL,
  PRIMARY KEY (`IdSectie`),
  INDEX `fk_sectii_1_idx` (`IdPacient` ASC) VISIBLE,
  INDEX `fk_sectii_2_idx` (`IdMedic` ASC) VISIBLE,
  CONSTRAINT `fk_sectii_1`
    FOREIGN KEY (`IdPacient`)
    REFERENCES `SQLiteProject`.`pacienti` (`IdPacient`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_sectii_2`
    FOREIGN KEY (`IdMedic`)
    REFERENCES `SQLiteProject`.`medici` (`IdMedic`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
```

The interface and functionality of the application made in Android Studio will allow the user to perform operations: view, add, edit, delete. Viewing the connection tables will mean viewing the data referenced in the other tables.

## Application description:
This app will allow the user to do the following operations:
- To register as a Client user, by creating an account in the database (eg: an entry in the Users table, separated from the rest of the schema).
- To access the application by logging in either as a Client user or as an Administrator user. Customers can perform the following operations: viewing, adding and modifying data. The administrator can perform the operations of viewing, adding, modifying and deleting data. (To create a new Administrator user, it is needed to open Android Studio and call **db.InsertUtilizator("InsertName", "InsertPass", "Admin");** method in one of the **onCreate** methods (either from Activity_Login.java or Activity_Register.java)
- View data in each table.
- Insert data into each of the tables.
- Edit the data (hold down a record in a table to acces the edit/delete menu)
- Sort the doctors table by name of doctor or by specialization.
- Change the current authenticated user.
