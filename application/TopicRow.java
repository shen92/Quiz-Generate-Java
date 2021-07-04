//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
// Title: Quiz Generator
//
// Files: ChoiceGroup.java, IChoiceGroup.java, IGUI.java, IQuestion.java,
// IQuestionDatabase.java, ITopicR.java, Main.java, Question.java,
// QuestionDatabase.java, QuestionDisplayGUI.java, QuizGeneratorGUI.java,
// QuizResultsGUI.java, TopicRow.java, application.css
//
// Course: CS400 Spring 2019 Sec 001 & 002
//
// Author, E-mail:
// Bojun Xu, bxu57@wisc.edu
// Yingjie Shen, shen92@wisc.edu
// Kerui Wang, kwang392@wisc.edu
// Dongxia Wu, dwu93@wisc.edu
// Zhelai Chen, zchen743@wisc.edu
//
// Lecturer's Name: Deb Deppeler
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

import javafx.scene.control.CheckBox;

/**
 * This class represent a row in the topic list TableView
 * 
 * @author Yingjie Shen, Kerui Wang
 */
public class TopicRow implements ITopicR {
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
