package fonctionnalite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Questionnaire {
  private String nomQuestionnaire;
  private List<Question> listeDeQuestions;


  /**
   * Un constructeur qui génère un questionnaire grâce à un nom et une liste de questions.
   * 
   * @param nom : le nom du questionnaire
   * @param lesQuestions : la liste de questions du questionnaire
   */
  public Questionnaire(String nom, List<Question> lesQuestions) {
    this.nomQuestionnaire = nom;
    this.listeDeQuestions = lesQuestions;
  }

  /**
   * Un constructeur qui génère un questionnaire grâce à un nom. Le questionnaire est vide par
   * défault. Il faut le remplir avec une liste de questions avec le setter dédié.
   * 
   * @param nom : le nom du questionnaire
   */
  public Questionnaire(String nom) {
    this.nomQuestionnaire = nom;
    this.listeDeQuestions = new ArrayList<Question>();
  }

  /**
   * Une méthode pour ajouter une question booléenne à la liste de question.
   * 
   * @param intitule : l'intitulé de la question
   * @param defaut : la réponse par défaut de la question
   * @return : vrai si la question à été ajoutée, faux sinon
   */
  public boolean ajouterQuestionBoolenne(String intitule, boolean defaut) {
    boolean ret = false;
    if (intitule != null && intitule.length() > 0) {
      List<Question> l = getListeDeQuestions();
      int taille = l.size();
      for (int i = 0; i < taille; i++) {
        if (l.get(i).getIntituleQuestion().equals(intitule)) {
          return ret; // question avec le même intitulé déjà present
        }
      }
      QuestionBoolenne questionBool = new QuestionBoolenne(intitule, defaut);
      if (!this.listeDeQuestions.contains(questionBool)) {
        this.listeDeQuestions.add(questionBool);
        ret = true;
      }
    }
    return ret;
  }

  /**
   * Une méthode pour modifier une question.
   * 
   * @param ancienIntitule : l'ancienne intitulé pour repérer la question à modifier.
   * @param nouveauIntitule : le nouvel intitulé.
   * @return : vrai si la question à été modifiée, faux sinon
   */
  public boolean modifierQuestionBoolenne(String ancienIntitule, String nouveauIntitule, boolean defaut) {
    if (ancienIntitule == null || ancienIntitule.length() < 1 || nouveauIntitule == null
        || nouveauIntitule.length() < 1) {
      return false; // paramètres incorrect
    }
    Iterator<Question> questionIterator = listeDeQuestions.iterator();
    Question question;
    QuestionBoolenne questionBool;
    boolean ret = false;
    while (questionIterator.hasNext() && (!ret)) {
      question = questionIterator.next();
      if ((question instanceof QuestionBoolenne)
          && (question.getIntituleQuestion() == ancienIntitule)) {
        this.listeDeQuestions.remove(question);
        questionBool = (QuestionBoolenne) question;
        questionBool.setIntituleQuestion(nouveauIntitule);
        questionBool.setReponseQuestion(defaut);
        this.listeDeQuestions.add(questionBool);
        ret = true;
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
