package fonctionnalite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
   * Cette m�thode permet d'ajouter un objet reponses avec les param�tres qui lui sont fourni.
   * L'ajout aura lieu si et seulement si les param�tres sont correct et si l'�l�ment n'est pas d�j�
   * pr�sent (m�me sportif, questionnaire, et semaine). Une valeur boolean sera retourn�e : true si
   * la reponses � �t� ajout�, false sinon.
   * 
   * @param date : Il s'agit de la date de la derni�re modification de la liste de reponses.
   * @param listeReponses : Cette liste de Integer est la liste des reponses � un questionnaire.
   * @param unSportif : Ces r�ponses correspondent � ce sportif.
   * @param unQuestionnaire : Ces r�ponses correspondent � ce questionnaire.
   * @return Retourne true si la reponses � �t� ajout�e, false sinon.
   */
  public boolean ajouterReponses(Date date, Sportif unSportif, Questionnaire unQuestionnaire,
      List<Integer> listeReponses) {
    if (date == null || unSportif == null || unQuestionnaire == null || listeReponses == null) {
      return false; // parametres incorrects
    }
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    Integer numeroSemaine = calendar.get(Calendar.WEEK_OF_YEAR);
    System.out.println(numeroSemaine);
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
   * Cette m�thode permet de modifier la listes de reponses d'un objet reponses (la date sera mise �
   * jour �galement), Celui-ci est identifi� avec les param�tres unSportif, unQuestionnaire, et le
   * num�ro de semaine.
   * 
   * @param date : Il s'agit de la date de la derni�re modification de la liste de reponses.
   * @param numeroSemaine : Contient le num�ro de la semaine ou l'�l�ment � �t� cr�e.
   * @param listeReponses : Cette liste de Integer est la liste des reponses � un questionnaire.
   * @param unSportif : Ces r�ponses correspondent � ce sportif.
   * @param unQuestionnaire : Ces r�ponses correspondent � ce questionnaire.
   * @return Retourne 1 si la reponses est modifié, 0 s'il elle n'a pas été trouvé, -1 sinon.
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
    if (maReponse == null) {
      return false;
    }
    maReponse.setDate(date);
    maReponse.setListeReponses(listeReponses);
    return true;
  }

  /**
   * Cette m�thode permet de recup�rer un objet Reponses parmi la liste. Celui-ci est identifi� avec
   * les param�tres unSporti, unQuestionnaire, et le num�ro de semaine.
   * 
   * @param numeroSemaine : Contient le num�ro de la semaine ou l'�l�ment � �t� cr�e.
   * @param unSportif : Ces r�ponses correspondent � ce sportif.
   * @param unQuestionnaire : Ces r�ponses correspondent � ce questionnaire.
   * @return
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

}
