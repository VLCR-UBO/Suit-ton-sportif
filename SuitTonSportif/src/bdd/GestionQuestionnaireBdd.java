package bdd;

import fonctionnalite.GestionQuestionnaire;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
   * @return
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
        ResultSet lesQuestions = this.sqlStatement
            .executeQuery(("SELECT * FROM question WHERE unQuestionnaire = " + nom));

        while (lesQuestions.next()) {
          lesNomsDesQuestions.add(lesQuestionnaires.getString("unQuestionnaire"));
        }

        gestion.ajouterQuestionnaire(nom, lesNomsDesQuestions);

        lesNomsDesQuestions.clear();
      }
    } catch (Exception e) {
      return false;
    }

    return true;
  }
}
