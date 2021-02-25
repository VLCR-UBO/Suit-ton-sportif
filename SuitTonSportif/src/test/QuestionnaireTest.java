package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import fonctionnalite.Facade;
import fonctionnalite.Question;
import fonctionnalite.QuestionBoolenne;
import fonctionnalite.Questionnaire;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestionnaireTest {
  private Questionnaire questionnaire;

  @BeforeEach
  public void initialiser() {
    new Facade(true); // pour effacer des données qui pourrait altérer le résultat des tests
    QuestionBoolenne uneQuestion = new QuestionBoolenne("intitule", true);
    List<Question> listeDeQuestion = new ArrayList<Question>();
    listeDeQuestion.add(uneQuestion);
    questionnaire = new Questionnaire("Test", listeDeQuestion);
  }

  @AfterEach
  public void nettoyer() {
    questionnaire = null;
  }

  @Test
  public void ajouterQuestionBoolenne() {
    // Le fonctionnement normal fonctionne ?
    assertTrue(questionnaire.ajouterQuestionBoolenne("Qui suis je ?", true),
        "QuestionnaireTest : L'ajout d'une question boolenne à échouer anormalement");
    assertTrue(questionnaire.getListeDeQuestions().size() == 2,
        "QuestionnaireTest : La question boolenne n'a pas été ajouté à la liste");
    // Le cas de doublons est t-il gérer ?
    assertFalse(questionnaire.ajouterQuestionBoolenne("Qui suis je ?", false),
        "QuestionnaireTest : L'ajout d'une question boolenne à échouer anormalement");
    assertTrue(questionnaire.getListeDeQuestions().size() == 2,
        "QuestionnaireTest : La question boolenne a été ajouté à la liste");
    // L'ajout est t-il arreté si l'intitulé est null ?
    assertFalse(questionnaire.ajouterQuestionBoolenne(null, false),
        "QuestionnaireTest : L'ajout d'une question boolenne à échouer anormalement");
    assertTrue(questionnaire.getListeDeQuestions().size() == 2,
        "QuestionnaireTest : La question boolenne a été ajouté à la liste");
    // L'ajout est t-il arreté si l'intitulé est une chaine vide ?
    assertFalse(questionnaire.ajouterQuestionBoolenne("", true),
        "QuestionnaireTest : L'ajout d'une question boolenne à échouer anormalement");
    assertTrue(questionnaire.getListeDeQuestions().size() == 2,
        "QuestionnaireTest : La question boolenne a été ajouté à la liste");
  }

  @Test
  public void modifierQuestionBoolenne() {
    questionnaire.ajouterQuestionBoolenne("Qui suis je ?", false);
    // Le fonctionnement normal fonctionne ?
    assertTrue(questionnaire.modifierQuestionBoolenne("Qui suis je ?", "Qui est tu ?", true),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    assertTrue(
        questionnaire.getListeDeQuestions().get(1).getIntituleQuestion().equals("Qui est tu ?"),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    QuestionBoolenne question = (QuestionBoolenne) questionnaire.getListeDeQuestions().get(1);
    assertTrue(question.getReponseQuestion(),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    // La modification est t-elle arreté si le nom de la question est inconnu ?
    assertFalse(questionnaire.modifierQuestionBoolenne("Qui est la ?", "Qui etes vous ?", true),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    assertFalse(
        questionnaire.getListeDeQuestions().get(1).getIntituleQuestion().equals("Qui etes vous ?"),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    // La modification est t-elle arreté si le nom de la question est null ?
    assertFalse(questionnaire.modifierQuestionBoolenne(null, "Qui etes vous ?", true),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    assertFalse(
        questionnaire.getListeDeQuestions().get(1).getIntituleQuestion().equals("Qui etes vous ?"),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    // La modification est t-elle arreté si le nom de la question est vide ?
    assertFalse(questionnaire.modifierQuestionBoolenne("", "Qui etes vous ?", true),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    assertFalse(
        questionnaire.getListeDeQuestions().get(1).getIntituleQuestion().equals("Qui etes vous ?"),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    // La modification est t-elle arreté si le nouveau nom de la question est null ?
    assertFalse(questionnaire.modifierQuestionBoolenne("Qui est tu ?", null, true),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    assertFalse(questionnaire.getListeDeQuestions().get(1).getIntituleQuestion().equals(null),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    // La modification est t-elle arreté si le nouveau nom de la question est vide ?
    assertFalse(questionnaire.modifierQuestionBoolenne("Qui est tu ?", "", true),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
    assertFalse(questionnaire.getListeDeQuestions().get(1).getIntituleQuestion().equals(""),
        "QuestionnaireTest : La modification d'une question boolenne à échouer anormalement");
  }

  @Test
  public void supprimerQuestion() {
    questionnaire = new Questionnaire("Test");
    // La suppression fonctionne t-elle s'il n'y a pas de question ?
    assertFalse(questionnaire.supprimerQuestion("test"),
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");

    questionnaire.ajouterQuestionBoolenne("Qui suis je ?", true);
    // Le fonctionnement normal fonctionne ?
    assertTrue(questionnaire.supprimerQuestion("Qui suis je ?"),
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");
    assertTrue(questionnaire.getListeDeQuestions().size() == 0,
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");

    questionnaire.ajouterQuestionBoolenne("Qui est la ?", false);
    // La suppression est t-elle arreté si l'élément n'est pas présent ?
    assertFalse(questionnaire.supprimerQuestion("Qui suis je ?"),
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");
    assertFalse(questionnaire.getListeDeQuestions().size() == 0,
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");
    // La suppression est t-elle arreté si l'intitulé est null ?
    assertFalse(questionnaire.supprimerQuestion(null),
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");
    assertFalse(questionnaire.getListeDeQuestions().size() == 0,
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");
    // La suppression est t-elle arreté si l'intitulé est vide ?
    assertFalse(questionnaire.supprimerQuestion(""),
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");
    assertFalse(questionnaire.getListeDeQuestions().size() == 0,
        "QuestionnaireTest : La suppression d'un questionnaire à échouer anormalement");
  }

}
