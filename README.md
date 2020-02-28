# Android-SQLite-Database-MultipleTables
Database management application created with SQLite, using Android interface.

## Screenshots:
<p float="left">
  <img src="Images/Screenshot01_LogIn.jpg">
  <img src="Images/Screenshot02_Register.jpg">
  <img src="Images/Screenshot03_MainMenu.jpg">
</p>
<p float="left">
  <img src="Images/Screenshot04_Options_as_ADMIN.jpg">
  <img src="Images/Screenshot05_Options_as_CLIENT.jpg">
  <img src="Images/Screenshot06_MedicsTable.jpg">
</p>
<p float="left">
  <img src="Images/Screenshot07_PacientsTable.jpg">
  <img src="Images/Screenshot08_PacientsTableEdit.jpg">
  <img src="Images/Screenshot09_PacientsAdd.jpg">
</p>
<p float="left">
  <img src="Images/Screenshot10_SectionsTable.jpg">
  <img src="Images/Screenshot11_SectionsEdit.jpg">
  <img src="Images/Screenshot12_MainMenu2.jpg">
</p>
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
-- -----------------------------------------------------
-- Schema SQLiteProject
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SQLiteProject` DEFAULT CHARACTER SET latin1 ;
USE `SQLiteProject` ;

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
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `SQLiteProject`.`medicamente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`medicamente` (
  `IdMedicament` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `Denumire` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`IdMedicament`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


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
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `SQLiteProject`.`medici`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SQLiteProject`.`medici` (
  `IdMedic` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `NumeMedic` VARCHAR(45) NULL DEFAULT NULL,
  `PrenumeMedic` VARCHAR(45) NULL DEFAULT NULL,
  `Specializare` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`IdMedic`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


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
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
```



The interface and functionality of the application made in Android Studio will allow the user to perform operations: view, add, edit, delete. Viewing the connection tables will mean viewing the data referenced in the other tables.
