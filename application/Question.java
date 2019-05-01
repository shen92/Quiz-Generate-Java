package application;

import java.util.LinkedHashMap;
import javafx.scene.control.CheckBox;

public class Question {
  private String meta_data;

  private String topic;
  private String questionText;

  private ChoiceGroup choiceGroup;
  private String imageFile;

  private CheckBox checkBox = new CheckBox();


  /**
   * Default no-arg constructor
   */
  public Question() {
    this.meta_data = null;
    this.topic = null;
    this.questionText = null;
    this.choiceGroup = null;
    this.imageFile = null;
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

  public void setChoice(ChoiceGroup choiceGroup) {
    this.choiceGroup = choiceGroup;
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

  public ChoiceGroup getChoiceGroup() {
    return this.choiceGroup;
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
