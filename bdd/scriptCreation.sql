SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema enregistretonsportif
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `enregistretonsportif` DEFAULT CHARACTER SET utf8 ;
USE `enregistretonsportif` ;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`SPORTIF`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `enregistretonsportif`.`SPORTIF` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`SPORTIF` (
  `idSportif` INT NOT NULL AUTO_INCREMENT,
  `nomSportif` VARCHAR(45) NOT NULL,
  `prenomSportif` VARCHAR(45) NOT NULL,
  `dateDeNaissanceSportif` DATE NOT NULL,
  `motDePasseSportif` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSportif`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`ACTIVITE_SPORTIVE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`ACTIVITE_SPORTIVE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`ACTIVITE_SPORTIVE` (
  `nomActivite` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`nomActivite`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`RELATION_SPORTIF_ACTIVITE_SPORTIVE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`RELATION_SPORTIF_ACTIVITE_SPORTIVE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`RELATION_SPORTIF_ACTIVITE_SPORTIVE` (
  `idRelationSportifActiviteSportive` INT NOT NULL AUTO_INCREMENT,
  `unSportif` INT NOT NULL,
  `uneActiviteSportive` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRelationSportifActiviteSportive`),
  INDEX `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_SPORTIF_idx` (`unSportif` ASC),
  INDEX `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_ACTIVITE_SPORTIVE_idx` (`uneActiviteSportive` ASC),
  CONSTRAINT `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_SPORTIF`
    FOREIGN KEY (`unSportif`)
    REFERENCES `enregistretonsportif`.`SPORTIF` (`idSportif`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_ACTIVITE_SPORTIVE`
    FOREIGN KEY (`uneActiviteSportive`)
    REFERENCES `enregistretonsportif`.`ACTIVITE_SPORTIVE` (`nomActivite`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`QUESTIONNAIRE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`QUESTIONNAIRE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`QUESTIONNAIRE` (
  `intituleQuestionnaire` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`intituleQuestionnaire`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`RELATION_SPORTIF_QUESTIONNAIRE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`RELATION_SPORTIF_QUESTIONNAIRE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`RELATION_SPORTIF_QUESTIONNAIRE` (
  `idRelationSportifQuestionnaire` INT NOT NULL AUTO_INCREMENT,
  `unSportif` INT NOT NULL,
  `unQuestionnaire` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRelationSportifQuestionnaire`),
  INDEX `fk_RELATION_SPORTIF_QUESTIONNAIRE_SPORTIF_idx` (`unSportif` ASC),
  INDEX `fk_RELATION_SPORTIF_QUESTIONNAIRE_QUESTIONNAIRE_idx` (`unQuestionnaire` ASC),
  CONSTRAINT `fk_RELATION_SPORTIF_QUESTIONNAIRE_SPORTIF`
    FOREIGN KEY (`unSportif`)
    REFERENCES `enregistretonsportif`.`SPORTIF` (`idSportif`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RELATION_SPORTIF_QUESTIONNAIRE_QUESTIONNAIRE`
    FOREIGN KEY (`unQuestionnaire`)
    REFERENCES `enregistretonsportif`.`QUESTIONNAIRE` (`intituleQuestionnaire`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`REPONSE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`REPONSE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`REPONSE` (
  `idReponse` INT NOT NULL,
  `numeroSemaine` INT NOT NULL,
  `derniereModification` DATE NOT NULL,
  `valeurReponse` INT NOT NULL,
  `unSportif` INT NOT NULL,
  PRIMARY KEY (`idReponse`),
  INDEX `fk_REPONSE_SPORTIF_idx` (`unSportif` ASC),
  CONSTRAINT `fk_REPONSE_SPORTIF`
    FOREIGN KEY (`unSportif`)
    REFERENCES `enregistretonsportif`.`SPORTIF` (`idSportif`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `enregistretonsportif`.`QUESTION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`QUESTION` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`QUESTION` (
  `intituleQuestion` VARCHAR(45) NOT NULL,
  `reponseParDefaut` INT NOT NULL,
  `unQuestionnaire` VARCHAR(45) NOT NULL,
  `uneReponse` INT NOT NULL,
  PRIMARY KEY (`intituleQuestion`),
  INDEX `fk_QUESTION_QUESTIONNAIRE_idx` (`unQuestionnaire` ASC),
  INDEX `fk_QUESTION_REPONSE_idx` (`uneReponse` ASC),
  CONSTRAINT `fk_QUESTION_QUESTIONNAIRE`
    FOREIGN KEY (`unQuestionnaire`)
    REFERENCES `enregistretonsportif`.`QUESTIONNAIRE` (`intituleQuestionnaire`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_QUESTION_REPONSE`
    FOREIGN KEY (`uneReponse`)
    REFERENCES `enregistretonsportif`.`REPONSE` (`idReponse`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;