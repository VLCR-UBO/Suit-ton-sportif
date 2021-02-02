package bdd;

import fonctionnalite.GestionQuestionnaire;
import fonctionnalite.GestionReponses;
import fonctionnalite.GestionSportif;
import fonctionnalite.Questionnaire;
import fonctionnalite.Sportif;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    //System.out.println(unSportif.getPseudo());
    //System.out.println(unQuestionnaire.getNomQuestionnaire());
    //System.out.println(numeroSemaine);
    //System.out.println(taille);
    for (int i = 0; i < taille; i++) {
      
      if (gestionReponses.getListeDesReponses().get(i).getUnSportif().equals(unSportif)
          && gestionReponses.getListeDesReponses().get(i).getUnQuestionnaire()
              .equals(unQuestionnaire)
          && gestionReponses.getListeDesReponses().get(i).getNumeroSemaine() == numeroSemaine) {
        return false; // deja present
      }
    }
    System.out.println("je suis passez connard !");
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
  public boolean load(GestionReponses gestionReponses, GestionSportif gestionSportif,
      GestionQuestionnaire gestionQuestionnaire) {
    try {
      ResultSet lesReponses = this.sqlStatement.executeQuery(
          ("SELECT DISTINCT reponse.*, question.unQuestionnaire FROM reponse, question"));

      // initialisation des composantes nécessaire
      List<String> intituleQuestionnaire = new ArrayList<String>();
      List<String> pseudoSportif = new ArrayList<String>();
      List<Date> date = new ArrayList<Date>();
      List<Integer> valeur = new ArrayList<Integer>();

      // collecte des éléments (on rempli nos listes)
      while (lesReponses.next()) {
        intituleQuestionnaire.add(lesReponses.getString("unQuestionnaire"));
        pseudoSportif.add(lesReponses.getString("unSportif"));
        date.add(lesReponses.getDate("derniereModification"));
        valeur.add(lesReponses.getInt("valeurReponse"));
      }

      // traitement des listes pour les ajouts des réponses
      // On initialise les composants nécessaire
      List<Integer> reponses = new ArrayList<Integer>();
      String questionnaire;
      String pseudo;
      Date d;
      Sportif unSportif;
      Questionnaire unQuestionnaire;
      Calendar calendar;
      Calendar calendar2;
      Integer numeroSemaine;
      Integer numeroSemaine2;
      // tant qu'il reste un élément dans la liste, toute les reponses n'ont pas été crée
      //System.out.println(valeur.size());
      while (valeur.size() > 0) {
        // On prend tout les informations du premier élément
        questionnaire = intituleQuestionnaire.get(0);
        pseudo = pseudoSportif.get(0);
        d = date.get(0);
        calendar = new GregorianCalendar();
        calendar.setTime(d);
        numeroSemaine = calendar.get(Calendar.WEEK_OF_YEAR);
        //System.out.println("aa");
        reponses.add(valeur.get(0)); // On ajoute l'élément dans la liste des éléments à ajouté
        valeur.remove(0); // on supprimer l'élément qu'on va ajouté
        // On cherche si d'autre élément sont conforme au premier élément
        for (int i = 0; i < valeur.size(); i++) {
          calendar2 = new GregorianCalendar();
          calendar2.setTime(date.get(i));
          numeroSemaine2 = calendar2.get(Calendar.WEEK_OF_YEAR);
          if (intituleQuestionnaire.get(i) == questionnaire && pseudoSportif.get(0) == pseudo
              && numeroSemaine == numeroSemaine2) {
            // Il est conforme, on l'ajoute dans la liste des éléments à ajouté
            //System.out.println("bb");
            reponses.add(valeur.get(i));
            valeur.remove(i); // on supprimer l'élément qu'on va ajouté
          }
        }
        unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(questionnaire);
        unSportif = gestionSportif.consulterSportif(pseudo);
        //System.out.println("go");
        //System.out.println(reponses);
        //System.out.println(d);
        //System.out.println(unSportif);
        //System.out.println(unQuestionnaire);
        //System.out.println(reponses);
        reponses.add(2);
        boolean ret = gestionReponses.ajouterReponses(d, unSportif, unQuestionnaire, reponses); // on fait l'ajout
        List<Integer> a = gestionReponses.consulterReponses(numeroSemaine, unSportif, unQuestionnaire);
        System.out.println(ret);
        System.out.println(a);
        if (!ret) {
          return false; // Une erreur c'est produite lors de la création
        }
        reponses.clear(); // On réinitialise la liste ajouté pour le prochain tour de boucle
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

}
