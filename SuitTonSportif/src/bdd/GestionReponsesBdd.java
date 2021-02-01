package bdd;

import fonctionnalite.GestionReponses;
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
import java.util.Map;


/**
 * La classe gestionReponsesBdd permet l'ajout, et la modification des reponses dans la base de
 * données. Elle contient également une méthode load pour charger les données dans l'application.
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

  /**
   * Cette méthode permet d'ajouter une liste de reponses dans la base de données. L'ajout aura lieu
   * si et seulement si les paramètres sont correct et si l'élément n'est pas déjà présent (même
   * sportif, questionnaire, et semaine). Une valeur boolean sera retournée : true si la reponses
   * est ajouté, false sinon.
   * 
   * @param gestionReponses : Permet d'accéder aux éléments déjà présents dans l'application.
   * @param date : Il s'agit de la date de la dernière modification de la liste de reponses.
   * @param listeReponses : Contient les questions, et respectivement leurs réponses a ajouté.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent � ce questionnaire.
   * @return Retourne true si la reponses est ajoutée, false sinon.
   */
  public boolean ajouterReponses(GestionReponses gestionReponses, Date date, Sportif unSportif,
      String pseudo, Questionnaire unQuestionnaire, Map<String, Integer> listeReponses) {
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

    for (Map.Entry<String, Integer> map : listeReponses.entrySet()) {
      String query =
          "INSERT INTO reponse (numeroSemaine, derniereModification, valeurReponse, unSportif, "
              + "uneQuestion)" + " VALUES (" + numeroSemaine + ",DATE('" + dateBdd + "'),"
              + map.getValue() + ",'" + pseudo + "','" + map.getKey() + "')";
      try {
        sqlStatement.executeUpdate(query);
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /**
   * Cette méthode permet de modifier une listes de reponses de la base de données (la date sera
   * mise a jour également), Celui-ci est identifié avec les paramètres unSportif, unQuestionnaire,
   * et le numéro de semaine.
   * 
   * @param date : Il s'agit de la date de la dernière modification de la liste de reponses.
   * @param numeroSemaine : Contient le numéro de la semaine ou l'élément est crée.
   * @param listeReponses : Contient les questions, et respectivement leurs réponses a ajouté.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return true si tout ce pass bien, false sinon.
   */
  public boolean modifierReponses(Integer numeroSemaine, Date date, Sportif unSportif,
      Questionnaire unQuestionnaire, Map<String, Integer> listeReponses) {
    if (numeroSemaine == null || numeroSemaine < 1 || date == null || unSportif == null
        || unQuestionnaire == null || listeReponses == null) {
      return false; // parametres incorrects
    }
    java.util.Date utilDate = date;
    java.sql.Date dateBdd = new java.sql.Date(utilDate.getTime());

    for (Map.Entry<String, Integer> map : listeReponses.entrySet()) {
      String query = "UPDATE reponse SET derniereModification = DATE('" + dateBdd
          + "'), valeurReponse = " + map.getValue();
      try {
        sqlStatement.executeUpdate(query);
      } catch (SQLException e) {
        e.printStackTrace();
        return false;
      }
    }
    return true;
  }

  /**
   * Cette méthode permet de mettre à jour les données de l'application, avec celle de la base de
   * données.
   * 
   * @param gestionReponses : Permet de mettre à jour les éléments de l'application.
   * @return true si tout c'est bien passé, false sinon.
   */
  public boolean load(GestionReponses gestionReponses) {
    return true;
  }

}
