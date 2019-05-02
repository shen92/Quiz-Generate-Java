package application;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class tests the scene of the program
 * 
 * @author Yingjie Shen
 */

public class Main extends Application {
  private QuizGeneratorGUI quizGeneratorGUI;
  private QuestionDatabase questionList;

  @Override
  public void start(Stage primaryStage) {
    try {
      setup(primaryStage);
      primaryStage.setScene(quizGeneratorGUI.getScene());
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    for (String to : questionList.getAllTopic()) {
    	System.out.print(to);
    }
    
  }

  private void setup(Stage primaryStage) {
	questionList = new QuestionDatabase();
    this.quizGeneratorGUI = new QuizGeneratorGUI(primaryStage, questionList);
    
  }

  public static void main(String[] args) {
    launch(args);
  }
}
