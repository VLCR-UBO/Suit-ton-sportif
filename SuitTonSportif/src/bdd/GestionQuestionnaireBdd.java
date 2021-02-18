package bdd;

import fonctionnalite.GestionQuestionnaire;
import fonctionnalite.Question;
import fonctionnalite.QuestionBoolenne;
import fonctionnalite.Questionnaire;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les appel à la base de données pour la partie questionnaire.
 * 
 * @author Chiara
 *
 */
public class GestionQuestionnaireBdd {
  private GestionBdd gestionBdd;

  /**
   * Etablie une connexion à la base de données.
   */
  public GestionQuestionnaireBdd() {
    gestionBdd = new GestionBdd();
  }

  /**
   * Charge les questionnaires et les questions qui leurs sont associer dans le gestionnaire des
   * questionnaire.
   * 
   * @param gestionQuestionnaire : Gestionnaire des questionnaire
   * @return : true si c'est reussi false sinon
   */
  public boolean load(GestionQuestionnaire gestionQuestionnaire) {
    List<String> lesNomsDesQuestionnaires = new ArrayList<String>();
    List<String> lesNomsDesQuestions = new ArrayList<String>();

    try {
      String query = "SELECT * FROM QUESTIONNAIRE";
      ResultSet lesQuestionnaires = gestionBdd.executerRequeteAvecReponse(query);

      while (lesQuestionnaires.next()) {
        lesNomsDesQuestionnaires.add(lesQuestionnaires.getString("intituleQuestionnaire"));
      }

      for (String nom : lesNomsDesQuestionnaires) {
        // récuperer les questions
        query = "SELECT * FROM QUESTION WHERE unQuestionnaire = '" + nom + "'";
        ResultSet lesQuestions = gestionBdd.executerRequeteAvecReponse(query);

        // creer le questionnaire vide
        gestionQuestionnaire.getListeQuestionnaire()
            .add(new Questionnaire(nom, new ArrayList<Question>()));
        // ajouter chaque question avec leur valeur par defaut
        Questionnaire questionnaire = gestionQuestionnaire.consulterListeQuestion(nom);
        while (lesQuestions.next()) {
          int defaut = Integer.parseInt(lesQuestions.getString("reponseparDefaut"));
          questionnaire.getListeDeQuestions().add(new QuestionBoolenne(
              lesQuestions.getString("intituleQuestion"), defaut == 0 ? false : true));
        }
        lesNomsDesQuestions.clear();
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * creer un quetionnaire et ses questions dans la BDD.
   * 
   * @param nomQuestionnaire : nom du questionnaire à ajouter
   * @param questions : list des noms des questions à ajouter
   * @return : true si c'est reussi false sinon
   */
  public boolean ajouterQuestionnaire(String nomQuestionnaire, List<String> questions) {
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0 && questions != null) {

      try {
        String query =
            "INSERT INTO QUESTIONNAIRE (intituleQuestionnaire) VALUES ('" + nomQuestionnaire + "')";
        gestionBdd.executerRequete(query);

        for (String intitule : questions) {
          String queryQuestion =
              "INSERT INTO QUESTION (intituleQuestion, reponseParDefaut, unQuestionnaire) VALUES ('"
                  + intitule + "', 0 , '" + nomQuestionnaire + "')";
          gestionBdd.executerRequete(queryQuestion);
        }
      } catch (SQLException e) {
        return false;
      }
      return true;
    }

    return false;
  }

  /**
   * Supprime un questionnaire et toutes ses questions de la BDD.
   * 
   * @param nomQuestionnaire : nom du questioinnaire à supprimer
   * @return : true si c'est reussi false sinon
   */
  public boolean supprimerQuestionnaire(String nomQuestionnaire) {
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0) {
      try {
        String queryQuestions =
            "DELETE FROM QUESTION WHERE unQuestionnaire = '" + nomQuestionnaire + "'";
        gestionBdd.executerRequete(queryQuestions);

        String queryQuestionnaire =
            "DELETE FROM QUESTIONNAIRE WHERE intituleQuestionnaire = '" + nomQuestionnaire + "'";
        gestionBdd.executerRequete(queryQuestionnaire);
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Modifie le nom d'un questionnaire dans la BDD.
   * 
   * @param ancienNomQuestionnaire : nom du questionnaire avant la modification
   * @param nouveauNomQuestionnaire : nouveau nom du questionnaire
   * @return : true si c'est reussi false sinon
   */
  public boolean modifierQuestionnaire(String ancienNomQuestionnaire,
      String nouveauNomQuestionnaire) {
    if (ancienNomQuestionnaire != null && ancienNomQuestionnaire.length() > 0
        && nouveauNomQuestionnaire != null && nouveauNomQuestionnaire.length() > 0) {
      try {
        String queryQuestionnaire =
            "UPDATE QUESTIONNAIRE SET intituleQuestionnaire = '" + nouveauNomQuestionnaire
                + "' WHERE intituleQuestionnaire = '" + ancienNomQuestionnaire + "'";
        gestionBdd.executerRequete(queryQuestionnaire);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Cette méthode permet d'ajouter une question dans la base de données.
   * 
   * @param nomQuestionnaire : Chaine de caractères non nulle et non vide, permettant
   *        l'identification unique d'un questionnaire.
   * @param nomQuestion : Chaine de caractères non nulle et non vide, permettant l'identification
   *        unique d'un question.
   * @param reponseParDefaut : Valeur de la question passez en paramètre.
   * @return true si l'ajout de la question est un succès, false sinon.
   */
  public boolean ajouterQuestion(String nomQuestionnaire, String nomQuestion,
      boolean reponseParDefaut) {
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0 && nomQuestion != null
        && nomQuestion.length() > 0) {
      try {
        String queryQuestion =
            "INSERT INTO QUESTION (intituleQuestion, reponseParDefaut, unQuestionnaire) VALUES ('"
                + nomQuestion + "', " + (reponseParDefaut == true ? 1 : 0) + " , '"
                + nomQuestionnaire + "')";
        gestionBdd.executerRequete(queryQuestion);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Cette méthode permet de modifier une question dans la base de données.
   * 
   * @param ancienNomQuestion : Chaine de caractères non nulle et non vide, permettant
   *        l'identification de l'élément à modifier.
   * @param nouveauNomQuestion : Chaine de caractères non nulle et non vide, qui représente le
   *        nouveau nom unique de notre question.
   * @param defaut : La valeur par défault que doit prendre notre question.
   * @return true si la modification c'est bien passé, false sinon.
   */
  public boolean modifierQuestion(String ancienNomQuestion, String nouveauNomQuestion,
      boolean defaut) {
    if (ancienNomQuestion != null && ancienNomQuestion.length() > 0 && nouveauNomQuestion != null
        && nouveauNomQuestion.length() > 0) {
      try {
        int valeurDefaut = 0;
        if (defaut) {
          valeurDefaut = 1;
        }
        String queryQuestion = "UPDATE QUESTION SET intituleQuestion = '" + nouveauNomQuestion
            + "', reponseParDefaut = " + valeurDefaut + " WHERE intituleQuestion = '"
            + ancienNomQuestion + "'";
        gestionBdd.executerRequete(queryQuestion);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Cette méthode permet de supprimer une question dans la base de données.
   * 
   * @param intituleQuestion : Chaine de caractères non nulle et non vide, permettant
   *        l'identification unique d'un question.
   * @return true si la suppression c'est bien passé, false sinon.
   */
  public boolean supprimerQuestion(String intituleQuestion) {
    if (intituleQuestion != null && intituleQuestion.length() > 0) {
      try {
        String queryQuestions =
            "DELETE FROM QUESTION WHERE intituleQuestion = '" + intituleQuestion + "'";
        gestionBdd.executerRequete(queryQuestions);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }
}
