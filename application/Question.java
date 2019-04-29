package application;

import java.util.LinkedHashMap;
import javafx.scene.control.CheckBox;

public class Question {
  private String meta_data;

  private String topic;
  private String questionText;

  private LinkedHashMap<String, String> answer;
  private String imageFile;

  private CheckBox checkBox = new CheckBox();


  /**
   * Default no-arg constructor
   */
  public Question() {
    this.meta_data = null;
    this.topic = null;
    this.questionText = null;
    this.answer = null;
    this.imageFile = null;
  }

  public Question(String topic, String questionText, String imageFile) {
    this.topic = topic;
    this.questionText = questionText;
    this.imageFile = imageFile;
    this.checkBox.setSelected(false);
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

  public void setChoice(LinkedHashMap<String, String> answer) {
    this.answer = answer;
  }

  public void setImage(String imageFile) {
    this.imageFile = imageFile;
  }

  public void setSelected(Boolean select) {
    this.checkBox.setSelected(select);
  }

  public String getMetaData() {
    return this.meta_data;
  }

  public String getTopic() {
    return this.topic;
  }

  public String getQuestionText() {
    return this.questionText;
  }

  public LinkedHashMap<String, String> getChoice() {
    return this.answer;
  }

  public String getImage() {
    return this.imageFile;
  }

  public CheckBox getCheckBox() {
    return this.checkBox;
  }

  public boolean getSelected() {
    return this.checkBox.isSelected();
  }
}
