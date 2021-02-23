package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fonctionnalite.GestionReponses;
import fonctionnalite.Question;
import fonctionnalite.QuestionBoolenne;
import fonctionnalite.Questionnaire;
import fonctionnalite.Reponses;
import fonctionnalite.Sportif;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;


class GestionReponsesTest {
  @Test
  public void testGestionReponses() {
    GestionReponses gestion = new GestionReponses();
    assertNotNull(gestion, "Error : testGestionReponses : L'instance n'a pas été créée");
  }


  @Test
  public void testAjoutReponses() {
    // Test dans le cas normal
    List<Question> listeQuestion = new ArrayList<Question>();
    Question question1 = new QuestionBoolenne("Avez-vous pris de la drogue ?", false);
    Question question2 = new QuestionBoolenne("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);


    List<Integer> reponse = new ArrayList<Integer>();
    reponse.add(1);
    reponse.add(0);

    Questionnaire question = new Questionnaire("Sondage", listeQuestion);


    GestionReponses gestion = new GestionReponses();
    // Calendar date = Calendar.getInstance();
    // date.set(2020, 06, 22);

    Calendar dateNaissance = Calendar.getInstance();
    dateNaissance.set(1974, 3, 9);

    Date date = new Date(2020, 06, 22);
    Sportif sportif = new Sportif("Velo", "Marco", "theSportif", "marcovelo", dateNaissance);

    assertTrue(gestion.ajouterReponses(20, date, sportif, question, reponse));


    // Test dans le cas erreur

    assertFalse(gestion.ajouterReponses(null, null, null, null, null));


    // Test Questionnaire déja présent

    assertFalse(gestion.ajouterReponses(20, date, sportif, question, reponse));

  }

  @Test
  public void testModifierReponses() {
    GestionReponses gestion = new GestionReponses();

    // Test dans le cas erreur
    assertEquals(gestion.modifierReponses(null, null, null, null, null), -2);


    // Test dans le cas normal


    List<Question> listeQuestion = new ArrayList<Question>();
    Question question1 = new QuestionBoolenne("Avez-vous pris de la drogue ?", false);
    Question question2 = new QuestionBoolenne("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);


    List<Integer> reponse = new ArrayList<Integer>();
    reponse.add(1);
    reponse.add(0);

    Calendar dateNaissance = Calendar.getInstance();
    dateNaissance.set(1974, 3, 9);
    Date date = new Date(2020, 06, 22);
    Sportif sportif = new Sportif("Velo", "Marco", "theSportif", "marcovelo", dateNaissance);

    Questionnaire question = new Questionnaire("Sondage", listeQuestion);

    gestion.ajouterReponses(20, date, sportif, question, reponse);

    List<Reponses> listeReponse = gestion.getListeDesReponses();
    int numero = listeReponse.get(0).getNumeroSemaine();

    Date nouvelleDate = new Date();

    List<Integer> nouvelleReponse = new ArrayList<Integer>();
    reponse.add(new Integer(0));
    reponse.add(new Integer(0));

    assertEquals(
        gestion.modifierReponses(numero, nouvelleDate, sportif, question, nouvelleReponse), 1);


    // Test dans le cas ou on entre les mêmes informations
    assertTrue(
        gestion.modifierReponses(numero, nouvelleDate, sportif, question, nouvelleReponse));


  }

  @Test
  public void testConsulterReponses() {

    // Test dans le cas erreur : paramètres incorrects
    GestionReponses gestion = new GestionReponses();
    assertNull(gestion.consulterReponses(null, null, null));

    // Test dans le cas normal

    List<Question> listeQuestion = new ArrayList<Question>();
    Question question1 = new QuestionBoolenne("Avez-vous pris de la drogue ?", false);
    Question question2 = new QuestionBoolenne("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);

    List<Integer> reponse = new ArrayList<Integer>();
    reponse.add(new Integer(1));
    reponse.add(new Integer(0));

    Calendar dateNaissance = Calendar.getInstance();
    dateNaissance.set(1974, 3, 9);
    Sportif sportif = new Sportif("Velo", "Marco", "theSportif", "marcovelo", dateNaissance);
    Date date = new Date(2020, 6, 22);
    Questionnaire question = new Questionnaire("Sondage", listeQuestion);

    gestion.ajouterReponses(20, date, sportif, question, reponse);


    List<Question> listeQuestion2 = new ArrayList<Question>();
    Question question3 = new QuestionBoolenne("Avez-vous pris de la drogue ?", false);
    Question question4 = new QuestionBoolenne("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);

    Calendar dateNaissance2 = Calendar.getInstance();
    dateNaissance.set(1974, 3, 9);
    Sportif sportif2 =
        new Sportif("Champion", "Dimitri", "Campione", "dimitriChampion", dateNaissance2);

    Questionnaire questionnaire = new Questionnaire("Sondage2", listeQuestion);

    List<Integer> reponse2 = new ArrayList<Integer>();
    reponse.add(new Integer(0));
    reponse.add(new Integer(1));

    Date date2 = new Date(2020, 8, 16);

    gestion.ajouterReponses(20, date2, sportif2, questionnaire, reponse2);

    List<Reponses> listeReponse = gestion.getListeDesReponses();
    int numero = listeReponse.get(0).getNumeroSemaine();
    Integer numeroSemaine = new Integer(numero);

    List<Integer> res;
    res = gestion.consulterReponses(numeroSemaine, sportif, question);

    assertEquals(reponse, res);


  }



}
