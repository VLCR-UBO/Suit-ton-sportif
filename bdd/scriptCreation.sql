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
  `pseudo` VARCHAR(256) NOT NULL,
  `nomSportif` VARCHAR(256) NOT NULL,
  `prenomSportif` VARCHAR(256) NOT NULL,
  `dateDeNaissanceSportif` DATE NOT NULL,
  `motDePasseSportif` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`pseudo`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`ACTIVITE_SPORTIVE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`ACTIVITE_SPORTIVE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`ACTIVITE_SPORTIVE` (
  `nomActivite` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`nomActivite`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`RELATION_SPORTIF_ACTIVITE_SPORTIVE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`RELATION_SPORTIF_ACTIVITE_SPORTIVE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`RELATION_SPORTIF_ACTIVITE_SPORTIVE` (
  `idRelationSportifActiviteSportive` INT NOT NULL AUTO_INCREMENT,
  `unSportif` VARCHAR(256) NOT NULL,
  `uneActiviteSportive` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`idRelationSportifActiviteSportive`),
  INDEX `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_SPORTIF_idx` (`unSportif` ASC),
  INDEX `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_ACTIVITE_SPORTIVE_idx` (`uneActiviteSportive` ASC),
  CONSTRAINT `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_SPORTIF`
    FOREIGN KEY (`unSportif`)
    REFERENCES `enregistretonsportif`.`SPORTIF` (`pseudo`)
    ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT `fk_RELATION_SPORTIF_ACTIVITE_SPORTIVE_ACTIVITE_SPORTIVE`
    FOREIGN KEY (`uneActiviteSportive`)
    REFERENCES `enregistretonsportif`.`ACTIVITE_SPORTIVE` (`nomActivite`)
    ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`QUESTIONNAIRE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`QUESTIONNAIRE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`QUESTIONNAIRE` (
  `intituleQuestionnaire` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`intituleQuestionnaire`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`RELATION_SPORTIF_QUESTIONNAIRE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`RELATION_SPORTIF_QUESTIONNAIRE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`RELATION_SPORTIF_QUESTIONNAIRE` (
  `idRelationSportifQuestionnaire` INT NOT NULL AUTO_INCREMENT,
  `unSportif` VARCHAR(256) NOT NULL,
  `unQuestionnaire` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`idRelationSportifQuestionnaire`),
  INDEX `fk_RELATION_SPORTIF_QUESTIONNAIRE_SPORTIF_idx` (`unSportif` ASC),
  INDEX `fk_RELATION_SPORTIF_QUESTIONNAIRE_QUESTIONNAIRE_idx` (`unQuestionnaire` ASC),
  CONSTRAINT `fk_RELATION_SPORTIF_QUESTIONNAIRE_SPORTIF`
    FOREIGN KEY (`unSportif`)
    REFERENCES `enregistretonsportif`.`SPORTIF` (`pseudo`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_RELATION_SPORTIF_QUESTIONNAIRE_QUESTIONNAIRE`
    FOREIGN KEY (`unQuestionnaire`)
    REFERENCES `enregistretonsportif`.`QUESTIONNAIRE` (`intituleQuestionnaire`)
    ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `enregistretonsportif`.`QUESTION`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`QUESTION` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`QUESTION` (
  `intituleQuestion` VARCHAR(256) NOT NULL,
  `reponseParDefaut` INT NOT NULL,
  `unQuestionnaire` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`intituleQuestion`),
  INDEX `fk_QUESTION_QUESTIONNAIRE_idx` (`unQuestionnaire` ASC),
  CONSTRAINT `fk_QUESTION_QUESTIONNAIRE`
    FOREIGN KEY (`unQuestionnaire`)
    REFERENCES `enregistretonsportif`.`QUESTIONNAIRE` (`intituleQuestionnaire`)
    ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `enregistretonsportif`.`REPONSE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `enregistretonsportif`.`REPONSE` ;

CREATE TABLE IF NOT EXISTS `enregistretonsportif`.`REPONSE` (
  `idReponse` INT NOT NULL AUTO_INCREMENT,
  `numeroSemaine` INT NOT NULL,
  `derniereModification` DATE NOT NULL,
  `valeurReponse` INT NOT NULL,
  `unSportif` VARCHAR(256) NOT NULL,
  `uneQuestion` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`idReponse`),
  INDEX `fk_REPONSE_SPORTIF_idx` (`unSportif` ASC),
  INDEX `fk_REPONSE_QUESTION_idx` (`uneQuestion` ASC),
  CONSTRAINT `fk_REPONSE_SPORTIF`
    FOREIGN KEY (`unSportif`)
    REFERENCES `enregistretonsportif`.`SPORTIF` (`pseudo`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_REPONSE_QUESTION`
    FOREIGN KEY (`uneQuestion`)
    REFERENCES `enregistretonsportif`.`QUESTION` (`intituleQuestion`)
    ON DELETE CASCADE ON UPDATE CASCADE)
ENGINE = InnoDB;
