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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import org.json.simple.parser.*;

/**
 * This interface contains the methods the Question Database class should implement
 * 
 * @Author Bojun Xu
 */
public interface IQuestionDatabase {

  /**
   * Add question to question bank.
   * 
   * @param question
   */
  void addQuestion(Question question);

  /**
   * write the json file of the questions from a specific topic
   * 
   * @param question
   * @throws FileNotFoundException
   */
  void writeQuestions(ArrayList<Question> question) throws FileNotFoundException;

  /**
   * load questions from a json file add questions to questionBank
   * 
   * @param jsonFile
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  void loadQuestions(File jsonFile) throws FileNotFoundException, IOException, ParseException;

  /**
   * get all the topics in questionBank
   * 
   * @return set of all topics
   */
  Set<String> getAllTopic();

  /**
   * iterate all the questions in questionBank add all the questions to allQuestionSet
   *
   * @return ArrayList<ArrayList<Question>>
   */
  ArrayList<ArrayList<Question>> getAllQuestionSets();

  /**
   * convert allQuestionSet to a single arrayList allQuestions
   * 
   * @return an arrayList of questions
   */
  ArrayList<Question> getAllQuestion();

  /**
   * get the arrayList of questions corresponding to the filtered topic
   * 
   * @param filteredTopic
   * @return the arrayList of questions
   */
  ArrayList<Question> filteredQuestionList(String filteredTopic);

  /**
   * update the number of topic rows when the number of topics increases
   * 
   * @param question
   */
  void updateTopicRow(Question question);

  /**
   * get topicRows
   * 
   * @return topicRows
   */
  LinkedList<TopicRow> getTopicRows();
}
