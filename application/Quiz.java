package application;

import java.util.LinkedList;

public class Quiz {
  private LinkedList<Question> quizQuestions;
  private LinkedList<Boolean> quizCorrectness;

  public Quiz() {
    this.quizQuestions = new LinkedList<>();
    this.quizCorrectness = new LinkedList<>();
  }

  public int size() {
    return quizQuestions.size();
  }

}
