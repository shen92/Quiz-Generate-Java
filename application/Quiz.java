package application;

import java.util.LinkedList;

public class Quiz {
  private LinkedList<Question> quizQuestions;
  private int correct;

  public Quiz() {
    this.quizQuestions = new LinkedList<>();
    this.correct = 0;
  }

  public int size() {
    return quizQuestions.size();
  }
  
  


}
