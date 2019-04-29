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

  @Override
  public void start(Stage primaryStage) {
    try {
      setup(primaryStage);
      primaryStage.setScene(quizGeneratorGUI.getScene());
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setup(Stage primaryStage) {
    this.quizGeneratorGUI = new QuizGeneratorGUI(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
