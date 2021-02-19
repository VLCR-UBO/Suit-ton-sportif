package fonctionnalite;

import bdd.GestionQuestionnaireBdd;
import bdd.GestionReponsesBdd;
import bdd.GestionSportifBdd;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe Facade fourni à l'IHM toutes les informations dont elle à besoin. Elle agit comme un
 * intermédiaire entre ces deux packages.
 * 
 * @author ychan
 *
 */
public class Facade {
  private GestionSportif gestionSportif;
  private GestionReponses gestionReponses;
  private GestionQuestionnaire gestionQuestionnaire;

  /**
   * Le constructeur de la classe Facade. Il initialise les objets dont nous allons avoir besoins
   * pour accéder et intéragir avec nos différentes ressources.
   */
  public Facade() {
    gestionSportif = new GestionSportif();
    gestionQuestionnaire = new GestionQuestionnaire();
    gestionReponses = new GestionReponses();

    load();
  }

  /**
   * Cette méthode renvoie la liste des sportifs.
   * 
   * @return Retourne la liste des sportifs ou null en cas de problème.
   */
  public List<String> obtenirListeSportif() {
    if (gestionSportif == null || gestionSportif.getListeDesSportifs().size() == 0) {
      return null;
    }
    List<String> ret = new ArrayList<String>();
    List<Sportif> l = gestionSportif.getListeDesSportifs();
    if (l == null) {
      return null;
    }
    int taille = l.size();
    for (int i = 0; i < taille; i++) {
      ret.add(l.get(i).getPseudo());
    }
    return ret;
  }

