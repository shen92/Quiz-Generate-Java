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

  /**
   * this method returns the check box of the topic row.
   * 
   * @return CheckBox
   */
  public CheckBox getCheckBox() {
    return this.checkBox;
  }

  /**
   * this method returns if the topic row is selected.
   * 
   * @return boolean
   */
  public boolean getSelect() {
    return this.checkBox.isSelected();
  }

  /**
   * this method returns the topic of the topic row.
   * 
   * @return topic
   */
  public String getTopic() {
    return this.topic;
  }

  /**
   * this method returns the number of questions.
   * 
   * @return numQuestions
   */
  public int getNumQuestions() {
    return this.numQuestions;
  }

  /**
   * this method sets the select value to the topic row.
   * 
   * @param select
   */
  public void setSelect(boolean select) {
    this.checkBox.setSelected(select);
  }

  /**
   * this method sets the topic value to the topic row.
   * 
   * @param topic
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }

  /**
   * 
   * this method sets the numQuestions value to the topic row.
   * 
   * @param numQuestions
   */
  public void setNumQuestions(int numQuestions) {
    this.numQuestions = numQuestions;
  }
}
