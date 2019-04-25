package application;

import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;

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
    show();
  }

  private void show() {
    System.out.println(this.topic);
    System.out.println(this.questionText);
  }

  public void setMetaData(String metaData) {
    this.meta_data = metaData;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }

  public void setChoice(HashMap<String, String> answer) {
    this.answer = answer;
  }

  public void setImage(String imageFile) {
    this.imageFile = imageFile;
  }

  public String getMetaData(String metaData) {
    return this.meta_data;
  }

  public String getTopic(String topic) {
    return this.topic;
  }

  public String getQuestionText(String questionText) {
    return this.questionText;
  }

  public HashMap<String, String> getChoice(HashMap<String, String> answer) {
    return this.answer;
  }

  public String getImage(String imageFile) {
    return this.imageFile;
  }
}
