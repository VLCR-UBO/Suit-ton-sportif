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
   * @param questions : la liste de string utilisée pour créée les questions.
   */
  public boolean ajouterQuestionnaire(String nomQuestionnaire, List<String> questions) {
    List<Question> listeQuestions = new ArrayList<Question>();
    Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
    Questionnaire questionnaireVerif;
    boolean ret = false;
    boolean verif = false;

    while (questionnaireIterator.hasNext() && (!verif)) {
      questionnaireVerif = questionnaireIterator.next();

      if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
        verif = true;
      }
    }

    if (!verif) {
      for (String intitule : questions) {
        Question question = new QuestionBooleene(intitule, true);
        listeQuestions.add(question);

      }
      Questionnaire questionnaire = new Questionnaire(nomQuestionnaire, listeQuestions);
      if (!this.listeQuestionnaire.contains(questionnaire)) {
        this.listeQuestionnaire.add(questionnaire);
        ret = true;
      }
    }

    return ret;

  }

  /**
   * Une méthode pour modifier un questionnaire depuis une liste de string utilisée pour créée le
   * nouveau questionnaire.
   * 
   * @param questions : la liste de string utilisée pour le nouveau questionnaire
   * @return : vrai si le questionnaire à été modifié, faux sinon
   */
  public boolean modifierQuestionnaire(String nomQuestionnaire, List<String> questions) {
    List<Question> listeQuestions = new ArrayList<Question>();
    Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
    Questionnaire questionnaireVerif;
    boolean ret = false;
    boolean verif = false;

    while (questionnaireIterator.hasNext() && (!verif)) {
      questionnaireVerif = questionnaireIterator.next();

      if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
        verif = true;
      }
    }

    if (!verif) {
      for (String intitule : questions) {
        Question question = new QuestionBooleene(intitule, true);
        listeQuestions.add(question);

      }
    }

    verif = false;
    while (questionnaireIterator.hasNext() && (!verif)) {
      questionnaireVerif = questionnaireIterator.next();

      if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
        this.listeQuestionnaire.remove(questionnaireVerif);
        questionnaireVerif.setListeDeQuestions(listeQuestions);
        questionnaireVerif.setNomQuestionnaire(nomQuestionnaire);

        if (!this.listeQuestionnaire.contains(questionnaireVerif)) {
          this.listeQuestionnaire.add(questionnaireVerif);
          ret = true;
        }
        verif = true;
      }
    }
    return ret;
  }

  /**
   * Une méthode pour supprimer un questionnaire.
   * 
   * @param nomQuestionnaire : L'intitulé du questionnaire à supprimer
   * @return : vrai si le questionnaire à été supprimé, faux sinon
   */
  public boolean supprimerQuestionnaire(String nomQuestionnaire) {
    Iterator<Questionnaire> questionnaireIterator = this.listeQuestionnaire.iterator();
    Questionnaire questionnaireVerif;
    boolean ret = false;

    while (questionnaireIterator.hasNext() && (!ret)) {
      questionnaireVerif = questionnaireIterator.next();

      if (questionnaireVerif.getNomQuestionnaire() == nomQuestionnaire) {
        this.listeQuestionnaire.remove(questionnaireVerif);
        ret = true;

      }
    }
    return ret;

  }

  public List<Questionnaire> getListeQuestionnaire() {
    return listeQuestionnaire;
  }
  
  public void setListeQuestionnaire(List<Questionnaire> listeQuestionnaire) {
    this.listeQuestionnaire = listeQuestionnaire;
  }



}
