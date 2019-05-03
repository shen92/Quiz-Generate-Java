//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Question class
// Files: ChoiceGroup.java Main.java Question.java QuestionDatabase.java
// QuizGeneratorGUI.java QuizResultsGUI.java TopicRow.java
// Course: CS400
//
// Author: Zhelai Chen, Yingjie Shen, Dongxia Wu, Kerui Wang, Bojun Xu
// Email: zchen743@wisc.edu, shen92@wisc.edu, dwu93@wisc.edu,
// kwang392@wisc.edu, bxu57@wisc.edu
// Lecturer's Name: Deb Deppeler
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
package application;

/**
 * This class represents the question in the question database
 * 
 * And implements the IQuestion interface
 * 
 * @Author Bojun Xu, Yingjie Shen
 */
public class Question implements IQuestion {
  // Fields of the question
  // Sorted by json file
  private String meta_data;
  private String questionText;
  private String topic;
  private String imageFile;
  private ChoiceGroup choiceGroup;

  /**
   * Default no-argument constructor of a Question object
   * 
   * This method sets each field to null
   */
  public Question() {
    this.meta_data = null;
    this.topic = null;
    this.questionText = null;
    this.choiceGroup = new ChoiceGroup();
    this.imageFile = null;
  }

  /**
   * This method sets the meta_data field during adding new question to the question database
   * 
   * @param String meta_data
   */
  public void setMetaData(String metaData) {
    this.meta_data = metaData;
  }

  /**
   * This method sets the question text field during adding new question to the question database
   * 
   * @param String questionText
   */
  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }

  /**
   * This method sets the topic field during adding new question to the question database
   * 
   * @param String topic
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }


  /**
   * This method sets the imageFile field during adding new question to the question database
   * 
   * @param String imageFile
   */
  public void setImage(String imageFile) {
    this.imageFile = imageFile;
  }

  /**
   * This method sets the ChoiceGroup field during adding new question to the question database
   * 
   * @param ChoiceGroup choiceGroup
   */
  public void setChoiceGroup(ChoiceGroup choiceGroup) {
    this.choiceGroup = choiceGroup;
  }

  /**
   * This method gets the meta_data field during
   * 
   * 1) displaying the question in quiz
   * 
   * 2) generating the quiz row
   * 
   * @return String meta_data
   */
  public String getMetaData() {
    return this.meta_data;
  }

  /**
   * This method gets the questionText field during displaying the question in quiz
   * 
   * @return String questionText
   */
  public String getQuestionText() {
    return this.questionText;
  }

  /**
   * This method gets the topic field during generating the topic row
   * 
   * @return String topic
   */
  public String getTopic() {
    return this.topic;
  }

  /**
   * This method gets the imageFile field during displaying the imageFile in quiz
   * 
   * @return String imageFile
   */
  public String getImage() {
    return this.imageFile;
  }

  /**
   * This method gets the choiceGroup field during displaying the choices in quiz
   * 
   * @return ChoiceGroup choiceGroup
   */
  public ChoiceGroup getChoiceGroup() {
    return this.choiceGroup;
  }

  /**
   * This method checks if the question is answered correctly
   * 
   * @return boolean true is correct choice is selected
   */
  public boolean isCorrect() {
    return this.choiceGroup.isCorrect();
  }

  public boolean isAnswered() {
    return this.choiceGroup.isAnswered();
  }

  public void reset() {
    this.choiceGroup.reset();
  }
}
