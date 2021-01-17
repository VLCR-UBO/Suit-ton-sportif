package fonctionnalite;

import java.util.Date;
import java.util.List;

/**
 * La classe Reponses permet la cr�ation d'une liste de reponses pour un sportif et questionnaire
 * donn�. Des Getters et des Setters permettent l'acc�s � ses informations.
 * 
 * @author ychan
 *
 */
public class Reponses {
  private Date date;
  private Integer numeroSemaine;
  private List<Integer> listeReponses;
  private Sportif unSportif;
  private Questionnaire unQuestionnaire;

  /**
   * Constructeur de la classe Reponses. Il cr�e une nouvelle Reponses avec les param�tres qui lui
   * sont fourni.
   * 
   * @param date : Il s'agit de la date de la derni�re modification de la liste de reponses.
   * @param numeroSemaine : Contient le num�ro de la semaine ou l'�l�ment � �t� cr�e.
   * @param listeReponses : Cette liste de Integer est la liste des reponses � un questionnaire.
   * @param unSportif : Ces r�ponses correspondent � ce sportif.
   * @param unQuestionnaire : Ces r�ponses correspondent � ce questionnaire.
   */
  public Reponses(Date date, Integer numeroSemaine, List<Integer> listeReponses, Sportif unSportif,
      Questionnaire unQuestionnaire) {
    this.date = date;
    this.numeroSemaine = numeroSemaine;
    this.listeReponses = listeReponses;
    this.unSportif = unSportif;
    this.unQuestionnaire = unQuestionnaire;
  }

  public int getNumeroSemaine() {
    return numeroSemaine;
  }

  public void setNumeroSemaine(Integer numeroSemaine) {
    this.numeroSemaine = numeroSemaine;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<Integer> getListeReponses() {
    return listeReponses;
  }

  public void setListeReponses(List<Integer> listeReponses) {
    this.listeReponses = listeReponses;
  }

  public Sportif getUnSportif() {
    return unSportif;
  }

  public void setUnSportif(Sportif unSportif) {
    this.unSportif = unSportif;
  }

  public Questionnaire getUnQuestionnaire() {
    return unQuestionnaire;
  }

  public void setUnQuestionnaire(Questionnaire unQuestionnaire) {
    this.unQuestionnaire = unQuestionnaire;
  }


}
