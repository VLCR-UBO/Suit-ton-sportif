package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fonctionnalite.Facade;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.Test;


public class FacadeTest {

  // Les trois fichiers Gestion etant deja verifie nous partons du principe qu'ils fonctionnent
  // individuellement. Ici nous testons que le tout marche bien ensemble.
  @Test
  public void facade() {
    Facade facade = new Facade();

    // Vérification de l'ajout des sportifs
    Calendar dateSportif1 = Calendar.getInstance();
    dateSportif1.set(1976, 7, 3); // annee/mois/jour
    int ajoutSportif1 =
        facade.ajouterUnSportif("Ondra", "Adam", "grinpeurDu57", "leRageux", dateSportif1);
    assertEquals(ajoutSportif1, 1, "FacadeTest : L'ajout d'un sportif à échouer anormalement");

    Calendar dateSportif2 = Calendar.getInstance();
    dateSportif2.set(1978, 10, 26); // annee/mois/jour
    int ajoutSportif2 =
        facade.ajouterUnSportif("Qim", "Jahin", "QimJahin", "theBest", dateSportif2);
    assertEquals(ajoutSportif2, 1, "FacadeTest : L'ajout d'un sportif à échouer anormalement");

    Calendar dateSportif3 = Calendar.getInstance();
    dateSportif3.set(1978, 10, 26); // annee/mois/jour
    int ajoutSportif3 = facade.ajouterUnSportif("Fiet", "Jean", "FietJean", "01234", dateSportif3);
    assertEquals(ajoutSportif3, 1, "FacadeTest : L'ajout d'un sportif à échouer anormalement");

    Calendar dateSportif4 = Calendar.getInstance();
    dateSportif4.set(1978, 10, 26); // annee/mois/jour
    int ajoutSportif4 =
        facade.ajouterUnSportif("Mandela", "Nelson", "MandelaNelson", "azerty", dateSportif4);
    assertEquals(ajoutSportif4, 1, "FacadeTest : L'ajout d'un sportif à échouer anormalement");

    // cas d'erreur
    Calendar dateSportif5 = Calendar.getInstance();
    dateSportif5.set(1978, 10, 26); // annee/mois/jour
    int ajoutSportif5 =
        facade.ajouterUnSportif(null, "Nelson", "MandelaNelson", "azerty", dateSportif5);
    assertEquals(ajoutSportif5, -2, "FacadeTest : L'ajout d'un sportif à échouer anormalement");

    Calendar dateSportif6 = Calendar.getInstance();
    dateSportif6.set(1978, 10, 26); // annee/mois/jour
    int ajoutSportif6 =
        facade.ajouterUnSportif("Mandela", "", "MandelaNelson", "azerty", dateSportif6);
    assertEquals(ajoutSportif6, -2, "FacadeTest : L'ajout d'un sportif à échouer anormalement");

    // Verification de la liste des pseudos
    List<String> listePseudo = facade.obtenirListeSportif();
    assertEquals(listePseudo.size(), 4,
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listePseudo.get(0), "grinpeurDu57",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listePseudo.get(1), "QimJahin",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listePseudo.get(2), "FietJean",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listePseudo.get(3), "MandelaNelson",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // cas d'erreur
    Facade facade2 = new Facade();
    List<String> listePseudo2 = facade2.obtenirListeSportif();
    assertNull(listePseudo2, "FacadeTest : La liste n'est pas conforme");

    // Verification de l'obtention du nom
    assertEquals(facade.obtenirNomSportif("QimJahin"), "Qim",
        "FacadeTest : L'obtention d'un nom à partir du pseudo échoue");

    // cas d'erreur
    assertNull(facade.obtenirNomSportif(null),
        "FacadeTest : L'obtention d'un nom à partir du pseudo échoue");
    assertNull(facade.obtenirNomSportif(""),
        "FacadeTest : L'obtention d'un nom à partir du pseudo échoue");
    assertNull(facade.obtenirNomSportif("test"),
        "FacadeTest : L'obtention d'un nom à partir du pseudo échoue");

    // Verification de l'obtention du prenom
    assertEquals(facade.obtenirPrenomSportif("QimJahin"), "Jahin",
        "FacadeTest : L'obtention d'un prenom à partir du pseudo échoue");

    // cas d'erreur
    assertNull(facade.obtenirPrenomSportif(null),
        "FacadeTest : L'obtention d'un prenom à partir du pseudo échoue");
    assertNull(facade.obtenirPrenomSportif(""),
        "FacadeTest : L'obtention d'un prenom à partir du pseudo échoue");
    assertNull(facade.obtenirPrenomSportif("test"),
        "FacadeTest : L'obtention d'un prenom à partir du pseudo échoue");

    // Verification de l'obtention de la date de naissance
    Calendar dateSportif = Calendar.getInstance();
    dateSportif.set(1978, 10, 26); // annee/mois/jour
    assertTrue(
        facade.obtenirDateDeNaissanceSportif("QimJahin").getTime().toString()
            .equals(dateSportif.getTime().toString()),
        "FacadeTest : L'obtention de la date de naissance partir du pseudo échoue");

    // cas d'erreur
    assertNull(facade.obtenirDateDeNaissanceSportif(null),
        "FacadeTest : L'obtention de la date de naissance à partir du pseudo échoue");
    assertNull(facade.obtenirDateDeNaissanceSportif(""),
        "FacadeTest : L'obtention de la date de naissance à partir du pseudo échoue");
    assertNull(facade.obtenirDateDeNaissanceSportif("test"),
        "FacadeTest : L'obtention de la date de naissance à partir du pseudo échoue");

    // Verification de l'obtention des activités du sportif
    List<String> listeActiviteSportive = facade.consulterLesActivitesDuSportif("QimJahin");
    assertEquals(listeActiviteSportive.size(), 2,
        "FacadeTest : L'obtention de la liste des activités sportives à partir du pseudo échoue");
    assertEquals(listeActiviteSportive.get(0), "BADMINTON",
        "FacadeTest : L'obtention de la liste des activités sportives à partir du pseudo échoue");
    assertEquals(listeActiviteSportive.get(1), "TENNIS",
        "FacadeTest : L'obtention de la liste des activités sportives à partir du pseudo échoue");

    // cas d'erreur
    assertNull(facade.consulterLesActivitesDuSportif(null),
        "FacadeTest : L'obtention de la liste des activités sportives à partir du pseudo échoue");
    assertNull(facade.consulterLesActivitesDuSportif(""),
        "FacadeTest : L'obtention de la liste des activités sportives à partir du pseudo échoue");
    assertNull(facade.consulterLesActivitesDuSportif("test"),
        "FacadeTest : L'obtention de la liste des activités sportives à partir du pseudo échoue");

    // Vérification de la suppression d'un sportif
    assertEquals(facade.supprimerUnSportif("QimJahin"), 1,
        "FacadeTest : La suppression à partir du pseudo échoue");

    // cas d'erreur
    assertEquals(facade.supprimerUnSportif(null), -2,
        "FacadeTest : La suppression à partir du pseudo échoue");
    assertEquals(facade.supprimerUnSportif(""), -2,
        "FacadeTest : La suppression à partir du pseudo échoue");
    assertEquals(facade.supprimerUnSportif("test"), 0,
        "FacadeTest : La suppression à partir du pseudo échoue");

    // Verification du bon dérouler de la suppression précédente
    List<String> listePseudo3 = facade.obtenirListeSportif();
    assertEquals(listePseudo3.size(), 3,
        "FacadeTest : La liste n'est pas conforme à la suppression précédent");
    assertEquals(listePseudo3.get(0), "grinpeurDu57",
        "FacadeTest : La liste n'est pas conforme à la suppression précédent");
    assertNotEquals(listePseudo3.get(1), "QimJahin",
        "FacadeTest : La liste n'est pas conforme à la suppression précédent");
    assertEquals(listePseudo3.get(1), "FietJean",
        "FacadeTest : La liste n'est pas conforme à la suppression précédent");
    assertEquals(listePseudo3.get(2), "MandelaNelson",
        "FacadeTest : La liste n'est pas conforme à la suppression précédent");

    // Vérification de la modification d'un sportif
    Calendar dateSportif7 = Calendar.getInstance();
    dateSportif7.set(1980, 12, 28); // annee/mois/jour
    assertEquals(facade.modifierUnSportif("grinpeurDu57", "nomTest", "prenomTest", "pseudoTest",
        "mdpTest", dateSportif7), 1, "FacadeTest : La modification à partir du pseudo échoue");

    // cas d'erreur
    assertEquals(facade.modifierUnSportif(null, "nomTest", "prenomTest", "pseudoTest", "mdpTest",
        dateSportif7), -3, "FacadeTest : La modification à partir du pseudo échoue");
    assertEquals(facade.modifierUnSportif("grinpeurDu57", "", "prenomTest", "pseudoTest", "mdpTest",
        dateSportif7), -3, "FacadeTest : La modification à partir du pseudo échoue");
    assertEquals(facade.modifierUnSportif("QimJahin", "nomTest", "prenomTest", "pseudoTest",
        "mdpTest", dateSportif7), -2, "FacadeTest : La modification à partir du pseudo échoue");

    // Verification du bon dérouler de la modification précédente
    List<String> listePseudo4 = facade.obtenirListeSportif();
    assertEquals(listePseudo4.size(), 3,
        "FacadeTest : La liste n'est pas conforme à la modification précédent");
    assertEquals(listePseudo4.get(0), "pseudoTest",
        "FacadeTest : La liste n'est pas conforme à la modification précédent");

    assertEquals(facade.obtenirNomSportif("pseudoTest"), "nomTest",
        "FacadeTest : La liste n'est pas conforme à la modification précédent");
    assertEquals(facade.obtenirPrenomSportif("pseudoTest"), "prenomTest",
        "FacadeTest : La liste n'est pas conforme à la modification précédent");
    assertTrue(
        facade.obtenirDateDeNaissanceSportif("pseudoTest").getTime().toString()
            .equals(dateSportif7.getTime().toString()),
        "FacadeTest : La liste n'est pas conforme à la modification précédent");

    // Vérification de l'ajout d'un questionnaire
    List<String> questions = new ArrayList<String>();
    List<String> questions2 = new ArrayList<String>();
    assertEquals(facade.ajouterUnQuestionnaire("monQuestionnaire", questions), 1,
        "FacadeTest : L'ajout d'un questionnaire échoue");
    assertEquals(facade.ajouterUnQuestionnaire("monDeuxiemeQuestionnaire", questions2), 1,
        "FacadeTest : L'ajout d'un questionnaire échoue");

    // Cas d'erreur
    assertEquals(facade.ajouterUnQuestionnaire(null, questions), -2,
        "FacadeTest : L'ajout d'un questionnaire échoue");
    assertEquals(facade.ajouterUnQuestionnaire("", questions), -2,
        "FacadeTest : L'ajout d'un questionnaire échoue");
    assertEquals(facade.ajouterUnQuestionnaire("monQuestionnaire", questions), -1,
        "FacadeTest : L'ajout d'un questionnaire échoue");
    assertEquals(facade.ajouterUnQuestionnaire("monAutreQuestionnaire", null), -2,
        "FacadeTest : L'ajout d'un questionnaire échoue");

    // Verification de la liste des questionnaire
    List<String> listeQuestionnaire = facade.consulterLesQuestionnaire();
    assertEquals(listeQuestionnaire.size(), 2,
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeQuestionnaire.get(0), "monQuestionnaire",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeQuestionnaire.get(1), "monDeuxiemeQuestionnaire",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // Vérification de la suppression d'un questionnaire
    assertEquals(facade.supprimerUnQuestionnaire("monDeuxiemeQuestionnaire"), 1,
        "FacadeTest : La suppression d'un questionnaire échoue");

    // Cas d'erreur
    assertEquals(facade.supprimerUnQuestionnaire(null), -2,
        "FacadeTest : La suppression d'un questionnaire échoue");
    assertEquals(facade.supprimerUnQuestionnaire(""), -2,
        "FacadeTest : La suppression d'un questionnaire échoue");
    assertEquals(facade.supprimerUnQuestionnaire("Test"), -1,
        "FacadeTest : La suppression d'un questionnaire échoue");

    // Verification du bon dérouler de la suppression précédente
    List<String> listeQuestionnaire2 = facade.consulterLesQuestionnaire();
    assertEquals(listeQuestionnaire2.size(), 1,
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeQuestionnaire2.get(0), "monQuestionnaire",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // Vérification de l'ajout d'une question
    assertEquals(facade.ajouterUneQuestion("monQuestionnaire", "Question1"), 1,
        "FacadeTest : L'ajout d'une question à échouer");
    assertEquals(facade.ajouterUneQuestion("monQuestionnaire", "Question2"), 1,
        "FacadeTest : L'ajout d'une question à échouer");

    // Cas d'erreur
    assertEquals(facade.ajouterUneQuestion(null, "Question3"), -3,
        "FacadeTest : L'ajout d'une question à échouer");
    assertEquals(facade.ajouterUneQuestion("", "Question4"), -3,
        "FacadeTest : L'ajout d'une question à échouer");
    assertEquals(facade.ajouterUneQuestion("Test", "Question5"), -2,
        "FacadeTest : L'ajout d'une question à échouer");
    assertEquals(facade.ajouterUneQuestion("monQuestionnaire", ""), -3,
        "FacadeTest : L'ajout d'une question à échouer");
    assertEquals(facade.ajouterUneQuestion("monQuestionnaire", null), -3,
        "FacadeTest : L'ajout d'une question à échouer");

    // Vérification de la liste des questions du questionnaire
    List<String> listeDesQuestions = facade.consulterLesQuestionDuQuestionnaire("monQuestionnaire");
    assertEquals(listeDesQuestions.size(), 2,
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeDesQuestions.get(0), "Question1",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeDesQuestions.get(1), "Question2",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // cas d'erreur
    assertNull(facade.consulterLesQuestionDuQuestionnaire(""),
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertNull(facade.consulterLesQuestionDuQuestionnaire(null),
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertNull(facade.consulterLesQuestionDuQuestionnaire("Test"),
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // Vérification de la suppression d'une question
    assertEquals(facade.supprimerUneQuestion("monQuestionnaire", "Question1"), 1,
        "FacadeTest : La suppression d'une question à échouer");

    // cas d'erreur
    assertEquals(facade.supprimerUneQuestion(null, "Question2"), -3,
        "FacadeTest : La suppression d'une question à échouer");
    assertEquals(facade.supprimerUneQuestion("", "Question2"), -3,
        "FacadeTest : La suppression d'une question à échouer");
    assertEquals(facade.supprimerUneQuestion("Test", "Question2"), -2,
        "FacadeTest : La suppression d'une question à échouer");
    assertEquals(facade.supprimerUneQuestion("monQuestionnaire", null), -3,
        "FacadeTest : La suppression d'une question à échouer");
    assertEquals(facade.supprimerUneQuestion("monQuestionnaire", ""), -3,
        "FacadeTest : La suppression d'une question à échouer");
    assertEquals(facade.supprimerUneQuestion("monQuestionnaire", "Question3"), 0,
        "FacadeTest : La suppression d'une question à échouer");

    // Vérification du bon dérouler de la suppression précédente
    List<String> listeDesQuestions2 =
        facade.consulterLesQuestionDuQuestionnaire("monQuestionnaire");
    assertEquals(listeDesQuestions2.size(), 1,
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeDesQuestions2.get(0), "Question2",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // Vérification de la modification d'une question
    assertEquals(facade.modifierUneQuestion("monQuestionnaire", "Question2", "Question1", true), 1,
        "FacadeTest : La modification d'une question à échouer");

    // Cas d'erreur
    assertEquals(facade.modifierUneQuestion("monQuestionnaire", "QuestionTest", "Question3", true),
        0, "FacadeTest : La modification d'une question à échouer");
    assertEquals(facade.modifierUneQuestion("monQuestionnaire", "", "Question3", true), -3,
        "FacadeTest : La modification d'une question à échouer");
    assertEquals(facade.modifierUneQuestion("monQuestionnaire", null, "Question3", true), -3,
        "FacadeTest : La modification d'une question à échouer");
    assertEquals(facade.modifierUneQuestion("", "QuestionTest", "Question3", true), -3,
        "FacadeTest : La modification d'une question à échouer");
    assertEquals(facade.modifierUneQuestion(null, "QuestionTest", "Question3", true), -3,
        "FacadeTest : La modification d'une question à échouer");
    assertEquals(
        facade.modifierUneQuestion("monQuestionnaire345", "QuestionTest", "Question3", true), -2,
        "FacadeTest : La modification d'une question à échouer");

    // Vérification du bon dérouler de la modification précédente
    List<String> listeDesQuestions3 =
        facade.consulterLesQuestionDuQuestionnaire("monQuestionnaire");
    assertEquals(listeDesQuestions3.size(), 1,
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeDesQuestions3.get(0), "Question1",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // Vérification de modifier un questionnaire
    Facade facade3 = new Facade();
    List<String> listeDesQuestions4 = new ArrayList<String>();
    facade3.ajouterUnQuestionnaire("azerty", listeDesQuestions4);
    facade3.ajouterUneQuestion("azerty", "Question1");
    facade3.ajouterUneQuestion("azerty", "Question2");
    facade3.ajouterUneQuestion("azerty", "Question3");
    listeDesQuestions4 = facade3.consulterLesQuestionDuQuestionnaire("azerty");

    assertEquals(facade.modifierUnQuestionnaire("monQuestionnaire", "MonNouveauQuestionnaire",
        listeDesQuestions4), 1, "FacadeTest : La modification d'une question à échouer");

    // Cas d'erreur
    assertEquals(facade.modifierUnQuestionnaire("", "MonNouveauQuestionnaire", listeDesQuestions4),
        -2, "FacadeTest : La modification d'une question à échouer");
    assertEquals(
        facade.modifierUnQuestionnaire(null, "MonNouveauQuestionnaire", listeDesQuestions4), -2,
        "FacadeTest : La modification d'une question à échouer");
    assertEquals(facade.modifierUnQuestionnaire("unFauxQuestionnaire", "MonNouveauQuestionnaire",
        listeDesQuestions4), -1, "FacadeTest : La modification d'une question à échouer");
    assertEquals(facade.modifierUnQuestionnaire("unFauxQuestionnaire", "", listeDesQuestions4), -2,
        "FacadeTest : La modification d'une question à échouer");
    assertEquals(facade.modifierUnQuestionnaire("monQuestionnaire", null, listeDesQuestions4), -2,
        "FacadeTest : La modification d'une question à échouer");
    assertEquals(
        facade.modifierUnQuestionnaire("monQuestionnaire", "MonNouveauQuestionnaire", null), -2,
        "FacadeTest : La modification d'une question à échouer");

    // Vérification de la modification précédente
    List<String> listeDesQuestions5 =
        facade.consulterLesQuestionDuQuestionnaire("MonNouveauQuestionnaire");
    assertEquals(listeDesQuestions5.size(), 3,
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeDesQuestions5.get(0), "Question1",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeDesQuestions5.get(1), "Question2",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");
    assertEquals(listeDesQuestions5.get(2), "Question3",
        "FacadeTest : La liste n'est pas conforme aux ajouts précédent");

    // Vérification de la méthode obtenirQuestionnaireEtReponses (pas testable dans ce sprint)

    // HashMap<String, Integer> map = facade.obtenirQuestionnaireEtReponses(null, "MandelaNelson",
    // "MonNouveauQuestionnaire");

  }
}
