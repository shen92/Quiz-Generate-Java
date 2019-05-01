package application;

import javafx.scene.control.CheckBox;

/**
 * This class represent a row in the topic list TableView
 * 
 * @author Yingjie Shen, Kerui Wang
 */
public class TopicRow {
  private CheckBox checkBox;// CheckBox column
  private String topic;// topic column
  private int numQuestions;// numQuestions column, represent how may questions in a topic

  /**
   * default constructor of a topic row
   */
  public TopicRow() {
    this.checkBox = new CheckBox();
    this.checkBox.setSelected(false);
    this.topic = null;
    this.numQuestions = 0;
  }

  public CheckBox getCheckBox() {
    return this.checkBox;
  }

  public boolean getSelect() {
    return this.checkBox.isSelected();
  }

  public String getTopic() {
    return this.topic;
  }

  public int getNumQuestions() {
    return this.numQuestions;
  }

  public void setSelect(boolean select) {
    this.checkBox.setSelected(select);
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public void setNumQuestions(int numQuestions) {
    this.numQuestions = numQuestions;
  }
}
