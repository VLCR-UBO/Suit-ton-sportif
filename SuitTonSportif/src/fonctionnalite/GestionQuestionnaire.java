package fonctionnalite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionQuestionnaire {
  private List<Questionnaire> listeQuestionnaire;

  /**
   * Un constructeur qui genère une classe qui va gérer les questionnaires.
   * 
   */
  public GestionQuestionnaire() {
    this.setListeQuestionnaire(new ArrayList<Questionnaire>());
  }

  /**
   * Une méthode pour ajouter un questionnaire depuis une liste de string utilisée pour créée les
   * questions.
   * 
   * @param nomQuestionnaire : L'identifiant unique du nouveau questionnaire.
   * @param questions : la liste de string utilisée pour créée les questions.
   */
  public boolean ajouterQuestionnaire(String nomQuestionnaire, List<String> questions) {
    boolean ret = false;
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0 && questions != null) {
      List<Question> listeQuestions = new ArrayList<Question>();
      Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
      Questionnaire questionnaireVerif;
      boolean verif = false;
      while (questionnaireIterator.hasNext() && (!verif)) {
        questionnaireVerif = questionnaireIterator.next();
        if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
          verif = true;
        }
      }
      if (!verif) {
        for (String intitule : questions) {
          Question question = new QuestionBoolenne(intitule, true);
          listeQuestions.add(question);
        }
        Questionnaire questionnaire = new Questionnaire(nomQuestionnaire, listeQuestions);
        if (!this.listeQuestionnaire.contains(questionnaire)) {
          this.listeQuestionnaire.add(questionnaire);
          ret = true;
        }
      }
    }
    return ret;
  }

  /**
   * Une méthode pour modifier un questionnaire depuis une liste de string utilisée pour créée le
   * nouveau questionnaire.
   * 
   * @param ancienNomQuestionnaire : L'identifiant unique pour retrouver notre questionnaire
   * @param nouveauNomQuestionnaire : Le nouvelle identifiant unique pour notre questionnaire
   * @param questions : la liste de string utilisée pour le nouveau questionnaire
   * @return Retourne vrai si le questionnaire à été modifié, faux sinon
   */
  public boolean modifierQuestionnaire(String ancienNomQuestionnaire,
      String nouveauNomQuestionnaire, List<String> questions) {
    boolean ret = false;
    if (ancienNomQuestionnaire != null && ancienNomQuestionnaire.length() > 0
        && nouveauNomQuestionnaire != null && nouveauNomQuestionnaire.length() > 0
        && questions != null) {
      List<Question> listeQuestions = new ArrayList<Question>();
      Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
      Questionnaire questionnaireVerif;
      boolean verif = false;
      while (questionnaireIterator.hasNext() && (!verif)) {
        questionnaireVerif = questionnaireIterator.next();
        if (questionnaireVerif.getNomQuestionnaire() == ancienNomQuestionnaire) {
          verif = true;
        }
      }
      if (verif) {
        for (String intitule : questions) {
          Question question = new QuestionBoolenne(intitule, true);
          listeQuestions.add(question);
        }
        verif = false;
        questionnaireIterator = this.listeQuestionnaire.iterator();
        while (questionnaireIterator.hasNext() && (!verif)) {
          questionnaireVerif = questionnaireIterator.next();
          if (questionnaireVerif.getNomQuestionnaire() == ancienNomQuestionnaire) {
            this.listeQuestionnaire.remove(questionnaireVerif);
            questionnaireVerif.setListeDeQuestions(listeQuestions);
            questionnaireVerif.setNomQuestionnaire(nouveauNomQuestionnaire);
            this.listeQuestionnaire.add(questionnaireVerif);
            ret = true;
            verif = true;
          }
        }
      }
    }
    return ret;
  }

  /**
   * Une méthode pour supprimer un questionnaire.
   * 
   * @param nomQuestionnaire : L'identifiant unique du questionnaire à supprimer
   * @return Retourne vrai si le questionnaire à été supprimé, faux sinon
   */
  public boolean supprimerQuestionnaire(String nomQuestionnaire) {
    boolean ret = false;
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0) {
      Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
      Questionnaire questionnaireVerif;
      while (questionnaireIterator.hasNext() && (!ret)) {
        questionnaireVerif = questionnaireIterator.next();
        if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
          this.listeQuestionnaire.remove(questionnaireVerif);
          ret = true;
        }
      }
    }
    return ret;
  }

  /**
   * Une méthode permettant d'obtenir un questionnaire grâce à son nom.
   * 
   * @param nomQuestionnaire : le nom du questionnaire
   * @return Retourne la liste des question retournée
   */
  public Questionnaire consulterListeQuestion(String nomQuestionnaire) {
    Questionnaire questionnaire = null;
    if (nomQuestionnaire != null && nomQuestionnaire.length() > 0) {
      Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
      Questionnaire questionnaireVerif;
      boolean verif = false;
      while (questionnaireIterator.hasNext() && (!verif)) {
        questionnaireVerif = questionnaireIterator.next();
        if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
          questionnaire = questionnaireVerif;
          verif = true;
        }
      }
    }
    return questionnaire;
  }

  public List<Questionnaire> getListeQuestionnaire() {
    return listeQuestionnaire;
  }

  public void setListeQuestionnaire(List<Questionnaire> listeQuestionnaire) {
    this.listeQuestionnaire = listeQuestionnaire;
  }



}
