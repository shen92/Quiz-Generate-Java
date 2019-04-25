package application;

import java.util.HashMap;

public class Question {
  private String meta_data;

  private String topic;
  private String questionText;


  private HashMap<String, String> answer;
  private String imageFile;

  /**
   * Default no-arg constructor
   */
  public Question() {

  }

  public Question(String topic, String questionText) {
    this.topic = topic;
    this.questionText = questionText;
  }

  public void setMetaData(String metaData) {
    meta_data = metaData;
  }

  public void setContent(String questionText) {
    this.questionText = questionText;
  }

  public void setChoice(HashMap<String, String> answer) {
    this.answer = answer;
  }

  public void setImage(String imageFile) {
    this.imageFile = imageFile;
  }
}
