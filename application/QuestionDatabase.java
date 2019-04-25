package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class QuestionDatabase {
  private HashMap<String, ArrayList<Question>> questionBank;

  public QuestionDatabase() {
    questionBank = new HashMap<String, ArrayList<Question>>();
  }

  public void loadQuestions(File jsonFile)
      throws FileNotFoundException, IOException, ParseException {

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
      HashMap<String, String> questionChoice = new HashMap<String, String>();
      for (int j = 0; j < choiceArray.size(); j++) {
        Object obj3 = new JSONParser().parse(choiceArray.get(j).toString());
        JSONObject jo3 = (JSONObject) obj3;
        String correctness = (String) jo3.get("iscorrect");
        String choiceText = (String) jo3.get("choice");
        questionChoice.put(choiceText, correctness);
      }

      newQuestion.setQuestionText(questionText);
      newQuestion.setMetaData(meta_data);
      newQuestion.setChoice(questionChoice);
      newQuestion.setImage(image);
      newQuestion.setTopic(topic);

      if (questionBank.containsKey(topic))
        questionBank.get(topic).add(newQuestion);
      else {
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionBank.put(topic, questionList);
        questionBank.get(topic).add(newQuestion);
      }
    }
  }

  //get all topics
  public Set<String> getAllTopic() {
    return questionBank.keySet();
  }

  public ArrayList<ArrayList<Question>> getAllQuestionSets(){
    ArrayList<ArrayList<Question>> allQuestionSet = new ArrayList<ArrayList<Question>>();
    Iterator<String> it = getAllTopic().iterator();
    while(it.hasNext()) {
      allQuestionSet.add(questionBank.get(it.next()));
    }
    return allQuestionSet;
  }
  
  public ArrayList<Question> getAllQuestion() {
    ArrayList<Question> allQuestions = new ArrayList<Question>();
    for(int i = 0; i < getAllQuestionSets().size(); i++) {
      for(int j = 0; j < getAllQuestionSets().get(i).size(); j++) {
        allQuestions.add(getAllQuestionSets().get(i).get(j));
      }
    }
    
    return allQuestions;
  }
  
  

  public int getQuestionNum() {
    return questionBank.size();
  }

}
