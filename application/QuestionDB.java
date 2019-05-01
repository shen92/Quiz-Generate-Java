package application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the database for storing two types of questions
 * 
 * 1) Question from *.json File
 * 
 * 2) User added question
 * 
 * @author Yingjie, Bojun
 * 
 */
public class QuestionDB implements DatabaseADT {
  // The database is implemented by a HashMap,
  // K = topic of the question
  // V = question instance
  // HashMap<Topic, Question>
  private HashMap<String, Question> questionDatabase;

  /**
   * Constructor of the Question DB
   */
  public QuestionDB() {
    this.questionDatabase = new HashMap<>();
  }

  /**
   * This method adds a question to the database from an external json file
   * 
   * @param File jsonFile external file
   */
  @Override
  public void addQuestionFromFile(File jsonFile) {
    // TODO Auto-generated method stub

  }

  /**
   * This method adds a question to the database from an user
   * 
   * @Param Question user defined question
   */
  @Override
  public void addQuestionFromUser(Question question) {
    // TODO Auto-generated method stub

  }

  /**
   * This method gets the size of the database
   * 
   * @return int size
   */
  @Override
  public int size() {
    return this.questionDatabase.size();
  }

  @Override
  public void saveQuestions(ArrayList<Question> allQuestions) {
    // TODO Auto-generated method stub

  }

}
