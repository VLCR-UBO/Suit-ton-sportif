package bdd;

import fonctionnalite.GestionQuestionnaire;
import fonctionnalite.GestionReponses;
import fonctionnalite.GestionSportif;
import fonctionnalite.Questionnaire;
import fonctionnalite.Reponses;
import fonctionnalite.Sportif;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
  private GestionBdd gestionBdd;

  /**
   * Constructeur de la classe GestionReponsesBdd. Il initialise la connexion avec notre base de
   * données.
   */
  public GestionReponsesBdd() {
    gestionBdd = new GestionBdd();
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
  public boolean ajouterReponses(GestionReponses gestionReponses, Integer numeroSemaine, Date date,
      Sportif unSportif, String pseudo, Questionnaire unQuestionnaire,
      Map<String, Integer> listeReponses) {
    if (date == null || unSportif == null || pseudo == null || pseudo.length() < 1
        || unQuestionnaire == null || listeReponses == null) {
      return false; // parametres incorrects
    }
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
          "INSERT INTO REPONSE (numeroSemaine, derniereModification, valeurReponse, unSportif, "
              + "uneQuestion)" + " VALUES (" + numeroSemaine + ",DATE('" + dateBdd + "'),"
              + map.getValue() + ",'" + pseudo + "','" + map.getKey() + "')";
      try {
        gestionBdd.executerRequete(query);
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
    String pseudo = unSportif.getPseudo();
    for (Map.Entry<String, Integer> map : listeReponses.entrySet()) {
      String intituleQuestion = map.getKey();
      String query = "UPDATE REPONSE SET derniereModification = DATE('" + dateBdd
          + "'), valeurReponse = " + map.getValue() + " WHERE numeroSemaine = " + numeroSemaine
          + " AND unSportif = '" + pseudo + "' AND uneQuestion = '" + intituleQuestion + "'";
      try {
        gestionBdd.executerRequete(query);
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
      String query = "SELECT DISTINCT REPONSE.*, QUESTION.unQuestionnaire FROM REPONSE, "
          + "QUESTION WHERE REPONSE.uneQuestion = QUESTION.intituleQuestion";
      ResultSet lesReponses = gestionBdd.executerRequeteAvecReponse(query);

      // initialisation des composantes nécessaire
      List<String> intituleQuestionnaire = new ArrayList<String>();
      List<String> pseudoSportif = new ArrayList<String>();
      List<Date> date = new ArrayList<Date>();
      List<Integer> numeroDesSemaines = new ArrayList<Integer>();
      List<Integer> valeur = new ArrayList<Integer>();

      // collecte des éléments (on rempli nos listes)
      while (lesReponses.next()) {
        intituleQuestionnaire.add(lesReponses.getString("unQuestionnaire"));
        pseudoSportif.add(lesReponses.getString("unSportif"));
        date.add(lesReponses.getDate("derniereModification"));
        numeroDesSemaines.add(lesReponses.getInt("numeroSemaine"));
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
      Integer numeroSemaine;
      Integer numeroSemaine2;
      // tant qu'il reste un élément dans la liste, toute les reponses n'ont pas été crée
      while (valeur.size() > 0) {
        // On prend tout les informations du premier élément
        questionnaire = intituleQuestionnaire.get(0);
        pseudo = pseudoSportif.get(0);
        d = date.get(0);
        numeroSemaine = numeroDesSemaines.get(0);
        reponses.add(valeur.get(0)); // On ajoute l'élément dans la liste des éléments à ajouté

        valeur.remove(0); // on supprimer l'élément qu'on va ajouté
        intituleQuestionnaire.remove(0);
        pseudoSportif.remove(0);
        date.remove(0);
        numeroDesSemaines.remove(0);

        // On cherche si d'autre élément sont conforme au premier élément

        boolean a = false;
        int i = 0;
        while (i < valeur.size()) {
          numeroSemaine2 = numeroDesSemaines.get(i);
          if (intituleQuestionnaire.get(i).equals(questionnaire)
              && pseudoSportif.get(i).equals(pseudo) && numeroSemaine == numeroSemaine2) {
            // Il est conforme, on l'ajoute dans la liste des éléments à ajouté
            reponses.add(valeur.get(i));

            valeur.remove(i); // on supprimer l'élément qu'on va ajouté
            intituleQuestionnaire.remove(i);
            pseudoSportif.remove(i);
            date.remove(i);
            numeroDesSemaines.remove(i);

            a = true;
          }
          if (!a) {
            i++;
          } else {
            i = 0;
            a = false;
          }
        }
        unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(questionnaire);
        unSportif = gestionSportif.consulterSportif(pseudo);
        // On fait l'ajout
        gestionReponses.getListeDesReponses()
            .add(new Reponses(d, numeroSemaine, reponses, unSportif, unQuestionnaire));
        // On réinitialise la liste ajouté, pour le prochain tour de boucle
        reponses = new ArrayList<Integer>();
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Cette méthode permet de retourner toutes les réponses liée à un questionnaire.
   * 
   * @param nomQuestionnaire : Chaine de caractères non null et non vide, représentant le nom du
   *        questionnaire.
   * @return Retourne les réponses liée à un questionnaire, ou null en cas de problème.
   */
  public ResultSet reponsesPourUnQuestionnaire(String nomQuestionnaire) {
    String query = "SELECT uneQuestion, unSportif, numeroSemaine, valeurReponse FROM REPONSE "
        + ", QUESTION WHERE uneQuestion = intituleQuestion AND unQuestionnaire =" + " '"
        + nomQuestionnaire + "'";
    try {
      ResultSet lesReponses = gestionBdd.executerRequeteAvecReponse(query);
      return lesReponses;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Cette méthode retourne le résultat d'une requête envoyé à notre base de données. Cette requête
   * à pour but de récupérer toutes les réponses à une question, et pour une semaine donnée.
   * 
   * @param uneQuestion : Chaine non null et non vide représentant l'intitulé de la question.
   * @param numeroSemaine : Numéro supérieur à 0, représentant une numéro de semaine.
   * @return les résultats de la requête.
   */
  public ResultSet reponsesPourUneQuestionEtUneSemaine(String uneQuestion, Integer numeroSemaine) {
    String query = "SELECT valeurReponse FROM REPONSE WHERE uneQuestion = '" + uneQuestion
        + "' AND numeroSemaine = " + numeroSemaine;
    try {
      ResultSet lesReponses = gestionBdd.executerRequeteAvecReponse(query);
      return lesReponses;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Permet d'effacer l'ensemble des données.
   * 
   * @return true si la demande est passé, false sinon.
   */
  public boolean effacerLesDonnees() {
    try {
      gestionBdd.executerRequete("DELETE FROM QUESTION");
      gestionBdd.executerRequete("DELETE FROM QUESTIONNAIRE");
      gestionBdd.executerRequete("DELETE FROM REPONSE");
      gestionBdd.executerRequete("DELETE FROM SPORTIF");
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }
}
