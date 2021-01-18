package fonctionnalite;

public class QuestionBoolenne extends Question {

  private boolean reponseQuestion;

  /**
   * Un constructeur qui génère une question grâce à un intitulé et une valeur par défaut.
   *
   * @param intitule : l'intitulé de la question
   * @param reponseQuestion : la réponse par default de la question
   */

  public QuestionBoolenne(String intitule, boolean reponseQuestion) {
    super(intitule);
    this.reponseQuestion = reponseQuestion;
    // TODO Auto-generated constructor stub
  }

  public boolean getReponseQuestion() {
    return reponseQuestion;
  }

  public void setReponseQuestion(boolean reponseQuestion) {
    this.reponseQuestion = reponseQuestion;
  }



}
