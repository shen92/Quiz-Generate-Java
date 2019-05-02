package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import javafx.scene.control.CheckBox;
public interface IQuestionDatabase {
	
	/**
	   * Add question to question bank.
	   * 
	   * @param question
	   */
	public void addQuestion(Question question);
	/**
	   * write the json file of the questions from a specific topic
	   * 
	   * @param question
	   * @throws FileNotFoundException
	   */
	public void writeQuestions(ArrayList<Question> question) throws FileNotFoundException;

	  /**
	   * load questions from a json file add questions to questionBank
	   * 
	   * @param jsonFile
	   * @throws FileNotFoundException
	   * @throws IOException
	   * @throws ParseException
	   */
	public void loadQuestions(File jsonFile)throws FileNotFoundException, IOException, ParseException;
	
	/**
	   * get all the topics in questionBank
	   * 
	   * @return set of all topics
	   */
	public Set<String> getAllTopic();
	/**
	   * iterate all the questions in questionBank add all the questions to allQuestionSet
	   *
	   * @return ArrayList<ArrayList<Question>>
	   */
	public ArrayList<ArrayList<Question>> getAllQuestionSets();
	/**
	   * convert allQuestionSet to a single arrayList allQuestions
	   * 
	   * @return an arrayList of questions
	   */
	public ArrayList<Question> getAllQuestion();
	 /**
	   * get the arrayList of questions corresponding to the filtered topic
	   * 
	   * @param filteredTopic
	   * @return the arrayList of questions
	   */
	public ArrayList<Question> filteredQuestionList(String filteredTopic);
	/**
	   * update the number of topic rows when the number of topics increases
	   * 
	   * @param question
	   */
	public void updateTopicRow(Question question);
	/**
	   * get topicRows
	   * 
	   * @return topicRows
	   */
	public LinkedList<TopicRow> getTopicRows();
}
