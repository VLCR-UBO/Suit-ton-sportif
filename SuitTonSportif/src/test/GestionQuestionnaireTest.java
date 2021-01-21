package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fonctionnalite.GestionQuestionnaire;
import fonctionnalite.Question;
import fonctionnalite.QuestionBoolenne;
import fonctionnalite.Questionnaire;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class GestionQuestionnaireTest {
  private GestionQuestionnaire gestionQuestionnaire;

  @BeforeEach
  public void initialiser() {
    gestionQuestionnaire = new GestionQuestionnaire();
  }

  @AfterEach
  public void nettoyer() {
    gestionQuestionnaire = null;
  }

  @Test
  public void gestionQuestionnaire() {
    assertNotNull(gestionQuestionnaire,
        "TestGestionQuestionnaire : Instance gestionQuestionnaire non créée");
  }

  @Test
  public void getListeQuestionnaire() {
    List<Questionnaire> liste = gestionQuestionnaire.getListeQuestionnaire();
    assertNotNull(liste, "TestGestionQuestionnaire : La liste des questions est null");
    assertEquals(liste.size(), 0, "TestGestionQuestionnaire : La liste des questions est non vide");
  }

  @Test
  public void setListeQuestionnaire() {
    QuestionBoolenne uneQuestion = new QuestionBoolenne("intitule", true);
    List<Question> uneListeDeQuestion = new ArrayList<Question>();
    uneListeDeQuestion.add(uneQuestion);
    Questionnaire unQuestionnaire = new Questionnaire("questionnaire", uneListeDeQuestion);
    List<Questionnaire> uneListeDeQuestionnaire = new ArrayList<Questionnaire>();
    uneListeDeQuestionnaire.add(unQuestionnaire);
    gestionQuestionnaire.setListeQuestionnaire(uneListeDeQuestionnaire);

    List<Questionnaire> liste = gestionQuestionnaire.getListeQuestionnaire();
    assertNotNull(liste, "TestGestionQuestionnaire : La liste des questions est null");
    assertEquals(liste.size(), 1, "TestGestionQuestionnaire : La liste des questions est vide");
  }

  @Test
  public void ajouterQuestionnaire() {
    List<String> listeIntituleQuestions = new ArrayList<String>();
    listeIntituleQuestions.add("Aime tu le beurre salé ?");
    listeIntituleQuestions.add("Aime tu la pluie ?");
    // Le fonctionnement normal fonctionne ?
    assertTrue(gestionQuestionnaire.ajouterQuestionnaire("Questionnaire", listeIntituleQuestions),
        "TestGestionQuestionnaire : L'ajout d'un questionnaire à échouer anormalement");
    assertTrue(gestionQuestionnaire.getListeQuestionnaire().size() > 0,
        "TestGestionQuestionnaire : Le questionnaire n'a pas été ajouté à la liste");
    // Le cas de doublons est t-il gérer ?
    assertFalse(gestionQuestionnaire.ajouterQuestionnaire("Questionnaire", listeIntituleQuestions),
        "TestGestionQuestionnaire : L'ajout d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() > 1,
        "TestGestionQuestionnaire : Le questionnaire n'a pas été ajouté à la liste");
    // L'ajout est t-il arreté si l'intitulé est null ?
    assertFalse(gestionQuestionnaire.ajouterQuestionnaire(null, listeIntituleQuestions),
        "TestGestionQuestionnaire : L'ajout d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() > 1,
        "TestGestionQuestionnaire : Le questionnaire a été ajouté ànormalement à la liste");
    // L'ajout est t-il arreté si l'intitulé est une chaine vide ?
    assertFalse(gestionQuestionnaire.ajouterQuestionnaire("", listeIntituleQuestions),
        "TestGestionQuestionnaire : L'ajout d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() > 1,
        "TestGestionQuestionnaire : Le questionnaire a été ajouté ànormalement à la liste");
    // L'ajout est t-il arreté si la liste de question est null ?
    assertFalse(gestionQuestionnaire.ajouterQuestionnaire("Questionnaire", null),
        "TestGestionQuestionnaire : L'ajout d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() > 1,
        "TestGestionQuestionnaire : Le questionnaire a été ajouté ànormalement à la liste");
    // L'ajout est t-il arreté si la liste de question est vide ?
    List<String> listeIntituleQuestions2 = new ArrayList<String>();
    assertFalse(gestionQuestionnaire.ajouterQuestionnaire("Questionnaire", listeIntituleQuestions2),
        "TestGestionQuestionnaire : L'ajout d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() > 1,
        "TestGestionQuestionnaire : Le questionnaire a été ajouté ànormalement à la liste");
  }

  @Test
  public void modifierQuestionnaire() { // NE FONCTIONNE PAS
    List<String> listeIntituleQuestions = new ArrayList<String>();
    listeIntituleQuestions.add("Aime tu le beurre salé ?");
    gestionQuestionnaire.ajouterQuestionnaire("Questionnaire", listeIntituleQuestions);

    List<String> listeIntituleQuestions2 = new ArrayList<String>();
    listeIntituleQuestions2.add("Aime tu le beurre doux ?");

    // Le fonctionnement normal fonctionne ?
    assertTrue(gestionQuestionnaire.modifierQuestionnaire("Questionnaire", listeIntituleQuestions2),
        "TestGestionQuestionnaire : La modification d'un questionnaire à échouer anormalement");
    assertTrue(gestionQuestionnaire.getListeQuestionnaire().size() == 1,
        "TestGestionQuestionnaire : Le questionnaire n'est pas présent dans la liste");
    assertTrue(
        gestionQuestionnaire.getListeQuestionnaire().get(0).getListeDeQuestions().get(0)
            .getIntituleQuestion() == "Aime tu le beurre doux ?",
        "TestGestionQuestionnaire : Le questionnaire a été mise à jour");
    // La modification est t-elle arreté si le nom du questionnaire est null ?
    assertFalse(gestionQuestionnaire.modifierQuestionnaire(null, listeIntituleQuestions),
        "TestGestionQuestionnaire : La modification d'un questionnaire à échouer anormalement");
    assertTrue(
        gestionQuestionnaire.getListeQuestionnaire().get(0).getListeDeQuestions().get(0)
            .getIntituleQuestion() == "Aime tu le beurre doux ?",
        "TestGestionQuestionnaire : Le questionnaire a été mise à jour");
    // La modification est t-elle arreté si le nom du questionnaire est une chaine vide ?
    assertFalse(gestionQuestionnaire.modifierQuestionnaire("", listeIntituleQuestions),
        "TestGestionQuestionnaire : La modification d'un questionnaire à échouer anormalement");
    assertTrue(
        gestionQuestionnaire.getListeQuestionnaire().get(0).getListeDeQuestions().get(0)
            .getIntituleQuestion() == "Aime tu le beurre doux ?",
        "TestGestionQuestionnaire : Le questionnaire a été mise à jour");
    // La modification est t-elle arreté si le nom du questionnaire est inconnu ?
    assertFalse(gestionQuestionnaire.modifierQuestionnaire("test", listeIntituleQuestions),
        "TestGestionQuestionnaire : La modification d'un questionnaire à échouer anormalement");
    assertTrue(
        gestionQuestionnaire.getListeQuestionnaire().get(0).getListeDeQuestions().get(0)
            .getIntituleQuestion() == "Aime tu le beurre doux ?",
        "TestGestionQuestionnaire : Le questionnaire a été mise à jour");
    // La modification est t-elle arreté si la nouvelle liste est null ?
    assertFalse(gestionQuestionnaire.modifierQuestionnaire("Questionnaire", null),
        "TestGestionQuestionnaire : La modification d'un questionnaire à échouer anormalement");
    assertTrue(
        gestionQuestionnaire.getListeQuestionnaire().get(0).getListeDeQuestions().get(0)
            .getIntituleQuestion() == "Aime tu le beurre doux ?",
        "TestGestionQuestionnaire : Le questionnaire a été mise à jour");
    // La modification est t-elle arreté si la nouvelle liste est vide ?
    List<String> listeIntituleQuestions3 = new ArrayList<String>();
    assertFalse(gestionQuestionnaire.modifierQuestionnaire("Questionnaire", listeIntituleQuestions3),
        "TestGestionQuestionnaire : La modification d'un questionnaire à échouer anormalement");
    assertTrue(
        gestionQuestionnaire.getListeQuestionnaire().get(0).getListeDeQuestions().get(0)
            .getIntituleQuestion() == "Aime tu le beurre doux ?",
        "TestGestionQuestionnaire : Le questionnaire a été mise à jour");

  }

  @Test
  public void supprimerQuestionnaire() {
    // La suppression fonctionne t-elle s'il n'y a pas de questionnaire ?
    assertFalse(gestionQuestionnaire.supprimerQuestionnaire("Questionnaire"),
        "TestGestionQuestionnaire : La suppression d'un questionnaire à échouer anormalement");

    List<String> listeIntituleQuestions = new ArrayList<String>();
    listeIntituleQuestions.add("Aime tu le beurre salé ?");
    listeIntituleQuestions.add("Aime tu la pluie ?");
    gestionQuestionnaire.ajouterQuestionnaire("Questionnaire", listeIntituleQuestions);
    List<String> listeIntituleQuestions2 = new ArrayList<String>();
    listeIntituleQuestions2.add("Aime tu le beurre doux ?");
    listeIntituleQuestions2.add("Aime tu le soleil ?");
    gestionQuestionnaire.ajouterQuestionnaire("Questionnaire2", listeIntituleQuestions2);

    // Verification de l'ajout
    assertTrue(gestionQuestionnaire.getListeQuestionnaire().size() == 2,
        "TestGestionQuestionnaire : Le questionnaire est présent dans la liste");
    // La suppression fonctionne t-elle ?
    assertTrue(gestionQuestionnaire.supprimerQuestionnaire("Questionnaire"),
        "TestGestionQuestionnaire : La suppression d'un questionnaire à échouer anormalement");
    assertTrue(gestionQuestionnaire.getListeQuestionnaire().size() == 1,
        "TestGestionQuestionnaire : Le questionnaire n'a pas été supprimer de la liste");
    // La suppression est t-elle arreté si l'élément n'est pas présent ?
    assertFalse(gestionQuestionnaire.supprimerQuestionnaire("Questionnaire3"),
        "TestGestionQuestionnaire : La suppression d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() == 0,
        "TestGestionQuestionnaire : Le questionnaire a été supprimer de la liste");
    // La suppression est t-elle arreté si l'intitulé est null ?
    assertFalse(gestionQuestionnaire.supprimerQuestionnaire(null),
        "TestGestionQuestionnaire : La suppression d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() == 0,
        "TestGestionQuestionnaire : Le questionnaire a été supprimer de la liste");
 // La suppression est t-elle arreté si l'intitulé est vide ?
    assertFalse(gestionQuestionnaire.supprimerQuestionnaire(""),
        "TestGestionQuestionnaire : La suppression d'un questionnaire à échouer anormalement");
    assertFalse(gestionQuestionnaire.getListeQuestionnaire().size() == 0,
        "TestGestionQuestionnaire : Le questionnaire a été supprimer de la liste");
  }

  @Test
  public void consulterListeQuestion() {
    // La consultation fonctionne t-elle s'il n'y a pas de questionnaire ?
    assertNull(gestionQuestionnaire.consulterListeQuestion("Questionnaire"),
        "TestGestionQuestionnaire : La consultation d'un questionnaire à échouer anormalement");

    List<String> listeIntituleQuestions = new ArrayList<String>();
    listeIntituleQuestions.add("Aime tu le beurre salé ?");
    listeIntituleQuestions.add("Aime tu la pluie ?");
    gestionQuestionnaire.ajouterQuestionnaire("Questionnaire", listeIntituleQuestions);

    // La consultation fonctionne t-elle ?
    Questionnaire questionnaire = gestionQuestionnaire.consulterListeQuestion("Questionnaire");
    assertNotNull(gestionQuestionnaire.consulterListeQuestion("Questionnaire"),
        "TestGestionQuestionnaire : La consultation d'un questionnaire à échouer anormalement");
    List<Question> liste = questionnaire.getListeDeQuestions();
    assertTrue(liste.get(0).getIntituleQuestion() == "Aime tu le beurre salé ?",
        "TestGestionQuestionnaire : La consultation d'un questionnaire à échouer anormalement");
    assertTrue(liste.get(1).getIntituleQuestion() == "Aime tu la pluie ?",
        "TestGestionQuestionnaire : La consultation d'un questionnaire à échouer anormalement");
    // La consultation d'un élément inconnu fonctionne t-elle ?
    assertNull(gestionQuestionnaire.consulterListeQuestion("Questionnaire2"),
        "TestGestionQuestionnaire : La consultation d'un questionnaire à échouer anormalement");
    // La consultation d'un élément null fonctionne t-elle ?
    assertNull(gestionQuestionnaire.consulterListeQuestion(null),
        "TestGestionQuestionnaire : La consultation d'un questionnaire à échouer anormalement");
    // La consultation d'un élément vide fonctionne t-elle ?
    assertNull(gestionQuestionnaire.consulterListeQuestion(""),
        "TestGestionQuestionnaire : La consultation d'un questionnaire à échouer anormalement");
  }

}
