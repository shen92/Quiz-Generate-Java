package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
  private HashMap<String, ArrayList<Question>> questionDatabase;

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
   * @throws ParseException
   * @throws IOException
   * @throws FileNotFoundException
   */
  @Override
  public void addQuestionFromFile(File jsonFile)
      throws FileNotFoundException, IOException, ParseException {
    if (jsonFile == null)
      return;

    Object obj1 = new JSONParser().parse(new FileReader(jsonFile));

    JSONObject jo = (JSONObject) obj1;

    JSONArray questionArray = (JSONArray) jo.get("questionArray");

    for (int i = 0; i < questionArray.size(); i++) {
      Object obj2 = new JSONParser().parse(questionArray.get(i).toString());
      JSONObject jo2 = (JSONObject) obj2;
      Question newQuestion = new Question();
      String meta_data = (String) jo2.get("meta-data");
      String questionText = (String) jo2.get("questionText");
      String topic = (String) jo2.get("topic");
      String image = (String) jo2.get("image");
      JSONArray choiceArray = (JSONArray) jo2.get("choiceArray");
      ChoiceGroup choiceGroup = new ChoiceGroup();
      // LinkedHashMap<String, String> questionChoice = new LinkedHashMap<String, String>();
      for (int j = 0; j < choiceArray.size(); j++) {
        Object obj3 = new JSONParser().parse(choiceArray.get(j).toString());
        JSONObject jo3 = (JSONObject) obj3;
        String correctness = (String) jo3.get("isCorrect");
        String choiceText = (String) jo3.get("choice");
        choiceGroup.addChoice(choiceText, correctness);
      }

      newQuestion.setQuestionText(questionText);
      newQuestion.setMetaData(meta_data);
      newQuestion.setChoice(choiceGroup);
      newQuestion.setImage(image);
      newQuestion.setTopic(topic);

      if (questionDatabase.containsKey(topic))
        questionDatabase.get(topic).add(newQuestion);
      else {
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionDatabase.put(topic, questionList);
        questionDatabase.get(topic).add(newQuestion);
      }
      //updateTopicRow(newQuestion);
    }
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
