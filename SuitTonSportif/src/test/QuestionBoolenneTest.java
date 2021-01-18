package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fonctionnalite.QuestionBoolenne;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class QuestionBoolenneTest {
  private QuestionBoolenne questionBoolenne;

  @BeforeEach
  public void initialiser() {
    questionBoolenne = new QuestionBoolenne("intitule", true);
  }

  @AfterEach
  public void nettoyer() {
    questionBoolenne = null;
  }

  @Test
  public void questionBoolenne() {
    assertNotNull(questionBoolenne, "QuestionBoolenneTest : Instance questionBoolenne non créée");
    assertEquals(questionBoolenne.getIntituleQuestion(), "intitule",
        "Intitulé de questionBoolenne incorrect");
  }

  @Test
  public void getReponseQuestion() {
    assertTrue(questionBoolenne.getReponseQuestion(),
        "QuestionBoolenneTest : Instance questionBoolenne invalide");
  }

  @Test
  public void setReponseQuestion() {
    questionBoolenne.setReponseQuestion(false);
    assertFalse(questionBoolenne.getReponseQuestion(),
        "QuestionBoolenneTest : Instance questionBoolenne invalide");
  }

}
