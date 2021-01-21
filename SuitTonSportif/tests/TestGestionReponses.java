import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fonctionnalite.GestionReponses;
import fonctionnalite.Question;
import fonctionnalite.QuestionBooleene;
import fonctionnalite.Questionnaire;
import fonctionnalite.Reponses;
import fonctionnalite.Sportif;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;


class TestGestionReponses {


  @Test
  public void testGestionReponses() {
    GestionReponses gestion = new GestionReponses();
    assertNotNull(gestion, "Error : testGestionReponses : L'instance n'a pas été créée");

    assertNotNull(gestion.getListeDesReponses(), "Error : testGestionReponses : La liste est vide");

  }


  @Test
  public void testAjoutReponses() {


    // Test dans le cas normal
    List<Question> listeQuestion = new ArrayList<Question>();
    Question question1 = new QuestionBooleene("Avez-vous pris de la drogue ?", false);
    Question question2 = new QuestionBooleene("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);


    List<Integer> reponse = new ArrayList<Integer>();
    reponse.add(new Integer(1));
    reponse.add(new Integer(0));

    Questionnaire question = new Questionnaire("Sondage", listeQuestion);


    GestionReponses gestion = new GestionReponses();
    // Calendar date = Calendar.getInstance();
    Date date = new Date(2020, 06, 22);
    // date.set(2020, 06, 22);
    Sportif sportif =
        new Sportif("Velo", "Marco", "theSportif", "marcovelo", new Date(1974, 03, 9));

    assertTrue(gestion.ajouterReponses(date, sportif, question, reponse));


    // Test dans le cas erreur

    assertFalse(gestion.ajouterReponses(null, null, null, null));


    // Test Questionnaire déja présent

    assertFalse(gestion.ajouterReponses(date, sportif, question, reponse));

  }

  @Test
  public void testModifierReponses() {
    GestionReponses gestion = new GestionReponses();

    // Test dans le cas erreur
    assertFalse(gestion.modifierReponses(null, null, null, null, null));


    // Test dans le cas normal


    List<Question> listeQuestion = new ArrayList<Question>();
    Question question1 = new QuestionBooleene("Avez-vous pris de la drogue ?", false);
    Question question2 = new QuestionBooleene("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);


    List<Integer> reponse = new ArrayList<Integer>();
    reponse.add(new Integer(1));
    reponse.add(new Integer(0));

    Date date = new Date(2020, 06, 22);
    Sportif sportif =
        new Sportif("Velo", "Marco", "theSportif", "marcovelo", new Date(1974, 03, 9));

    Questionnaire question = new Questionnaire("Sondage", listeQuestion);

    gestion.ajouterReponses(date, sportif, question, reponse);

    List<Reponses> listeReponse = gestion.getListeDesReponses();
    int numero = listeReponse.get(0).getNumeroSemaine();
    Integer numeroSemaine = new Integer(numero);

    Date nouvelleDate = new Date();

    List<Integer> nouvelleReponse = new ArrayList<Integer>();
    reponse.add(new Integer(0));
    reponse.add(new Integer(0));

    assertTrue(
        gestion.modifierReponses(numeroSemaine, nouvelleDate, sportif, question, nouvelleReponse));


    // Test dans le cas ou on entre les mêms informations
    assertFalse(
        gestion.modifierReponses(numeroSemaine, nouvelleDate, sportif, question, nouvelleReponse));


  }

  @Test
  public void testConsulterReponses() {

    // Test dans le cas erreur
    GestionReponses gestion = new GestionReponses();
    assertNull(gestion.consulterReponses(null, null, null));

    // Test dans le cas normal


    List<Question> listeQuestion = new ArrayList<Question>();
    Question question1 = new QuestionBooleene("Avez-vous pris de la drogue ?", false);
    Question question2 = new QuestionBooleene("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);

    Questionnaire question = new Questionnaire("Sondage", listeQuestion);

    List<Integer> reponse = new ArrayList<Integer>();
    reponse.add(new Integer(1));
    reponse.add(new Integer(0));

    Sportif sportif =
        new Sportif("Velo", "Marco", "theSportif", "marcovelo", new Date(1974, 03, 9));
    Date date = new Date(2020, 6, 22);

    gestion.ajouterReponses(date, sportif, question, reponse);

    

    List<Question> listeQuestion2 = new ArrayList<Question>();
    Question question3 = new QuestionBooleene("Avez-vous pris de la drogue ?", false);
    Question question4 = new QuestionBooleene("En prenez-vous régulièrement ?", false);
    listeQuestion.add(question1);
    listeQuestion.add(question2);
    
    Sportif sportif2 =
        new Sportif("Champion", "Dimitri", "Campione", "dimitriChampion", new Date(1974, 03, 9));

    Questionnaire questionnaire = new Questionnaire("Sondage2", listeQuestion);

    List<Integer> reponse2 = new ArrayList<Integer>();
    reponse.add(new Integer(0));
    reponse.add(new Integer(1));

    Date date2 = new Date(2020, 8, 16);

    gestion.ajouterReponses(date2, sportif2, questionnaire, reponse2);

    List<Reponses> listeReponse = gestion.getListeDesReponses();
    int numero = listeReponse.get(0).getNumeroSemaine();
    Integer numeroSemaine = new Integer(numero);



  }



}
