package bdd;

import fonctionnalite.GestionQuestionnaire;
import fonctionnalite.Questionnaire;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les appel à la base de données pour la partie questionnaire.
 * 
 * @author Chiara
 *
 */
public class GestionQuestionnaireBdd {
  private Connection connection;
  private Statement sqlStatement;

  /**
   * Etablie une connexion à la base de données.
   */
  public GestionQuestionnaireBdd() {
    try {
      String url = "jdbc:mysql://localhost/enregistretonsportif";
      String user = "root";
      String passwd = "motdepasse";

      connection = DriverManager.getConnection(url, user, passwd);
      sqlStatement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Charge les questionnaires et les questions qui leurs sont associer dans le gestionnaire des
   * questionnaire.
   * 
   * @param gestion : Gestionnaire des questionnaire
   * @return : true si c'est reussi false sinon
   */
  public boolean load(GestionQuestionnaire gestion) {
    List<String> lesNomsDesQuestionnaires = new ArrayList<String>();
    List<String> lesNomsDesQuestions = new ArrayList<String>();

    try {
      ResultSet lesQuestionnaires = this.sqlStatement.executeQuery(("SELECT * FROM questionnaire"));

      while (lesQuestionnaires.next()) {
        lesNomsDesQuestionnaires.add(lesQuestionnaires.getString("intituleQuestionnaire"));
      }

      for (String nom : lesNomsDesQuestionnaires) {
        // récuperer les questions
        ResultSet lesQuestions = this.sqlStatement
            .executeQuery(("SELECT * FROM question WHERE unQuestionnaire = " + nom));

        // creer le questionnaire vide
        gestion.ajouterQuestionnaire(nom, new ArrayList<String>());

        // ajouter chaque question avec leur valeur par defaut
        Questionnaire questionnaire = gestion.consulterListeQuestion(nom);
        while (lesQuestions.next()) {
          int defaut = Integer.parseInt(lesQuestions.getString("reponseparDefaut"));
          questionnaire.ajouterQuestionBoolenne(lesQuestions.getString("intituleQuestion"),
              defaut == 0 ? false : true);
        }

        lesNomsDesQuestions.clear();
      }
    } catch (Exception e) {
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
            "INSERT INTO questionnaire (intituleQuestionnaire) VALUES ('" + nomQuestionnaire + "')";
        sqlStatement.executeUpdate(query);

        for (String intitule : questions) {
          String queryQuestion =
              "INSERT INTO question (intituleQuestion, reponseParDefaut, unQuestionnaire) VALUES ('"
                  + intitule + "', 0 , '" + nomQuestionnaire + "')";
          sqlStatement.executeUpdate(queryQuestion);
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
            "DELETE FROM question WHERE unQuestionnaire = " + nomQuestionnaire;
        sqlStatement.executeUpdate(queryQuestions);
        
        String queryQuestionnaire =
            "DELETE FROM questionnaire WHERE intituleQuestionnaire = " + nomQuestionnaire;
        sqlStatement.executeUpdate(queryQuestionnaire);
      } catch (SQLException e) {
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
            "UPDATE questionnaire SET intituleQuestionnaire = '" + nouveauNomQuestionnaire
                + "' WHERE intituleQuestionnaire = '" + ancienNomQuestionnaire + "'";
        sqlStatement.executeUpdate(queryQuestionnaire);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * 
   * 
   * @param nomQuestionnaire
   * @param nomQuestion
   * @param reponseParDefaut
   * @return
   */
  public boolean ajouterQuestion(String nomQuestionnaire, String nomQuestion,
      boolean reponseParDefaut) {
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0 && nomQuestion != null
        && nomQuestion.length() > 0) {
      try {
        String queryQuestion =
            "INSERT INTO question (intituleQuestion, reponseParDefaut, unQuestionnaire) VALUES ('"
                + nomQuestion + "', " + (reponseParDefaut == true ? 1 : 0) + " , '"
                + nomQuestionnaire + "')";
        sqlStatement.executeUpdate(queryQuestion);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  public boolean modifierQuestion(String ancienNomQuestion, String nouveauNomQuestion) {
    if (ancienNomQuestion != null && ancienNomQuestion.length() > 0 && nouveauNomQuestion != null
        && nouveauNomQuestion.length() > 0) {
      try {
        String queryQuestion = "UPDATE question SET intituleQuestion = '" + nouveauNomQuestion
            + "' WHERE unQuestionnaire = '" + ancienNomQuestion;
        sqlStatement.executeUpdate(queryQuestion);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }
  
  public boolean supprimerQuestion(String intituleQuestion) {
    if (intituleQuestion != null && intituleQuestion.length() > 0) {
      try {
        String queryQuestions =
            "DELETE FROM question WHERE intituleQuestion = " + intituleQuestion;
        sqlStatement.executeUpdate(queryQuestions);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }
}
