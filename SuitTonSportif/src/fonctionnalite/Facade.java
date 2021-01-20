package fonctionnalite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * La classe Facade fourni à l'IHM toutes les informations dont elle à besoin. Elle agit comme
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
   * Le constructeur de la classe Facade. Il initialise les 3 objets dont nous allons avoir besoins
   * pour accéder à nos différentes ressources.
   */
  public Facade() {
    gestionSportif = new GestionSportif();
    gestionReponses = new GestionReponses();
    gestionQuestionnaire = new GestionQuestionnaire();
  }

  /**
   * Cette méthode renvoie la liste des sportifs.
   * 
   * @return la liste des sportifs ou null en cas de problème.
   */
  public List<String> obtenirListeSportif() {
    if (gestionSportif == null || gestionSportif.getListeDesSportifs().size() == 0) {
      return null;
    }
    List<String> ret = new ArrayList<String>();
    List<Sportif> l = gestionSportif.getListeDesSportifs();
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
   * @return le nom du sportif demandé ou null en cas de problème.
   */
  public String obtenirNomSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    return gestionSportif.consulterSportif(pseudo).getNom();
  }

  /**
   * Cette méthode renvoie le prenom du sportif demandé.
   * 
   * @param pseudo : Chaine non null et non vide, qui permet l'identifiaction d'un sportif de
   *        manière unique.
   * @return le prenom du sportif demandé ou null en cas de problème.
   */
  public String obtenirPrenomSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    return gestionSportif.consulterSportif(pseudo).getPrenom();
  }

  /**
   * Cette méthode renvoie la date de naissance du sportif demandé.
   * 
   * @param pseudo : Chaine non null et non vide, qui permet l'identifiaction d'un sportif de
   *        manière unique.
   * @return la date de naissance du sportif demandé ou null en cas de problème.
   */
  public Calendar obtenirDateDeNaissanceSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    return gestionSportif.consulterSportif(pseudo).getDateDeNaissance();
  }

  /**
   * Cette méthode permet la suppression du sportif qui à pour pseudo la chaine passé en paramètre.
   * 
   * @param pseudo : Chaine non null et non vide, qui permet l'identifiaction d'un sportif de
   *        manière unique.
   * @return true si la demande c'est bien passé, false sinon.
   */
  public boolean supprimerUnSportif(String pseudo) {
    if (gestionSportif == null) {
      return false;
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
   * @return true si la demande c'est bien passé, false sinon.
   */
  public boolean modifierUnSportif(String ancienPseudo, String nom, String prenom, String pseudo,
      String motDePasse, Calendar dateDeNaissance) {
    if (gestionSportif == null) {
      return false;
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
   * @return true si la demande c'est bien passé, false sinon.
   */
  public boolean ajouterUnSportif(String nom, String prenom, String pseudo, String motDePasse,
      Calendar dateDeNaissance) {
    if (gestionSportif == null) {
      return false;
    }
    return gestionSportif.ajouterSportif(nom, prenom, pseudo, motDePasse, dateDeNaissance);
  }

  /**
   * Cette méthode permet de retourner les activités sportives du sportif demandé.
   * 
   * @param pseudo : Chaine de caractères non null et non vide qui permet l'identification du
   *        sportif de manière unique.
   * @return la liste des activites du sportif demandé ou null en cas de problème.
   */
  public List<String> consulterLesActivitesDuSportif(String pseudo) {
    if (gestionSportif == null) {
      return null;
    }
    List<String> ret = new ArrayList<String>();
    List<ActiviteSportive> l =
        gestionSportif.consulterSportif(pseudo).getListeDesActivitesSportive();
    int taille = l.size();
    for (int i = 0; i < taille; i++) {
      ret.add(l.get(i).name());
    }
    return ret;
  }

  /**
   * Cette méthode retourne un objet HashMap contenant chaque intitulé de question et, pour chaque
   * intitulé de question, la réponse correspondante en valeur numérique.
   * 
   * @param numeroSemaine : Le numéro correspondant à la semaine de création de ce questionnaire.
   * @param pseudo : Chaine non null et non vide qui permet l'identification d'un sportif de manière
   *        unique.
   * @param nomQuestionnaire : Chaine non null et non vide qui permet l'identification d'un
   *        questionnaire de manière unique.
   * @return l'objet HashMap correpondant, ou null en cas de problème.
   */
  public HashMap<String, Integer> obtenirQuestionnaireEtReponses(Integer numeroSemaine,
      String pseudo, String nomQuestionnaire) {
    if (gestionReponses == null || gestionQuestionnaire == null || gestionSportif == null) {
      return null;
    }
    Sportif unSportif = gestionSportif.consulterSportif(pseudo);
    Questionnaire unQuestionnaire = gestionQuestionnaire.consulterListeQuestion(nomQuestionnaire);
    List<Integer> listeReponses =
        gestionReponses.consulterReponses(numeroSemaine, unSportif, unQuestionnaire);
    List<Question> listeQuestions = unQuestionnaire.getListeDeQuestions();
    int taille = listeReponses.size();
    if (taille != listeQuestions.size()) { // Problème
      return null;
    }

    HashMap<String, Integer> ret = new HashMap<String, Integer>();
    for (int i = 0; i < taille; i++) {
      ret.put(listeQuestions.get(i).getIntituleQuestion(), listeReponses.get(i));
    }
    return ret;
  }

}
