package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;

public interface DatabaseADT {
  /**
   * This method adds a question to the database from an external json file
   * 
   * @param File jsonFile external file
   * @throws ParseException
   * @throws IOException
   * @throws FileNotFoundException
   */
  void addQuestionFromFile(File jsonFile) throws FileNotFoundException, IOException, ParseException;

  /**
   * This method saves the questions in the questionDatabase
   * 
   * @ArrayList<Question> allQuestions
   */
  void saveQuestions(ArrayList<Question> allQuestions);

  /**
   * This method adds a question to the database from an user
   * 
   * @Param Question user defined question
   */
  void addQuestionFromUser(Question question);

  /**
   * This method gets the size of the database
   * 
   * @return int size
   */
  int size();
}
