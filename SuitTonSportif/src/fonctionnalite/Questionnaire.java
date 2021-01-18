package fonctionnalite;

import java.util.Iterator;
import java.util.List;


public class Questionnaire {
  private String nomQuestionnaire;
  private List<Question> listeDeQuestions;


  /**
   * Un constructeur qui génère un questionnaire grâce à un id et une liste de questions.
   * 
   * @param nom : le nom du questionnaire
   * @param lesQuestions : la liste de questions du questionnaire
   */
  public Questionnaire(String nom, List<Question> lesQuestions) {
    this.nomQuestionnaire = nom;
    this.listeDeQuestions = lesQuestions;

  }

  /**
   * Une méthode pour ajouter une question booléenne à la liste de question.
   * 
   * @param intitule : l'intitulé de la question
   * @param defaut : la réponse par défaut de la question
   * @return : vrai si la question à été ajoutée, faux sinon
   */
  public boolean ajouterQuestionBooleene(String intitule, boolean defaut) {
    boolean ret = false;
    QuestionBooleene questionBool = new QuestionBooleene(intitule, defaut);
    if (!this.listeDeQuestions.contains(questionBool)) {
      this.listeDeQuestions.add(questionBool);
      ret = true;

    }
    return ret;
  }

  /**
   * Une méthode pour modifier une question.
   * 
   * @param ancienIntitule : l'ancienne intitulé pour repérer la question à modifier.
   * @param nouveauIntitule : le nouvel intitulé.
   * @param defaut : la nouvelle réponse par défault.
   * @return : vrai si la question à été modifiée, faux sinon
   */
  public boolean modifierQuestionBooleene(String ancienIntitule, String nouveauIntitule,
      boolean defaut) {
    Iterator<Question> questionIterator = listeDeQuestions.iterator();
    Question question;
    QuestionBooleene questionBool;
    boolean verif = false;
    boolean ret = false;

    while (questionIterator.hasNext() && (!ret)) {
      question = questionIterator.next();

      if ((question instanceof QuestionBooleene)
          && (question.getIntituleQuestion() == ancienIntitule)) {
        this.listeDeQuestions.remove(question);
        if (verif) {
          questionBool = (QuestionBooleene) question;
          questionBool.setIntituleQuestion(nouveauIntitule);
          questionBool.setReponseQuestion(defaut);
          this.listeDeQuestions.add(questionBool);
          ret = true;

        }
      }
    }
    return ret;
  }

  /**
   * Une méthode pour supprimer une question.
   * 
   * @param intitule : l'intitulé de la question à supprimer
   * @return : vrai si la question à été supprimée
   */
  public boolean supprimerQuestion(String intitule) {
    boolean ret = false;
    boolean verif = false;
    Iterator<Question> questionIterator = listeDeQuestions.iterator();
    Question question;

    while ((questionIterator.hasNext()) && (!ret)) {
      question = questionIterator.next();

      if (question.getIntituleQuestion() == intitule) {
        verif = this.listeDeQuestions.remove(question);
        if (verif) {
          ret = true;
        }
      }
    }

    return ret;
  }


  public String getNomQuestionnaire() {
    return this.nomQuestionnaire;
  }

  public void setNomQuestionnaire(String nom) {
    this.nomQuestionnaire = nom;
  }

  public List<Question> getListeDeQuestions() {
    return listeDeQuestions;
  }

  public void setListeDeQuestions(List<Question> listeDeQuestions) {
    this.listeDeQuestions = listeDeQuestions;
  }

}
