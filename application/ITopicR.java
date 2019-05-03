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
