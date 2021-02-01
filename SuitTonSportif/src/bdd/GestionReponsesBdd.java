package bdd;

import fonctionnalite.GestionReponses;
import fonctionnalite.Question;
import fonctionnalite.Questionnaire;
import fonctionnalite.Sportif;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * La classe gestionReponsesBdd permet l'ajout, la modification, et la suppression des reponses dans
 * la base de données. Elle contient également une méthode load pour charger les données dans
 * l'application.
 * 
 * @author ychan
 *
 */
public class GestionReponsesBdd {
  private Connection connection;
  private Statement sqlStatement;

  /**
   * Constructeur de la classe GestionReponsesBdd. Il initialise la connexion avec notre base de
   * données.
   */
  public GestionReponsesBdd() {
    try {
      String url = "jdbc:mysql://localhost/enregistretonsportif";
      String user = "root";
      String passwd = "motdepasse";

      connection = DriverManager.getConnection(url, user, passwd);
      sqlStatement =
          connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

      System.out.println("Connexion OK");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean ajouterReponses(GestionReponses gestionReponses, Date date, Sportif unSportif,
      String pseudo, Questionnaire unQuestionnaire, List<Integer> listeReponses) {
    if (date == null || unSportif == null || pseudo == null || pseudo.length() < 1
        || unQuestionnaire == null || listeReponses == null) {
      return false; // parametres incorrects
    }
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    Integer numeroSemaine = calendar.get(Calendar.WEEK_OF_YEAR);
    int taille = gestionReponses.getListeDesReponses().size();
    for (int i = 0; i < taille; i++) {
      if (gestionReponses.getListeDesReponses().get(i).getUnSportif().equals(unSportif)
          && gestionReponses.getListeDesReponses().get(i).getUnQuestionnaire()
              .equals(unQuestionnaire)
          && gestionReponses.getListeDesReponses().get(i).getNumeroSemaine() == numeroSemaine) {
        return false; // deja present
      }
    }

    java.util.Date utilDate = date;
    java.sql.Date dateBdd = new java.sql.Date(utilDate.getTime());

    // On insère toute les réponses
    int taille2 = listeReponses.size();
    for (int i = 0; i < taille2; i++) {
      String query =
          "INSERT INTO reponse (numeroSemaine, derniereModification, valeurReponse, unSportif)"
              + " VALUES (" + numeroSemaine + "," + dateBdd + "," + listeReponses.get(i) + ",'"
              + pseudo + "')";
      try {
        sqlStatement.executeUpdate(query);
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    }

    // mise à jour des questions
    // A FAIRE

    return true;
  }

  public boolean modifierReponses(Integer numeroSemaine, Date date, Sportif unSportif,
      Questionnaire unQuestionnaire, List<Integer> listeReponses) {
    if (numeroSemaine == null || numeroSemaine < 1 || date == null || unSportif == null
        || unQuestionnaire == null || listeReponses == null) {
      return false; // parametres incorrects
    }
    java.util.Date utilDate = date;
    java.sql.Date dateBdd = new java.sql.Date(utilDate.getTime());

    int taille2 = listeReponses.size();
    for (int i = 0; i < taille2; i++) {
      String query = "UPDATE reponse SET derniereModification = DATE('" + dateBdd
          + "'), valeurReponse = " + listeReponses.get(i);
      try {
        sqlStatement.executeUpdate(query);
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  public boolean load(GestionReponses gestion) {
    return true;
  }

}
