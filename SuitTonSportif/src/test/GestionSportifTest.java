package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fonctionnalite.GestionSportif;
import fonctionnalite.Sportif;
import java.util.Calendar;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GestionSportifTest {
  private GestionSportif gestion;

  @BeforeEach
  public void initialiser() {
    gestion = new GestionSportif();
    Calendar date = Calendar.getInstance();
    date.set(1976, 7, 3);
    gestion.ajouterSportif("Valentino", "Rossi", "monster", "wesh&=", date);
    date.set(1999, 4, 12);
    gestion.ajouterSportif("Quartararo", "Fabio", "quarta", "boop*&=", date);
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
    Calendar date = Calendar.getInstance();
    date.set(1976, 7, 4);
    assertEquals(gestion.ajouterSportif("Ondra", "Adam", "adam", "coucou", date), 1);

    // avec un attribut null
    assertEquals(gestion.ajouterSportif(null, "Adam", "adam1", "coucou", date), -2);
    assertEquals(gestion.ajouterSportif("Ondra", null, "adam2", "coucou", date), -2);
    assertEquals(gestion.ajouterSportif("Ondra", "Adam", null, "coucou", date), -2);
    assertEquals(gestion.ajouterSportif("Ondra", "Adam", "adam3", null, date), -2);
    assertEquals(gestion.ajouterSportif("Ondra", "Adam", "adam4", "coucou", null), -2);

    // avec un attribut vide
    assertEquals(gestion.ajouterSportif("", "Adam", "adam5", "coucou", date), -2);
    assertEquals(gestion.ajouterSportif("Ondra", "", "adam6", "coucou", date), -2);
    assertEquals(gestion.ajouterSportif("Ondra", "Adam", "", "coucou", date), -2);
    assertEquals(gestion.ajouterSportif("Ondra", "Adam", "adam7", "", date), -2);

    // Si le pseudo existe deja
    assertEquals(gestion.ajouterSportif("Honold", "Aranaud", "adam", "coucou", date), -1);
  }

  @Test
  public void suppression() { // test de la suppression d'un sportif
    assertEquals(gestion.supprimerSportif("quarta"), 1);
    // supprier un sportif qui existe pas
    assertEquals(gestion.supprimerSportif("KimYahin"), 0);
    // supprimer un sportif qui à deja été supprimer
    assertEquals(gestion.supprimerSportif("quarta"), 0);
    // ajout de quarta pour à nouveau le supprimer
    Calendar date = Calendar.getInstance();
    date.set(1999, 4, 12);
    gestion.ajouterSportif("Quartararo", "Fabio", "quarta", "boop*&=", date);
    assertEquals(gestion.supprimerSportif("quarta"), 1);
  }

  @Test
  public void modification() { // test de la modification d'un sportif
    Calendar date = Calendar.getInstance();
    date.set(1976, 7, 4);
    assertEquals(gestion.modifierSportif("monster", "Zarco", "Johann", "NewMonster", "boop", date),
        1);
    // sportif qui n'existe pas
    assertEquals(gestion.modifierSportif("Bas", "Basil", "Loris", "bous", "wooop", date), -1);
    // avec un argument null
    assertEquals(gestion.modifierSportif(null, "Ondra", "Adam", "adam", "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", null, "Adam", "adam", "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", "Ondra", null, "adam", "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", "Ondra", "Adam", null, "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", "Ondra", "Adam", "adam", null, date), -3);
    assertEquals(gestion.modifierSportif("quarta", "Ondra", "Adam", "adam", "coucou", null), -3);
    // avec un argument vide
    assertEquals(gestion.modifierSportif("", "Ondra", "Adam", "adam", "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", "", "Adam", "adam", "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", "Ondra", "", "adam", "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", "Ondra", "Adam", "", "coucou", date), -3);
    assertEquals(gestion.modifierSportif("quarta", "Ondra", "Adam", "adam", "", date), -3);
    // modifier le pseudo en quelque chose qui existe deja
    assertEquals(
        gestion.modifierSportif("quarta", "Quartararo", "Fabio", "NewMonster", "boop*&=", date),
        -2);
  }

  @Test
  public void consulter() { // test de la consultation de sportif
    Sportif sportif = gestion.consulterSportif("quarta");
    assertEquals(sportif.getNom(), "Quartararo");
    assertEquals(sportif.getPrenom(), "Fabio");
    assertEquals(sportif.getPseudo(), "quarta");
    assertEquals(sportif.getMotDePasse(), "boop*&=");

    // sportif qui n'existe pas
    assertNull(gestion.consulterSportif("adam"));
    // pseudo null
    assertNull(gestion.consulterSportif(null));
    // pseudo vide
    assertNull(gestion.consulterSportif(""));
  }
}
