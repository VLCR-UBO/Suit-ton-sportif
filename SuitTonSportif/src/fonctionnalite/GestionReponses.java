package fonctionnalite;

import bdd.GestionReponsesBdd;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * La classe GestionReponses contient la liste des objet Reponses. Elle permet l'ajout, la
 * suppression, et la consultation des Reponses.
 * 
 * @author ychan
 *
 */
public class GestionReponses {
  private GestionReponsesBdd gestionReponsesBdd;
  private List<Reponses> listeDesReponses;

  /**
   * Constructeur de la classe GestionReponses. Il initialise une liste vide qui contiendra nos
   * objet reponses.
   */
  public GestionReponses() {
    this.listeDesReponses = new ArrayList<Reponses>();
    gestionReponsesBdd = new GestionReponsesBdd();
  }

  /**
   * Permet de charger les données depuis la base de données, dans l'application.
   * 
   * @return true si la demande c'est bien passé, false sinon.
   */
  public boolean load(GestionSportif gestionSportif, GestionQuestionnaire gestionQuestionnaire) {
    return this.gestionReponsesBdd.load(this, gestionSportif, gestionQuestionnaire);
  }

  /**
   * Cette méthode permet d'ajouter un objet reponses avec les paramètres qui lui sont fourni.
   * L'ajout aura lieu si et seulement si les paramètres sont correct et si l'élément n'est pas déjà
   * présent (même sportif, questionnaire, et semaine). Une valeur boolean sera retournée : true si
   * la reponses est ajouté, false sinon.
   * 
   * @param date : Il s'agit de la date de la dernière modification de la liste de reponses.
   * @param listeReponses : Cette liste de Integer est la liste des reponses a un questionnaire.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int ajouterReponses(Integer numeroSemaine, Date date, Sportif unSportif,
      Questionnaire unQuestionnaire, Map<String, Integer> listeReponses) {
    if (numeroSemaine == null || numeroSemaine < 1 || date == null || unSportif == null
        || unQuestionnaire == null || listeReponses == null) {
      return -2; // parametres incorrects
    }
    int taille = this.listeDesReponses.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesReponses.get(i).getUnSportif().equals(unSportif)
          && this.listeDesReponses.get(i).getUnQuestionnaire().equals(unQuestionnaire)
          && this.listeDesReponses.get(i).getNumeroSemaine() == numeroSemaine) {
        return -1; // deja present
      }
    }
    boolean retBdd = gestionReponsesBdd.ajouterReponses(this, numeroSemaine, date, unSportif,
        unSportif.getPseudo(), unQuestionnaire, listeReponses);
    if (!retBdd) {
      return 0;
    }
    List<Integer> listeDesReponses2 = new ArrayList<Integer>();
    for (String key : listeReponses.keySet()) {
      listeDesReponses2.add(listeReponses.get(key));
    }
    this.listeDesReponses
        .add(new Reponses(date, numeroSemaine, listeDesReponses2, unSportif, unQuestionnaire));
    return 1;
  }

  /**
   * Cette méthode permet de modifier la listes de reponses d'un objet reponses (la date sera mise a
   * jour également), Celui-ci est identifié avec les paramètres unSportif, unQuestionnaire, et le
   * numéro de semaine.
   * 
   * @param date : Il s'agit de la date de la dernière modification de la liste de reponses.
   * @param numeroSemaine : Contient le numéro de la semaine ou l'élément est crée.
   * @param listeReponses : Cette liste de Integer est la liste des reponses à un questionnaire.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int modifierReponses(Integer numeroSemaine, Date date, Sportif unSportif,
      Questionnaire unQuestionnaire, Map<String, Integer> listeReponses) {
    if (numeroSemaine == null || numeroSemaine < 1 || date == null || unSportif == null
        || unQuestionnaire == null || listeReponses == null) {
      return -2; // paramètres incorrects
    }
    Reponses maReponse = null;
    int taille = this.listeDesReponses.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesReponses.get(i).getUnSportif().equals(unSportif)
          && this.listeDesReponses.get(i).getUnQuestionnaire().equals(unQuestionnaire)
          && this.listeDesReponses.get(i).getNumeroSemaine() == numeroSemaine) {
        maReponse = this.listeDesReponses.get(i);
      }
    }
    if (maReponse == null) {
      return -1; // reponse non trouvé
    }
    boolean retBdd = gestionReponsesBdd.modifierReponses(numeroSemaine, date, unSportif,
        unQuestionnaire, listeReponses);
    if (!retBdd) {
      return 0;
    }
    List<Integer> listeDesReponses2 = new ArrayList<Integer>();
    for (String key : listeReponses.keySet()) {
      listeDesReponses2.add(listeReponses.get(key));
    }
    maReponse.setDate(date);
    maReponse.setListeReponses(listeDesReponses2);
    return 1;
  }

  /**
   * Cette méthode permet d'exporter toutes nos réponses pour un questionnaire.
   * 
   * @param gestionQuestionnaire : Objet non null, permettant à GestionReponses d'utiliser les
   *        méthode présentes dans GestionQuestionnaire.
   * @param nomQuestionnaire : Chaine de caractères non null et non vide, représentant le nom du
   *        questionnaire.
   * @return Retourne 1 si la demande c'est bien passé, ou un nombre inférieur en cas d'erreur.
   */
  public int exporter(GestionQuestionnaire gestionQuestionnaire, String nomQuestionnaire) {
    if (gestionQuestionnaire == null || gestionReponsesBdd == null || nomQuestionnaire == null
        || nomQuestionnaire.length() < 1) {
      return -4;
    }
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(nomQuestionnaire);
    if (unQuestionnaire == null) {
      return -3;
    }
    // recuperation des reponses liee au questionnaire
    ResultSet lesReponses = gestionReponsesBdd.reponsesPourUnQuestionnaire(nomQuestionnaire);
    if (lesReponses == null) {
      return -2;
    }
    // initialisation des listes
    List<String> pseudoSportif = new ArrayList<String>();
    List<Integer> numeroDesSemaines = new ArrayList<Integer>();
    List<String> intituleQuestion = new ArrayList<String>();
    List<Integer> valeurReponse = new ArrayList<Integer>();

    // collecte des éléments (on rempli nos listes)
    try {
      while (lesReponses.next()) {
        pseudoSportif.add(lesReponses.getString("unSportif"));
        numeroDesSemaines.add(lesReponses.getInt("numeroSemaine"));
        intituleQuestion.add(lesReponses.getString("uneQuestion"));
        valeurReponse.add(lesReponses.getInt("valeurReponse"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return -1;
    }
    int taille = intituleQuestion.size();
    // exportation en csv
    FileWriter file = null;
    String separator = "\n";
    String delimiter = ";";
    try {
      file = new FileWriter("../" + nomQuestionnaire + ".csv");
      file.append("UnSportif;numeroSemaine;uneQuestion;valeurReponse"); // l'en-tête
      file.append(separator);
      for (int i = 0; i < taille; i++) {
        file.append(pseudoSportif.get(i));
        file.append(delimiter);
        file.append(numeroDesSemaines.get(i).toString());
        file.append(delimiter);
        file.append(intituleQuestion.get(i));
        file.append(delimiter);
        file.append(valeurReponse.get(i).toString());
        file.append(separator);
      }
      file.close();
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
    return 1;
  }

  /**
   * Cette méthode permet de récupérer la liste de toutes les réponses, concernant une question, et
   * à une semaine bien précise.
   * 
   * @param gestionQuestionnaire : Objet non null, permettant à GestionReponses d'utiliser les
   *        méthode présentes dans GestionQuestionnaire.
   * @param uneQuestion : Chaine non null et non vide, contenant l'intitulé de la question.
   * @param numeroSemaine : Numéro supérieur à 0, qui représente une semaine dans l'année.
   * @return Retourne la liste des réponses, ou null en cas d'erreur.
   */
  public List<Integer> obtenirReponses(GestionQuestionnaire gestionQuestionnaire,
      String uneQuestion, Integer numeroSemaine) {
    if (gestionQuestionnaire == null || gestionReponsesBdd == null || uneQuestion == null
        || uneQuestion.length() < 1 || numeroSemaine == null || numeroSemaine < 1) {
      return null;
    }
    // recuperation des reponses liee a ma question et à la date demandé
    ResultSet lesReponses =
        gestionReponsesBdd.reponsesPourUneQuestionEtUneSemaine(uneQuestion, numeroSemaine);
    if (lesReponses == null) {
      return null;
    }

    List<Integer> reponses = new ArrayList<Integer>();
    Integer nbReponsesPositives = 0;
    Integer nbReponsesNegatives = 0;
    try {
      while (lesReponses.next()) {
        Integer valeur = lesReponses.getInt("valeurReponse");
        if (valeur == 1) {
          nbReponsesPositives++;
        } else {
          nbReponsesNegatives++;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    reponses.add(nbReponsesPositives);
    reponses.add(nbReponsesNegatives);
    return reponses;
  }

  /**
   * Cette méthode permet de recupérer un objet Reponses parmi la liste. Celui-ci est identifié avec
   * les paramètres unSporti, unQuestionnaire, et le numéro de semaine.
   * 
   * @param numeroSemaine : Contient le numéro de la semaine ou l'élément est crée.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return Retourne la liste des réponses correspondant aux paramètres indiqué, ou null en cas de
   *         problème.
   */
  public List<Integer> consulterReponses(Integer numeroSemaine, Sportif unSportif,
      Questionnaire unQuestionnaire) {
    if (numeroSemaine == null || numeroSemaine < 1 || unSportif == null
        || unQuestionnaire == null) {
      return null; // parametres incorrects
    }
    int taille = this.listeDesReponses.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesReponses.get(i).getUnSportif().equals(unSportif)
          && this.listeDesReponses.get(i).getUnQuestionnaire().equals(unQuestionnaire)
          && this.listeDesReponses.get(i).getNumeroSemaine() == numeroSemaine) {
        return this.listeDesReponses.get(i).getListeReponses();
      }
    }
    return null; // reponses absente
  }

  public List<Reponses> getListeDesReponses() {
    return listeDesReponses;
  }

  public void setListeDesReponses(List<Reponses> listeDesReponses) {
    this.listeDesReponses = listeDesReponses;
  }

  /**
   * Permet d'effacer l'ensemble des données.
   * 
   * @return true si la demande est passé, false sinon.
   */
  public boolean effacerLaBdd() {
    if (gestionReponsesBdd == null) {
      return false;
    }
    return gestionReponsesBdd.effacerLesDonnees();
  }

}
