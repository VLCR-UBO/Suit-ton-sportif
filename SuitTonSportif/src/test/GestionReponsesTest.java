package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import fonctionnalite.Facade;
import fonctionnalite.GestionQuestionnaire;
import fonctionnalite.GestionReponses;
import fonctionnalite.GestionSportif;
import fonctionnalite.Question;
import fonctionnalite.QuestionBoolenne;
import fonctionnalite.Questionnaire;
import fonctionnalite.Reponses;
import fonctionnalite.Sportif;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// consulterReponses n'est pas tester à part car déjà utiliser à deux reprise, dans le test
// d'ajouterReponses, et dans le tests modifierReponses.

class GestionReponsesTest {
  private Facade facade;

  @BeforeEach
  public void initialiser() {
    // pour effacer des données qui pourrait altérer le résultat des tests
    this.facade = new Facade(true);
  }

  @Test
  public void testGestionReponses() {
    GestionReponses gestion = new GestionReponses();
    assertNotNull(gestion, "Error : testGestionReponses : L'instance n'a pas été créée");
  }


  @Test
  public void testAjoutReponses() {
    // Test dans le cas normal
    List<String> listeDesQuestions6 = new ArrayList<String>();
    this.facade.ajouterUnQuestionnaire("azerty", listeDesQuestions6);
    this.facade.ajouterUneQuestion("azerty", "Question1");
    this.facade.ajouterUneQuestion("azerty", "Question2");
    this.facade.ajouterUneQuestion("azerty", "Question3");
    Calendar dateSportif8 = Calendar.getInstance();
    dateSportif8.set(1976, 7, 3); // annee/mois/jour
    this.facade.ajouterUnSportif("Ondra", "Adam", "grinpeurDu57", "leRageux", dateSportif8);

    HashMap<String, Integer> listeReponses = new HashMap<String, Integer>();
    listeReponses.put("Question1", 1);
    listeReponses.put("Question2", 0);
    listeReponses.put("Question3", 1);
    int numeroSemaine = 10;

    // Ajout réponse
    GestionReponses gestionReponses = facade.getGestionReponses();
    GestionSportif gestionSportif = facade.getGestionSportif();
    GestionQuestionnaire gestionQuestionnaire = facade.getGestionQuestionnaire();

    Sportif unSportif = gestionSportif.consulterSportif("grinpeurDu57");
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion("azerty");
    int ret = gestionReponses.ajouterReponses(numeroSemaine, new Date(), unSportif, unQuestionnaire,
        listeReponses);
    assertEquals(ret, 1, "FacadeTest : L'ajout de réponses à échouer anormalement");

    // Consulter réponse
    HashMap<String, Integer> map =
        this.facade.obtenirQuestionnaireEtReponses(10, "grinpeurDu57", "azerty");
    assertEquals(map.size(), 3, "FacadeTest : La hashmap n'est pas conforme aux ajouts précédent");
    assertEquals(map.get("Question1"), 1,
        "FacadeTest : L'ajout de réponses à échouer anormalement");
    assertEquals(map.get("Question2"), 0,
        "FacadeTest : L'ajout de réponses à échouer anormalement");
    assertEquals(map.get("Question3"), 1,
        "FacadeTest : L'ajout de réponses à échouer anormalement");


    // Test dans le cas erreur
    int ret1 = gestionReponses.ajouterReponses(null, null, null, null, null);
    assertEquals(ret1, -2);
  }

  @Test
  public void testModifierReponses() {
    // Test dans le cas normal
    List<String> listeDesQuestions6 = new ArrayList<String>();
    this.facade.ajouterUnQuestionnaire("azerty", listeDesQuestions6);
    this.facade.ajouterUneQuestion("azerty", "Question1");
    this.facade.ajouterUneQuestion("azerty", "Question2");
    this.facade.ajouterUneQuestion("azerty", "Question3");
    Calendar dateSportif8 = Calendar.getInstance();
    dateSportif8.set(1976, 7, 3); // annee/mois/jour
    this.facade.ajouterUnSportif("Ondra", "Adam", "grinpeurDu57", "leRageux", dateSportif8);

    HashMap<String, Integer> listeReponses = new HashMap<String, Integer>();
    listeReponses.put("Question1", 1);
    listeReponses.put("Question2", 0);
    listeReponses.put("Question3", 1);

    int numeroSemaine = 10;

    // Ajout réponse
    GestionReponses gestionReponses = facade.getGestionReponses();
    GestionSportif gestionSportif = facade.getGestionSportif();
    GestionQuestionnaire gestionQuestionnaire = facade.getGestionQuestionnaire();

    Sportif unSportif = gestionSportif.consulterSportif("grinpeurDu57");
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion("azerty");
    HashMap<String, Integer> listeReponses2 = new HashMap<String, Integer>();
    listeReponses2.put("Question1", 0);
    listeReponses2.put("Question2", 0);
    listeReponses2.put("Question3", 0);

    // cas d'erreur avant l'ajout
    int ret2 = gestionReponses.modifierReponses(numeroSemaine, new Date(), unSportif,
        unQuestionnaire, listeReponses2);
    assertEquals(ret2, -1);

    gestionReponses.ajouterReponses(numeroSemaine, new Date(), unSportif, unQuestionnaire,
        listeReponses);

    int ret = gestionReponses.modifierReponses(numeroSemaine, new Date(), unSportif,
        unQuestionnaire, listeReponses2);
    assertEquals(ret, 1, "FacadeTest : L'ajout de réponses à échouer anormalement");

    // Consulter réponse
    HashMap<String, Integer> map =
        this.facade.obtenirQuestionnaireEtReponses(10, "grinpeurDu57", "azerty");
    assertEquals(map.size(), 3, "FacadeTest : La hashmap n'est pas conforme aux ajouts précédent");
    assertEquals(map.get("Question1"), 0,
        "FacadeTest : L'ajout de réponses à échouer anormalement");
    assertEquals(map.get("Question2"), 0,
        "FacadeTest : L'ajout de réponses à échouer anormalement");
    assertEquals(map.get("Question3"), 0,
        "FacadeTest : L'ajout de réponses à échouer anormalement");

    // Test dans le cas erreur
    int ret1 = gestionReponses.modifierReponses(null, null, null, null, null);
    assertEquals(ret1, -2);
  }

}
