package application;

import java.util.ArrayList;

public class Question {
  private String text;
  private ArrayList<Choice> answer;
  private String imageFile;

  /**
   * Default no-arg constructor
   */
  public Question() {

  }

  public Question(ArrayList<Choice> answer) {
    this.answer = answer;
  }

  public Question(ArrayList<Choice> answer, String imageFile) {
    this.answer = answer;
    this.imageFile = imageFile;
  }
}
