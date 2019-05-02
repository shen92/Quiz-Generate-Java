//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: QuestionDatabase class
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import javafx.scene.control.CheckBox;

public class QuestionDatabase {
  private HashMap<String, ArrayList<Question>> questionBank;
  private CheckBox checkBox = new CheckBox();
  private LinkedList<TopicRow> topicRows = new LinkedList<TopicRow>();

  public QuestionDatabase() {
    questionBank = new HashMap<String, ArrayList<Question>>();
    this.checkBox.setSelected(false);
  }

  public void addQuestion(Question question) {
    if (questionBank.containsKey(question.getTopic()))
      questionBank.get(question.getTopic()).add(question);
    else {
      ArrayList<Question> newQuestionList = new ArrayList<Question>();
      newQuestionList.add(question);
      questionBank.put(question.getTopic(), newQuestionList);
    }

    updateTopicRow(question);
  }

  @SuppressWarnings("unchecked")
  public void writeQuestions(ArrayList<Question> question) throws FileNotFoundException {
    JSONObject jo1 = new JSONObject();

    JSONArray ja1 = new JSONArray();

    for (int i = 0; i < question.size(); i++) {
      JSONObject jo2 = new JSONObject();

      jo2.put("meta-data", question.get(i).getMetaData());
      jo2.put("questionText", question.get(i).getQuestionText());
      jo2.put("topic", question.get(i).getTopic());
      jo2.put("image", question.get(i).getImage());

      JSONArray ja2 = new JSONArray();
      Iterator<String> it = question.get(i).getChoiceGroup().getChoiceGroupKeys().iterator();
      JSONObject jo3;
      while (it.hasNext()) {
        jo3 = new JSONObject();
        String key = it.next();
        String correctness;
        if (question.get(i).getChoiceGroup().checkCorrectness(key))
          correctness = "T";
        else
          correctness = "F";
        jo3.put("isCorrect", correctness);
        jo3.put("choice", key);
        ja2.add(jo3);
      }

      jo2.put("choiceArray", ja2);
      ja1.add(jo2);
    }

    jo1.put("questionArray", ja1);

    PrintWriter pw = new PrintWriter("JSONExample.json");
    pw.write(jo1.toJSONString());

    pw.flush();
    pw.close();
  }

  public void loadQuestions(File jsonFile)
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
      ChoiceGroup questionChoice = new ChoiceGroup();
      for (int j = 0; j < choiceArray.size(); j++) {
        Object obj3 = new JSONParser().parse(choiceArray.get(j).toString());
        JSONObject jo3 = (JSONObject) obj3;
        String correctness = (String) jo3.get("isCorrect");
        String choiceText = (String) jo3.get("choice");
        questionChoice.addChoice(choiceText, correctness);
      }

      newQuestion.setQuestionText(questionText);
      newQuestion.setMetaData(meta_data);
      newQuestion.setChoiceGroup(questionChoice);
      newQuestion.setImage(image);
      newQuestion.setTopic(topic);


      if (questionBank.containsKey(topic))
        questionBank.get(topic).add(newQuestion);
      else {
        ArrayList<Question> questionList = new ArrayList<Question>();
        questionBank.put(topic, questionList);
        questionBank.get(topic).add(newQuestion);
      }
      updateTopicRow(newQuestion);
    }
  }

  public Set<String> getAllTopic() {
    return questionBank.keySet();
  }

  public ArrayList<ArrayList<Question>> getAllQuestionSets() {
    ArrayList<ArrayList<Question>> allQuestionSet = new ArrayList<ArrayList<Question>>();
    Iterator<String> it = getAllTopic().iterator();
    while (it.hasNext()) {
      allQuestionSet.add(questionBank.get(it.next()));
    }
    return allQuestionSet;
  }

  public ArrayList<Question> getAllQuestion() {
    ArrayList<Question> allQuestions = new ArrayList<Question>();
    for (int i = 0; i < getAllQuestionSets().size(); i++) {
      for (int j = 0; j < getAllQuestionSets().get(i).size(); j++) {
        allQuestions.add(getAllQuestionSets().get(i).get(j));
      }
    }

    return allQuestions;
  }

  public ArrayList<Question> filteredQuestionList(String filteredTopic) {
    return questionBank.get(filteredTopic);
  }

  public void updateTopicRow(Question question) {
    for (TopicRow tr : topicRows) {
      if (tr.getTopic().equals(question.getTopic())) {
        tr.setNumQuestions(tr.getNumQuestions() + 1);
        return;
      }

    }

    TopicRow tr = new TopicRow();
    tr.setTopic(question.getTopic());
    tr.setNumQuestions(1);
    tr.setSelect(false);
    topicRows.add(tr);
  }

  public LinkedList<TopicRow> getTopicRows() {
    return topicRows;
  }

}
