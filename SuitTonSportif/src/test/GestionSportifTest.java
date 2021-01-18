package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fonctionnalite.GestionSportif;
import fonctionnalite.Sportif;
import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GestionSportifTest {
  private GestionSportif gestion;

  @BeforeEach
  public void initialiser() {
    gestion = new GestionSportif();
    gestion.ajouterSportif("Valentino", "Rossi", "monster", "wesh&=", new Date(1976, 9, 4));
    gestion.ajouterSportif("Quartararo", "Fabio", "quarta", "boop*&=", new Date(1999, 4, 12));
  }

  @AfterEach
  public void nettoyer() {
    gestion = null;
  }

  @Test
  public void gestionSportif() {
    assertNotNull(gestion);
    assertNotNull(gestion.getListeDesSportifs());
  }

  @Test
  public void ajout() { // Test de l'ajout d'un sportif
    assertTrue(gestion.ajouterSportif("Ondra", "Adam", "adam", "coucou", new Date(1986, 7, 4)));

    // avec un attribut null
    assertFalse(gestion.ajouterSportif(null, "Adam", "adam1", "coucou", new Date(1986, 7, 4)));
    assertFalse(gestion.ajouterSportif("Ondra", null, "adam2", "coucou", new Date(1986, 7, 4)));
    assertFalse(gestion.ajouterSportif("Ondra", "Adam", null, "coucou", new Date(1986, 7, 4)));
    assertFalse(gestion.ajouterSportif("Ondra", "Adam", "adam3", null, new Date(1986, 7, 4)));
    assertFalse(gestion.ajouterSportif("Ondra", "Adam", "adam4", "coucou", null));

    // avec un attribut vide
    assertFalse(gestion.ajouterSportif("", "Adam", "adam1", "coucou", new Date(1986, 7, 4)));
    assertFalse(gestion.ajouterSportif("Ondra", "", "adam2", "coucou", new Date(1986, 7, 4)));
    assertFalse(gestion.ajouterSportif("Ondra", "Adam", "", "coucou", new Date(1986, 7, 4)));
    assertFalse(gestion.ajouterSportif("Ondra", "Adam", "adam3", "", new Date(1986, 7, 4)));

    // si la date est superieure a ajourd'hui ??
    // Si le pseudo a des caractères spéciaux ??
    // Si le pseudo existe deja
    assertFalse(
        gestion.ajouterSportif("Honold", "Aranaud", "adam", "coucou", new Date(1986, 7, 4)));
  }

  @Test
  public void suppression() { // test de la suppression d'un sportif
    assertTrue(gestion.supprimerSportif("quarta"));
    // supprier un sportif qui existe pas
    assertFalse(gestion.supprimerSportif("KimYahin"));
    // supprimer un sportif qui à deja été supprimer
    assertFalse(gestion.supprimerSportif("quarta"));
  }

  @Test
  public void modification() { // test de la modification d'un sportif
    assertTrue(gestion.modifierSportif("monster", "Zarco", "Johann", "NewMonster", "boop",
        new Date(1986, 7, 4)));
    // sportif qui n'existe pas
    assertFalse(
        gestion.modifierSportif("Bas", "Basil", "Loris", "bous", "wooop", new Date(1986, 7, 4)));
    // avec un argument null
    assertFalse(
        gestion.modifierSportif(null, "Ondra", "Adam", "adam", "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", null, "Adam", "adam", "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", "Ondra", null, "adam", "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", "Ondra", "Adam", null, "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", "Ondra", "Adam", "adam", null, new Date(1986, 7, 4)));
    assertFalse(gestion.modifierSportif("quarta", "Ondra", "Adam", "adam", "coucou", null));
    // avec un argument vide
    assertFalse(
        gestion.modifierSportif("", "Ondra", "Adam", "adam", "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", "", "Adam", "adam", "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", "Ondra", "", "adam", "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", "Ondra", "Adam", "", "coucou", new Date(1986, 7, 4)));
    assertFalse(
        gestion.modifierSportif("quarta", "Ondra", "Adam", "adam", "", new Date(1986, 7, 4)));
  }

  @Test
  public void consulter() { // test de la consultation de sportif
    Sportif sportif = gestion.consulterSportif("quarta");
    assertEquals(sportif.getNom(), "Quartararo");
    assertEquals(sportif.getPrenom(), "Fabio");
    assertEquals(sportif.getPseudo(), "quarta");
    assertEquals(sportif.getMotDePasse(), "boop*&=");
    assertEquals(sportif.getDateDeNaissance(), new Date(1999, 4, 12));

    // sportif qui n'existe pas
    assertNull(gestion.consulterSportif("adam"));
    // pseudo null
    assertNull(gestion.consulterSportif(null));
    // pseudo vide
    assertNull(gestion.consulterSportif(""));
  }
}
