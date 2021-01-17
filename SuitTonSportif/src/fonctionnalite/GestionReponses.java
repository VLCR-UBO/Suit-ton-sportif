package fonctionnalite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe GestionReponses contient la liste des objet Reponses. Elle permet l'ajout, la
 * suppression, et la consultation des Reponses.
 * 
 * @author ychan
 *
 */
public class GestionReponses {
  private List<Reponses> listeDesReponses;

  /**
   * Constructeur de la classe GestionReponses. Il initialise une liste vide qui contiendra nos
   * objet reponses.
   */
  public GestionReponses() {
    this.listeDesReponses = new ArrayList<Reponses>();
  }

  /**
   * Cette méthode permet d'ajouter un objet reponses avec les paramètres qui lui sont fourni.
   * L'ajout aura lieu si et seulement si les paramètres sont correct et si l'élément n'est pas déjà
   * présent (même sportif, questionnaire, et semaine). Une valeur boolean sera retournée : true si
   * la reponses à été ajouté, false sinon.
   * 
   * @param date : Il s'agit de la date de la dernière modification de la liste de reponses.
   * @param listeReponses : Cette liste de Integer est la liste des reponses à un questionnaire.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return Retourne true si la reponses à été ajoutée, false sinon.
   */
  public boolean ajouterReponses(Date date, Sportif unSportif, Questionnaire unQuestionnaire,
      List<Integer> listeReponses) {
    if (date == null || unSportif == null || unQuestionnaire == null || listeReponses == null) {
      return false; // parametres incorrects
    }
    SimpleDateFormat formater = new SimpleDateFormat("w");
    System.out.println(formater.format(date));
    Integer numeroSemaine = Integer.parseInt(formater.format(date));
    int taille = this.listeDesReponses.size();
    for (int i = 0; i < taille; i++) {
      if (this.listeDesReponses.get(i).getUnSportif().equals(unSportif)
          && this.listeDesReponses.get(i).getUnQuestionnaire().equals(unQuestionnaire)
          && this.listeDesReponses.get(i).getNumeroSemaine() == numeroSemaine) {
        return false; // deja present
      }
    }
    this.listeDesReponses
        .add(new Reponses(date, numeroSemaine, listeReponses, unSportif, unQuestionnaire));
    return true;
  }

  /**
   * Cette méthode permet de modifier la listes de reponses d'un objet reponses (la date sera mise à
   * jour également), Celui-ci est identifié avec les paramètres unSportif, unQuestionnaire, et le
   * numéro de semaine.
   * 
   * @param date : Il s'agit de la date de la dernière modification de la liste de reponses.
   * @param numeroSemaine : Contient le numéro de la semaine ou l'élément à été crée.
   * @param listeReponses : Cette liste de Integer est la liste des reponses à un questionnaire.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return Retourne true si la reponses à été modifiée, false sinon.
   */
  public boolean modifierReponses(Integer numeroSemaine, Date date, Sportif unSportif,
      Questionnaire unQuestionnaire, List<Integer> listeReponses) {
    if (numeroSemaine == null || numeroSemaine < 1 || date == null || unSportif == null
        || unQuestionnaire == null || listeReponses == null) {
      return false; // parametres incorrects
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
    maReponse.setDate(date);
    maReponse.setListeReponses(listeReponses);
    return true;
  }

  /**
   * Cette méthode permet de recupérer un objet Reponses parmi la liste. Celui-ci est identifié avec
   * les paramètres unSporti, unQuestionnaire, et le numéro de semaine.
   * 
   * @param numeroSemaine : Contient le numéro de la semaine ou l'élément à été crée.
   * @param unSportif : Ces réponses correspondent à ce sportif.
   * @param unQuestionnaire : Ces réponses correspondent à ce questionnaire.
   * @return
   */
  public Reponses consulterReponses(Integer numeroSemaine, Sportif unSportif,
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
        return this.listeDesReponses.get(i);
      }
    }
    return null; // reponses absente
  }

}
