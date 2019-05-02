package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import org.json.simple.parser.*;

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
