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

/**
 * This interface have the method the Question class should implement
 * 
 * @Author Bojun Xu, Yingjie Shen
 */
public interface IQuestion {

  /**
   * This method sets the meta_data field during adding new question to the question database
   * 
   * @param String meta_data
   */
  void setMetaData(String metaData);

  /**
   * This method sets the question text field during adding new question to the question database
   * 
   * @param String questionText
   */
  void setQuestionText(String questionText);

  /**
   * This method sets the topic field during adding new question to the question database
   * 
   * @param String topic
   */
  void setTopic(String topic);

  /**
   * This method sets the imageFile field during adding new question to the question database
   * 
   * @param String imageFile
   */
  void setImage(String imageFile);

  /**
   * This method gets the meta_data field during
   * 
   * 1) displaying the question in quiz
   * 
   * 2) generating the quiz row
   * 
   * @return String meta_data
   */
  String getMetaData();

  /**
   * This method gets the questionText field during displaying the question in quiz
   * 
   * @return String questionText
   */
  String getQuestionText();

  /**
   * This method gets the topic field during generating the topic row
   * 
   * @return String topic
   */
  String getTopic();

  /**
   * This method gets the imageFile field during displaying the imageFile in quiz
   * 
   * @return String imageFile
   */
  String getImage();

  /**
   * This method gets the choiceGroup field during displaying the choices in quiz
   * 
   * @return ChoiceGroup choiceGroup
   */
  ChoiceGroup getChoiceGroup();

  /**
   * This method checks if the question is answered correctly
   * 
   * @return boolean true is correct choice is selected
   */
  boolean isCorrect();

  /**
   * This method checks if the question is answered
   * 
   * @return boolean true is answered
   */
  boolean isAnswered();

}