  /**
   * Cette méthode renvoie le nom du sportif demandé.
   * 
   * @param pseudo : Chaine non null et non vide, qui permet l'identifiaction d'un sportif de
   *        manière unique.
   * @return Retourne le nom du sportif demandé ou null en cas de problème.
   */
  public String obtenirNomSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    if (unSportif == null) {
      return null;
    }
    return unSportif.getNom();
  }

  /**
   * Cette méthode renvoie le prenom du sportif demandé.
   * 
   * @param pseudo : Chaine non null et non vide, qui permet l'identifiaction d'un sportif de
   *        manière unique.
   * @return Retourne le prenom du sportif demandé ou null en cas de problème.
   */
  public String obtenirPrenomSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    if (unSportif == null) {
      return null;
    }
    return unSportif.getPrenom();
  }

  /**
   * Cette méthode renvoie la date de naissance du sportif demandé.
   * 
   * @param pseudo : Chaine non null et non vide, qui permet l'identifiaction d'un sportif de
   *        manière unique.
   * @return Retourne la date de naissance du sportif demandé ou null en cas de problème.
   */
  public Calendar obtenirDateDeNaissanceSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    if (unSportif == null) {
      return null;
    }
    return unSportif.getDateDeNaissance();
  }

  /**
   * Cette méthode permet la suppression du sportif qui à pour pseudo la chaine passé en paramètre.
   * 
   * @param pseudo : Chaine non null et non vide, qui permet l'identifiaction d'un sportif de
   *        manière unique.
   * @return Retourne true si la demande c'est bien passé, false sinon.
   */
  public int supprimerUnSportif(String pseudo) {
    if (gestionSportif == null) {
      return -3;
    }
    return gestionSportif.supprimerSportif(pseudo);
  }

  /**
   * Cette méthode permet la modification du sportif qui à pour pseudo le paramètre "ancienPseudo".
   * Les nouvelles informations de celui-ci sont également fourni en paramètre.
   * 
   * @param ancienPseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif de manière unique.
   * @param nom : Chaine de caractères non null et non vide. Il s'agit du nouveau nom du sportif.
   * @param prenom : Chaine de caractères non null et non vide. Il s'agit du nouveau prenom du
   *        sportif.
   * @param pseudo : Chaine non null et non vide. Il s'agit du nouveau identifiant unique de ce
   *        sportif.
   * @param motDePasse : Chaine de caractères non null et non vide. Il s'agit du nouveau motDePasse
   *        du sportif.
   * @param dateDeNaissance : une date non null. Il s'agit de la nouvelle dateDeNaissance du
   *        sportif.
   * @return Retourne true si la demande c'est bien passé, false sinon.
   */
  public int modifierUnSportif(String ancienPseudo, String nom, String prenom, String pseudo,
      String motDePasse, Calendar dateDeNaissance) {
    if (gestionSportif == null) {
      return -4;
    }
    return gestionSportif.modifierSportif(ancienPseudo, nom, prenom, pseudo, motDePasse,
        dateDeNaissance);
  }

  /**
   * Cette méthode permet l'ajout du sportif qui à pour pseudo le paramètre "pseudo". Celui-ci sera
   * crée avec tout les paramètres fourni.
   * 
   * @param nom : Chaine de caractères non null et non vide.
   * @param prenom : Chaine de caractères non null et non vide.
   * @param pseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif de manière unique.
   * @param motDePasse : Chaine de caractères non null et non vide.
   * @param dateDeNaissance : Une date non null.
   * @return Retourne true si la demande c'est bien passé, false sinon.
   */
  public int ajouterUnSportif(String nom, String prenom, String pseudo, String motDePasse,
      Calendar dateDeNaissance) {
    if (gestionSportif == null) {
      return -3;
    }
    return gestionSportif.ajouterSportif(nom, prenom, pseudo, motDePasse, dateDeNaissance);
  }

  /**
   * Cette méthode permet de retourner les activités sportives du sportif demandé.
   * 
   * @param pseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif de manière unique.
   * @return Retourne la liste des activites du sportif demandé ou null en cas de problème.
   */
  public List<String> consulterLesActivitesDuSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    if (unSportif == null) {
      return null;
    }
    List<String> ret = new ArrayList<String>();
    List<ActiviteSportive> l = unSportif.getListeDesActivitesSportive();
    int taille = l.size();
    for (int i = 0; i < taille; i++) {
      ret.add(l.get(i).name());
    }
    return ret;
  }

  /**
   * Cette méthode retourne un objet HashMap contenant chaque intitulé de question et, pour chaque
   * intitulé de question, la réponse correspondante en valeur numérique. S'il trouve la liste de
   * question mais pas les réponses correpondante, on va aller chercher les réponses par défaut.
   * 
   * @param numeroSemaine : Le numéro correspondant à la semaine de création de ce questionnaire.
   * @param pseudo : Chaine non null et non vide qui permet l'identification d'un sportif de manière
   *        unique.
   * @param nomQuestionnaire : Chaine non null et non vide qui permet l'identification d'un
   *        questionnaire de manière unique.
   * @return Retourne l'objet HashMap correpondant, ou null en cas de problème.
   */
  public HashMap<String, Integer> obtenirQuestionnaireEtReponses(Integer numeroSemaine,
      String pseudo, String nomQuestionnaire) {
    if (gestionReponses == null || gestionQuestionnaire == null || gestionSportif == null) {
      return null;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(nomQuestionnaire);
    if (unSportif == null || unQuestionnaire == null) {
      return null;
    }
    List<Integer> listeReponses =
        gestionReponses.consulterReponses(numeroSemaine, unSportif, unQuestionnaire);
    for (int j = 0; j < listeReponses.size(); j++) {
      System.out.println("consulterReponses : " + listeReponses.get(j).toString() + "\n");
    }
    HashMap<String, Integer> ret = new HashMap<String, Integer>();
    List<Question> listeQuestions = unQuestionnaire.getListeDeQuestions();
    for (int j = 0; j < listeQuestions.size(); j++) {
      System.out.println("consulterQuestions : " + listeQuestions.get(j).getIntituleQuestion() + "\n");
    }
    int taille = listeQuestions.size();
    if (listeReponses == null) { // Pas de reponses -> On va chercher les reponses par défaut
      Integer reponseQuestion;
      boolean reponse;
      for (int i = 0; i < taille; i++) {
        reponse = ((QuestionBoolenne) listeQuestions.get(i)).getReponseQuestion();
        if (reponse == false) {
          reponseQuestion = 0;
        } else {
          reponseQuestion = 1;
        }
        ret.put(listeQuestions.get(i).getIntituleQuestion(), reponseQuestion);
      }
    } else {
      if (taille != listeReponses.size()) { // Problème
        return null;
      }
      for (int i = 0; i < taille; i++) {
        ret.put(listeQuestions.get(i).getIntituleQuestion(), listeReponses.get(i));
      }
      for (String key : ret.keySet()) {
        System.out.println("question : " + key);
        System.out.println("valeur : " + ret.get(key).toString());
      }
    }
    return ret;
  }

  /**
   * Cette méthode permet de modifier ou d'ajouter les reponses pour un sportif, un questionnnaire,
   * et un numéro de semaine précis. Si lors de la modification, l'élément n'existe pas, cette
   * méthode va essayer d'ajouter une liste de réponses avec les paramètres qui lui sont fourni.
   * Sinon elle va se contenter de la modifier.
   * 
   * @param date : Il s'agit de la date de la dernière modification de la liste de reponses.
   * @param numeroSemaine : Contient le numéro de la semaine ou l'élément à été crée.
   * @param listeReponses : Contient les questions, et respectivement leurs réponses a ajouté.
   * @param pseudo : Ces réponses correspondent à ce sportif.
   * @param nomQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return Retourne true si la modification c'est bien passé, false sinon.
   */
  public int modifierReponses(Date date, String pseudo, String nomQuestionnaire,
      Map<String, Integer> listeReponses, Integer numeroSemaine) {
    if (gestionReponses == null || gestionQuestionnaire == null || gestionSportif == null) {
      return -4;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(nomQuestionnaire);
    if (unSportif == null || unQuestionnaire == null) {
      return -3;
    }

    // On procède à la modification
    List<Integer> listeDesReponses = new ArrayList<Integer>();
    for (String key : listeReponses.keySet()) {
      listeDesReponses.add(listeReponses.get(key));
    }
    int modification = gestionReponses.modifierReponses(numeroSemaine, date, unSportif,
        unQuestionnaire, listeReponses);
    if (modification == 1) {
      return modification;
    }

    // On fait l'ajout
    return gestionReponses.ajouterReponses(numeroSemaine, date, unSportif, unQuestionnaire,
        listeReponses);
  }

  /**
   * Cette méthode retourne la liste de tout les nom des questionnaires.
   * 
   * @return Retourne la liste de tout les questionnaires, ou null en cas de problème.
   */
  public List<String> consulterLesQuestionnaire() {
    if (gestionQuestionnaire == null) {
      return null;
    }
    List<String> ret = new ArrayList<String>();
    List<Questionnaire> l = gestionQuestionnaire.getListeQuestionnaire();
    int taille = l.size();
    for (int i = 0; i < taille; i++) {
      ret.add(l.get(i).getNomQuestionnaire());
    }
    return ret;
  }

  /**
   * Cette méthode retourne la liste des questions correspondante au à l'intitulé de questionnaire
   * fourni en paramètre.
   * 
   * @param nomQuestionnaire : Chaine non null et non vide qui permet l'identification d'un sportif
   *        de manière unique.
   * @return Retourne la liste des questions correspondantes, ou null en cas de problème.
   */
  public List<String> consulterLesQuestionDuQuestionnaire(String nomQuestionnaire) {
    if (gestionQuestionnaire == null) {
      return null;
    }
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(nomQuestionnaire);
    if (unQuestionnaire == null) {
      return null;
    }
    List<String> ret = new ArrayList<String>();
    List<Question> l =
        gestionQuestionnaire.consulterListeQuestion(nomQuestionnaire).getListeDeQuestions();
    int taille = l.size();
    for (int i = 0; i < taille; i++) {
      ret.add(l.get(i).getIntituleQuestion());
    }
    return ret;
  }

  /**
   * Cette méthode permet l'ajout du questionnaire qui à pour intitulé le paramètre
   * "nomQuestionnaire". Celui-ci sera crée avec la liste de questions fourni.
   * 
   * @param nomQuestionnaire : L'identifiant unique du nouveau questionnaire.
   * @param questions : la liste de string utilisée pour créée les questions.
   * @return Retourne true si la demande c'est bien déroulé, false sinon.
   */
  public int ajouterUnQuestionnaire(String nomQuestionnaire, List<String> questions) {
    if (gestionQuestionnaire == null) {
      return -3;
    }
    return gestionQuestionnaire.ajouterQuestionnaire(nomQuestionnaire, questions);
  }

  // méthode incohérente
  /**
   * Une méthode pour modifier un questionnaire depuis une liste de string utilisée pour créée le
   * nouveau questionnaire. Le questionnaire est identifié avec son nom.
   * 
   * @param ancienNomQuestionnaire : L'identifiant unique du questionnaire.
   * @param nouveauNomQuestionnaire : L'identifiant unique du nouveau questionnaire.
   * @param questions : la liste de string utilisée pour créée les questions.
   * @return Retourne true si la demande c'est bien déroulé, false sinon.
   */
  public int modifierUnQuestionnaire(String ancienNomQuestionnaire, String nouveauNomQuestionnaire,
      List<String> questions) {
    if (gestionQuestionnaire == null) {
      return -3;
    }
    return gestionQuestionnaire.modifierQuestionnaire(ancienNomQuestionnaire,
        nouveauNomQuestionnaire, questions);
  }

  /**
   * Cette méthode permet la suppression du questionnaire passé en paramètre.
   * 
   * @param nomQuestionnaire : L'identifiant unique du questionnaire à supprimer.
   * @return Retourne true si la demande c'est bien passé, false sinon.
   */
  public int supprimerUnQuestionnaire(String nomQuestionnaire) {
    if (gestionQuestionnaire == null) {
      return -3;
    }
    return gestionQuestionnaire.supprimerQuestionnaire(nomQuestionnaire);
  }

  /**
   * Cette méthode permet l'ajout d'une question, avec l'intitulé fourni pour identifiant, au
   * questionnaire cible également fourni en paramètre.
   * 
   * @param nomQuestionnaire : L'identifiant unique du questionnaire.
   * @param intitule : L'identifiant unique de la question.
   * @return Retourne true si la demande c'est bien passez, false sinon.
   */
  public int ajouterUneQuestion(String nomQuestionnaire, String intitule) {
    if (gestionQuestionnaire == null) {
      return -4;
    }
    return gestionQuestionnaire.ajouterQuestion(nomQuestionnaire, intitule);
  }

  /**
   * Cette méthode permet la modification d'une question présent de le questionnaire cible, la
   * question est identifié avec l'ancienIntitule, et celle-ci va être modifié avec les autres
   * paramètres fourni.
   * 
   * @param nomQuestionnaire : L'identifiant unique du questionnaire.
   * @param ancienIntitule : L'identifiant unique de la question.
   * @param nouveauIntitule : Le nouveau identifiant unique de la question.
   * @return Retourne true si la demande c'est bien passez, false sinon.
   */
  public int modifierUneQuestion(String nomQuestionnaire, String ancienIntitule,
      String nouveauIntitule, boolean defaut) {
    if (gestionQuestionnaire == null) {
      return -4;
    }
    return gestionQuestionnaire.modifierQuestion(nomQuestionnaire, ancienIntitule, nouveauIntitule,
        defaut);
  }

  /**
   * Cette méthode permet la suppression d'une question présent dans le questionnaire cible. La
   * question est identifié avec l'intitulé de la question fourni en paramètre.
   * 
   * @param nomQuestionnaire : L'identifiant unique du questionnaire.
   * @param intitule : L'identifiant unique de la question.
   * @return Retourne true si la demande c'est bien passez, false sinon.
   */
  public int supprimerUneQuestion(String nomQuestionnaire, String intitule) {
    if (gestionQuestionnaire == null) {
      return -4;
    }
    return gestionQuestionnaire.supprimerQuestion(nomQuestionnaire, intitule);
  }

  /**
   * Cette méthode permet de charger les données depuis notre base de données, dans l'application.
   * 
   * @return Retourne true si le chargement des données c'est bien passé, false sinon.
   */
  public boolean load() {
    if (gestionSportif == null || gestionQuestionnaire == null || gestionReponses == null) {
      return false;
    }
    boolean ret1 = this.gestionSportif.load();
    boolean ret2 = this.gestionQuestionnaire.load();
    boolean ret3 = this.gestionReponses.load(this.gestionSportif, this.gestionQuestionnaire);
    if (ret1 && ret2 && ret3) {
      return true;
    }
    return false;
  }

  /**
   * Cette méthode permet d'exporter toutes nos réponses pour un questionnaire.
   * 
   * @param nomQuestionnaire : Chaine de caractères non null et non vide, représentant le nom du
   *        questionnaire.
   * @return Retourne true si l'exportation c'est bien déroulé, false sinon.
   */
  public int exporter(String nomQuestionnaire) {
    if (gestionReponses == null) {
      return -5;
    }
    return gestionReponses.exporter(gestionQuestionnaire, nomQuestionnaire);
  }

  public List<Integer> obtenirReponses(String uneQuestion, Integer numeroSemaine) {
    if (gestionReponses == null) {
      return null;
    }
    return gestionReponses.obtenirReponses(gestionQuestionnaire, uneQuestion, numeroSemaine);
  }

}

