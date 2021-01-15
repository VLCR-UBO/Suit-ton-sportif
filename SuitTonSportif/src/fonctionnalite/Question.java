package fonctionnalite;

public abstract class Question {

  private String intituleQuestion;

  public String getIntituleQuestion() {
    return intituleQuestion;
  }

  public void setIntituleQuestion(String intituleQuestion) {
    this.intituleQuestion = intituleQuestion;
  }

  public Question(String intitule) {
    this.intituleQuestion = intitule;

  }

}


