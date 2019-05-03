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
 * This interface contains the methods that TopicRow class should implement
 *
 * @Author Yingjie Shen, Kerui Wang
 */
interface ITopicR {

  /**
   * this method returns the check box of the topic row.
   * 
   * @return CheckBox
   */
  public CheckBox getCheckBox();

  /**
   * this method returns if the topic row is selected.
   * 
   * @return boolean
   */
  public boolean getSelect();

  /**
   * this method returns the topic of the topic row.
   * 
   * @return topic
   */
  public String getTopic();

  /**
   * this method returns the number of questions.
   * 
   * @return numQuestions
   */
  public int getNumQuestions();

  /**
   * this method sets the select value to the topic row.
   * 
   * @param select
   */
  public void setSelect(boolean select);

  /**
   * this method sets the topic value to the topic row.
   * 
   * @param topic
   */
  public void setTopic(String topic);

  /**
   * this method sets the numQuestions value to the topic row.
   * 
   * @param numQuestions
   */
  public void setNumQuestions(int numQuestions);
}
