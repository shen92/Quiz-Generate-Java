package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * This class tests the scene of the program
 * 
 * @author Yingjie Shen
 */

public class SceneTest extends Application {
  private PrimaryGUI primaryGUI;
  private QuizGeneratorGUI quizGeneratorGUI;
  private ShowQuestionGUI showQuestionGUI;
  private QuizResultsGUI quizResultsGUI;

  @Override
  public void start(Stage primaryStage) {
    try {
      this.primaryGUI = new PrimaryGUI(primaryStage);
      // TODO TEST Scene here:
      /* To test, change the *.getScene() */
      primaryStage.setScene(primaryGUI.getScene());
      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setup(Stage primaryStage) {
    this.quizGeneratorGUI = new QuizGeneratorGUI(primaryStage);
    this.showQuestionGUI = new ShowQuestionGUI(primaryStage);
    this.quizResultsGUI = new QuizResultsGUI(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
